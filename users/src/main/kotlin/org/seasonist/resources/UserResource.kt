package org.seasonist.resources

import org.eclipse.microprofile.graphql.*
import org.eclipse.microprofile.graphql.GraphQLException.ExceptionType
import org.seasonist.entities.User
import org.seasonist.services.KeycloakService

@GraphQLApi
class UserResource(
	private val keycloakService: KeycloakService,
) {
	@Query
	@Description("Get User from an email")
	fun getUserByEmail(@Name("email") email: String): User {
		val user = this.keycloakService.findUser(email)
			?: throw GraphQLException("User not found", ExceptionType.DataFetchingException)

		// fetch additional user info like:
		// • companies
		// • experiences
		// • recommandations.

		return User.from(user)
	}
}