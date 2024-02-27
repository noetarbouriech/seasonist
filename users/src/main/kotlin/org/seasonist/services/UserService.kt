package org.seasonist.services

import io.smallrye.graphql.api.Context
import jakarta.enterprise.context.ApplicationScoped
import jakarta.json.JsonValue
import org.eclipse.microprofile.graphql.GraphQLException
import org.keycloak.representations.idm.UserRepresentation
import org.seasonist.entities.enums.Gender
import org.seasonist.entities.enums.Nationality
import org.seasonist.entities.User
import java.util.*

@ApplicationScoped
class UserService(
	private val keycloakService: KeycloakService,
) {
	fun getUser(email: String, graphQLContext: Context): User {
		val userRepresentation = this.keycloakService.findUser(email)
			?: throw GraphQLException("User not found", GraphQLException.ExceptionType.DataFetchingException)
		val hasRecommendationsField = doesSelectedFieldsContains(RECOMMENDATIONS_FIELD, graphQLContext)
		val hasExperiencesField = doesSelectedFieldsContains(EXPERIENCES_FIELD, graphQLContext)
		val hasAvailabilityField = doesSelectedFieldsContains(AVAILABILITY_FIELD, graphQLContext)

		return convertUserRepresentationToUser(userRepresentation, hasRecommendationsField, hasExperiencesField, hasAvailabilityField)
	}

	fun getUser(id: UUID, graphQLContext: Context): User {
		val userRepresentation = this.keycloakService.findUser(id)
			?: throw GraphQLException("User not found", GraphQLException.ExceptionType.DataFetchingException)
		val hasRecommendationsField = doesSelectedFieldsContains(RECOMMENDATIONS_FIELD, graphQLContext)
		val hasExperiencesField = doesSelectedFieldsContains(EXPERIENCES_FIELD, graphQLContext)
		val hasAvailabilityField = doesSelectedFieldsContains(AVAILABILITY_FIELD, graphQLContext)

		return convertUserRepresentationToUser(userRepresentation, hasRecommendationsField, hasExperiencesField, hasAvailabilityField)
	}

	fun updateUser(
		id: UUID,
		firstname: String?,
		lastname: String?,
		email: String?,
		phone: String?,
		address: String?,
		gender: Gender?,
		bio: String?,
		nationality: Nationality?,
	) {
		val userRepr = UserRepresentation().apply {
			this.id = id.toString()
			this.attributes = mutableMapOf()
		}

		if (firstname != null) userRepr.firstName = firstname
		if (lastname != null) userRepr.lastName = lastname
		if (email != null) userRepr.email = email
		if (phone != null) userRepr.attributes["phone"] = listOf(phone)
		if (address != null) userRepr.attributes["address"] = listOf(address)
		if (gender != null) userRepr.attributes["gender"] = listOf(gender.toString())
		if (bio != null) userRepr.attributes["bio"] = listOf(bio)
		if (nationality != null) userRepr.attributes["nationality"] = listOf(nationality.toString())
		this.keycloakService.updateUser(userRepr)
	}

	companion object {
		const val RECOMMENDATIONS_FIELD = "recommendations"
		const val EXPERIENCES_FIELD = "experiences"
		const val AVAILABILITY_FIELD = "availability"

		fun doesSelectedFieldsContains(fieldName: String, context: Context): Boolean =
			context.selectedFields.asJsonArray().any { field ->
				if (!field.valueType.equals(JsonValue.ValueType.OBJECT))
					return@any false

				val obj = field.asJsonObject()
				if (!obj.containsKey(fieldName))
					return@any false

				true
			}

		private fun convertUserRepresentationToUser(
			userRepresentation: UserRepresentation,
			hasRecommendations: Boolean,
			hasExperiences: Boolean,
			hasAvailability: Boolean,
		): User {
			val user = User.from(userRepresentation)

			if (hasRecommendations) user.fetchRecommendations()
			if (hasExperiences) user.fetchExperiences()
			if (hasAvailability) user.fetchAvailability()

			return user
		}
	}
}