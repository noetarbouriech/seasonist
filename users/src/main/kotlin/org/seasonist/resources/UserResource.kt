package org.seasonist.resources

import jakarta.transaction.Transactional
import org.eclipse.microprofile.graphql.*
import org.eclipse.microprofile.graphql.GraphQLException.ExceptionType
import org.seasonist.entities.Experience
import org.seasonist.entities.User
import org.seasonist.services.KeycloakService
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@GraphQLApi
class UserResource(
	private val keycloakService: KeycloakService,
) {
	private val dateFormatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

	@Query
	@Description("Get User from an email")
	fun getUserByEmail(@Name("email") email: String): User {
		val userRepr = this.keycloakService.findUser(email)
			?: throw GraphQLException("User not found", ExceptionType.DataFetchingException)

		val user = User.from(userRepr)
		user.populateExperiences()

		// fetch additional user info like:
		// • companies
		// • experiences
		// • recommandations.
		user.fetchExperiences()

		return user
	}

	@Mutation
	@Transactional
	fun addExperience(
		userId: UUID,
		jobTitle: String,
		start: String,
		end: String?,
		description: String?,
		companyId: UUID?,
	): User {
		val userRepr = this.keycloakService.findUser(userId)
			?: throw GraphQLException("User not found", ExceptionType.DataFetchingException)
		val user = User.from(userRepr)
		user.fetchExperiences()

		try {
			val experience = Experience().apply {
				this.jobTitle = jobTitle
				this.dateStart = dateFormatter.parse(start)
				this.dateEnd = end?.let { dateFormatter.parse(it) }
				this.description = description
				this.userId = userId
				this.companyId = companyId
			}
			Experience.persist(experience)
			user.experiences.add(experience)
		} catch (e: ParseException) {
			throw GraphQLException("Invalid date format", ExceptionType.ExecutionAborted)
		}

		return user
	}

	@Mutation
	@Transactional
	fun deleteExperience(userId: UUID, experienceId: UUID): User {
		val userRepr = this.keycloakService.findUser(userId)
			?: throw GraphQLException("User not found", ExceptionType.DataFetchingException)
		val user = User.from(userRepr)
		user.fetchExperiences()

		val experience = user.experiences.find { it.id == experienceId }
			?: throw GraphQLException("Experience not found", ExceptionType.DataFetchingException)

		Experience.delete("id", experienceId)
		user.experiences.remove(experience)

		return user
	}
}