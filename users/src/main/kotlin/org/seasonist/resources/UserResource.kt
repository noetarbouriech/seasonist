package org.seasonist.resources

import io.smallrye.graphql.api.Context
import jakarta.json.JsonValue
import jakarta.transaction.Transactional
import org.eclipse.microprofile.graphql.*
import org.eclipse.microprofile.graphql.GraphQLException.ExceptionType
import org.seasonist.entities.Experience
import org.seasonist.entities.Recommendation
import org.seasonist.entities.User
import org.seasonist.services.KeycloakService
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


@GraphQLApi
class UserResource(
	private val keycloakService: KeycloakService,
	private val context: Context,
) {
	private val dateFormatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

	@Query
	@Description("Get User from an email")
	fun getUserByEmail(@Name("email") email: String): User {
		val userRepr = this.keycloakService.findUser(email)
			?: throw GraphQLException("User not found", ExceptionType.DataFetchingException)

		val user = User.from(userRepr)

		if (doesSelectedFieldsContains("recommendations"))
			user.fetchRecommendations()
		if (doesSelectedFieldsContains("experiences"))
			user.fetchExperiences()

		return user
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
		val userRepr = this.keycloakService.findUser(userId)
			?: throw GraphQLException("User not found", ExceptionType.DataFetchingException)
		val user = User.from(userRepr)

		val hasRecommendationField = doesSelectedFieldsContains("recommendations")
		if (hasRecommendationField)
			user.fetchRecommendations()
		if (doesSelectedFieldsContains("experiences"))
			user.fetchExperiences()

		val recommendation = Recommendation().apply {
			this.firstname = refereeFirstname
			this.lastname = refereeLastname
			this.email = refereeEmail
			this.phone = refereePhone
			this.companyId = companyId
		}
		Recommendation.persist(recommendation)
		if (hasRecommendationField) user.recommendations.add(recommendation)

		return user
	}

	@Mutation
	@Transactional
	fun deleteRecommendation(
		userId: UUID,
		recommendationId: UUID,
	): User {
		val userRepr = this.keycloakService.findUser(userId)
			?: throw GraphQLException("User not found", ExceptionType.DataFetchingException)
		val user = User.from(userRepr)

		val hasRecommendationField = doesSelectedFieldsContains("recommendations")
		if (hasRecommendationField) user.fetchRecommendations()
		if (doesSelectedFieldsContains("experiences")) user.fetchExperiences()

		val recommendation = user.recommendations.find { it.id == recommendationId }
			?: throw GraphQLException("Recommendation not found", ExceptionType.DataFetchingException)

		Recommendation.delete("id", recommendationId)
		if (hasRecommendationField) user.recommendations.remove(recommendation)

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

		val hasExperienceField = doesSelectedFieldsContains("experiences")
		if (hasExperienceField) user.fetchExperiences()
		if (doesSelectedFieldsContains("recommendations")) user.fetchRecommendations()

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
			if (hasExperienceField) user.experiences.add(experience)
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

		val hasExperienceField = doesSelectedFieldsContains("experiences")
		if (hasExperienceField) user.fetchExperiences()
		if (doesSelectedFieldsContains("recommendations")) user.fetchRecommendations()

		val experience = user.experiences.find { it.id == experienceId }
			?: throw GraphQLException("Experience not found", ExceptionType.DataFetchingException)

		Experience.delete("id", experienceId)
		if (hasExperienceField) user.experiences.remove(experience)

		return user
	}

	private fun doesSelectedFieldsContains(fieldName: String): Boolean {
		return context.selectedFields.asJsonArray().any { field ->
			if (!field.valueType.equals(JsonValue.ValueType.OBJECT))
				return@any false

			val obj = field.asJsonObject()
			println("obj: $obj -> " + obj.containsKey(fieldName))
			if (!obj.containsKey(fieldName))
				return@any false

			true
		}
	}
}