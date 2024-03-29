package org.seasonist.services

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.core.Response.Status
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.UserRepresentation
import org.seasonist.entities.User
import java.util.UUID

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

	fun findUser(id: UUID): UserRepresentation? =
		this.getRealm().users().get(id.toString()).toRepresentation()

	fun createUser(user: User, password: String): User? {
		val res = this.getRealm().users().create(user.toRepresentation(password))
		if (res.statusInfo.toEnum() != Status.CREATED) {
			return null
		}

		return this.findUser(user.email)?.let { User.from(it) }
	}

	fun updateUser(user: UserRepresentation) = this.getRealm().users().get(user.id.toString()).update(user)
}
