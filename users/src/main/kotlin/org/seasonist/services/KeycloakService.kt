package org.seasonist.services

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.core.Response.Status
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.UserRepresentation
import org.seasonist.entities.User

@ApplicationScoped
class KeycloakService(
	private val keycloak: Keycloak,
) {
	companion object {
		const val REALM_NAME = "seasonist"
	}

	private fun getRealm() = this.keycloak.realm(REALM_NAME)

	fun findUser(email: String): UserRepresentation? =
		this.getRealm().users().searchByUsername(email, true).firstOrNull()

	fun createUser(user: User, password: String): User? {
		val res = this.getRealm().users().create(user.toRepresentation(password))
		if (res.statusInfo.toEnum() != Status.CREATED) {
			return null
		}

		return this.findUser(user.email)?.let { User.from(it) }
	}
}
