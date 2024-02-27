package org.seasonist.resources

import io.smallrye.graphql.api.Context
import jakarta.transaction.Transactional
import org.eclipse.microprofile.graphql.*
import org.eclipse.microprofile.graphql.GraphQLException.ExceptionType
import org.seasonist.entities.*
import org.seasonist.services.UserService
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@GraphQLApi
class UserResource(
	private val userService: UserService,
	private val context: Context,
) {
	private val dateFormatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

	@Query
	@Description("Get User from an email")
	fun getUserByEmail(@Name("email") email: String): User = this.userService.getUser(email, context)

	@Query
	@Description("Get User from an ID")
	fun getUserById(@Name("userId") userId: UUID): User = this.userService.getUser(userId, context)

	@Mutation
	fun updateUserInformation(
		userId: UUID,
		firstname: String?,
		lastname: String?,
		email: String?,
		phone: String?,
		address: String?,
		gender: Gender?,
		bio: String?,
		nationality: Nationality?,
	): User {
		this.userService.updateUser(userId, firstname, lastname, email, phone, address, gender, bio, nationality)

		return this.userService.getUser(userId, context)
	}

	@Mutation
	@Transactional
	fun addRecommendation(
		userId: UUID,
		refereeFirstname: String,
		refereeLastname: String,
		refereeEmail: String?,
		refereePhone: String?,
		companyId: UUID?,
	): User {
		val user = this.userService.getUser(userId, context)

		val recommendation = Recommendation().apply {
			this.firstname = refereeFirstname
			this.lastname = refereeLastname
			this.email = refereeEmail
			this.phone = refereePhone
			this.userId = userId
			this.companyId = companyId
		}
		Recommendation.persist(recommendation)
		if (UserService.doesSelectedFieldsContains(UserService.RECOMMENDATIONS_FIELD, context))
			user.recommendations.add(recommendation)

		return user
	}

	@Mutation
	@Transactional
	fun deleteRecommendation(
		userId: UUID,
		recommendationId: UUID,
	): User {
		val user = this.userService.getUser(userId, context)

		val recommendation = user.recommendations.find { it.id == recommendationId }
			?: throw GraphQLException("Recommendation not found", ExceptionType.DataFetchingException)

		Recommendation.delete("id", recommendationId)
		if (UserService.doesSelectedFieldsContains(UserService.RECOMMENDATIONS_FIELD, context))
			user.recommendations.remove(recommendation)

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
		jobCategory: String?,
		companyId: UUID?,
	): User {
		val user = this.userService.getUser(userId, context)

		try {
			val experience = Experience().apply {
				this.jobTitle = jobTitle
				this.dateStart = dateFormatter.parse(start)
				this.dateEnd = end?.let { dateFormatter.parse(it) }
				this.description = description
				this.jobCategory = jobCategory
				this.userId = userId
				this.companyId = companyId
			}
			Experience.persist(experience)
			if (UserService.doesSelectedFieldsContains(UserService.EXPERIENCES_FIELD, context))
				user.experiences.add(experience)
		} catch (e: ParseException) {
			throw GraphQLException("Invalid date format", ExceptionType.ExecutionAborted)
		}

		return user
	}

	@Mutation
	@Transactional
	fun deleteExperience(userId: UUID, experienceId: UUID): User {
		val user = this.userService.getUser(userId, context)

		val experience = user.experiences.find { it.id == experienceId }
			?: throw GraphQLException("Experience not found", ExceptionType.DataFetchingException)

		Experience.delete("id", experienceId)
		if (UserService.doesSelectedFieldsContains(UserService.EXPERIENCES_FIELD, context))
			user.experiences.remove(experience)

		return user
	}
}