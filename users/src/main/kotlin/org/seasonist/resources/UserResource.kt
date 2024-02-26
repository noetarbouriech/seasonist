package org.seasonist.resources

import io.smallrye.graphql.api.Context
import jakarta.json.JsonValue
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
	companion object {
		const val RECOMMENDATIONS_FIELD = "recommendations"
		const val EXPERIENCES_FIELD = "experiences"
	}

	private val dateFormatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

	@Query
	@Description("Get User from an email")
	fun getUserByEmail(@Name("email") email: String): User = this.userService.getUser(
		email,
		doesSelectedFieldsContains(RECOMMENDATIONS_FIELD),
		doesSelectedFieldsContains(EXPERIENCES_FIELD)
	)

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

		val hasRecommendationsField = doesSelectedFieldsContains(RECOMMENDATIONS_FIELD)
		val hasExperiencesField = doesSelectedFieldsContains(EXPERIENCES_FIELD)
		val user = this.userService.getUser(userId, hasRecommendationsField, hasExperiencesField)

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
		val hasRecommendationsField = doesSelectedFieldsContains(RECOMMENDATIONS_FIELD)
		val hasExperiencesField = doesSelectedFieldsContains(EXPERIENCES_FIELD)
		val user = this.userService.getUser(userId, hasRecommendationsField, hasExperiencesField)

		val recommendation = Recommendation().apply {
			this.firstname = refereeFirstname
			this.lastname = refereeLastname
			this.email = refereeEmail
			this.phone = refereePhone
			this.userId = userId
			this.companyId = companyId
		}
		Recommendation.persist(recommendation)
		if (hasRecommendationsField)
			user.recommendations.add(recommendation)

		return user
	}

	@Mutation
	@Transactional
	fun deleteRecommendation(
		userId: UUID,
		recommendationId: UUID,
	): User {
		val hasRecommendationsField = doesSelectedFieldsContains(RECOMMENDATIONS_FIELD)
		val hasExperiencesField = doesSelectedFieldsContains(EXPERIENCES_FIELD)
		val user = this.userService.getUser(userId, hasRecommendationsField, hasExperiencesField)

		val recommendation = user.recommendations.find { it.id == recommendationId }
			?: throw GraphQLException("Recommendation not found", ExceptionType.DataFetchingException)

		Recommendation.delete("id", recommendationId)
		if (hasRecommendationsField)
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
		companyId: UUID?,
	): User {
		val hasRecommendationsField = doesSelectedFieldsContains(RECOMMENDATIONS_FIELD)
		val hasExperiencesField = doesSelectedFieldsContains(EXPERIENCES_FIELD)
		val user = this.userService.getUser(userId, hasRecommendationsField, hasExperiencesField)

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
			if (hasExperiencesField)
				user.experiences.add(experience)
		} catch (e: ParseException) {
			throw GraphQLException("Invalid date format", ExceptionType.ExecutionAborted)
		}

		return user
	}

	@Mutation
	@Transactional
	fun deleteExperience(userId: UUID, experienceId: UUID): User {
		val hasRecommendationsField = doesSelectedFieldsContains(RECOMMENDATIONS_FIELD)
		val hasExperiencesField = doesSelectedFieldsContains(EXPERIENCES_FIELD)
		val user = this.userService.getUser(userId, hasRecommendationsField, hasExperiencesField)

		val experience = user.experiences.find { it.id == experienceId }
			?: throw GraphQLException("Experience not found", ExceptionType.DataFetchingException)

		Experience.delete("id", experienceId)
		if (hasExperiencesField)
			user.experiences.remove(experience)

		return user
	}

	private fun doesSelectedFieldsContains(fieldName: String): Boolean =
		context.selectedFields.asJsonArray().any { field ->
			if (!field.valueType.equals(JsonValue.ValueType.OBJECT))
				return@any false

			val obj = field.asJsonObject()
			if (!obj.containsKey(fieldName))
				return@any false

			true
		}
}