package org.seasonist.services

import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.graphql.GraphQLException
import org.keycloak.representations.idm.UserRepresentation
import org.seasonist.entities.Gender
import org.seasonist.entities.Nationality
import org.seasonist.entities.User
import java.util.*

@ApplicationScoped
class UserService(
	private val keycloakService: KeycloakService,
) {
	fun getUser(email: String, hasRecommendations: Boolean, hasExperiences: Boolean): User {
		val userRepr = this.keycloakService.findUser(email)
			?: throw GraphQLException("User not found", GraphQLException.ExceptionType.DataFetchingException)

		return this.convertUserRepresentationToUser(userRepr, hasRecommendations, hasExperiences)
	}

	fun getUser(id: UUID, hasRecommendations: Boolean, hasExperiences: Boolean): User {
		val userRepr = this.keycloakService.findUser(id)
			?: throw GraphQLException("User not found", GraphQLException.ExceptionType.DataFetchingException)

		return this.convertUserRepresentationToUser(userRepr, hasRecommendations, hasExperiences)
	}

	private fun convertUserRepresentationToUser(
		userRepresentation: UserRepresentation,
		hasRecommendations: Boolean,
		hasExperiences: Boolean,
	): User {
		val user = User.from(userRepresentation)

		if (hasRecommendations) user.fetchRecommendations()
		if (hasExperiences) user.fetchExperiences()

		return user
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
}