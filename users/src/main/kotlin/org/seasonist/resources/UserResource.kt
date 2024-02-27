package org.seasonist.resources

import io.smallrye.graphql.api.Context
import jakarta.transaction.Transactional
import org.eclipse.microprofile.graphql.*
import org.eclipse.microprofile.graphql.GraphQLException.ExceptionType
import org.seasonist.entities.*
import org.seasonist.entities.enums.Gender
import org.seasonist.entities.enums.JobCategory
import org.seasonist.entities.enums.Nationality
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
		dateStart: String,
		dateEnd: String?,
		description: String?,
		jobCategory: JobCategory?,
		companyId: UUID?,
	): User {
		val user = this.userService.getUser(userId, context)

		try {
			val dateStartFormatted = dateFormatter.parse(dateStart)
			val dateEndFormatted = dateEnd?.let { dateFormatter.parse(it) }
			if (dateEndFormatted != null && dateStartFormatted.after(dateEndFormatted))
				throw GraphQLException("Start date cannot be after end date", ExceptionType.ExecutionAborted)

			val experience = Experience()
			experience.jobTitle = jobTitle
			experience.dateStart = dateStartFormatted
			experience.dateEnd = dateEndFormatted
			experience.description = description
			experience.jobCategory = jobCategory
			experience.userId = userId
			experience.companyId = companyId
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

	@Mutation
	@Transactional
	fun updateAvailability(
		userId: UUID,
		dateStart: String,
		dateEnd: String,
		searchArea: String?,
		jobCategory: JobCategory?,
	): User {
		val user = this.userService.getUser(userId, context)
		var availability = Availability.findById(userId)
		if (availability == null) {
			availability = Availability()
			availability.userId = userId
		}

		try {
			val dateStartFormatted = dateFormatter.parse(dateStart)
			val dateEndFormatted = dateFormatter.parse(dateEnd)
			if (dateStartFormatted.after(dateEndFormatted))
				throw GraphQLException("Start date cannot be after end date", ExceptionType.ExecutionAborted)

			availability.dateStart = dateStartFormatted
			availability.dateEnd = dateEndFormatted
			availability.searchArea = searchArea
			availability.jobCategory = jobCategory
			Availability.persist(availability)

			if (UserService.doesSelectedFieldsContains(UserService.AVAILABILITY_FIELD, context))
				user.availability = availability
		} catch (e: ParseException) {
			throw GraphQLException("Invalid date format", ExceptionType.ExecutionAborted)
		}

		return user
	}
}