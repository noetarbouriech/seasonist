package org.seasonist.services

import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.graphql.GraphQLException
import org.keycloak.representations.idm.UserRepresentation
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
}