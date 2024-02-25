package org.seasonist.resources

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.Response.Status
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody
import org.seasonist.dtos.ErrorResponse
import org.seasonist.dtos.RegisterRequest
import org.seasonist.entities.User
import org.seasonist.services.KeycloakService

@Path("/auth")
class AuthResource(
	private val keycloakService: KeycloakService,
) {
	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	fun register(@RequestBody request: RegisterRequest): Response {
		if (request.password != request.confirmPassword) {
			return Response.status(Status.BAD_REQUEST).entity(ErrorResponse("Passwords are not matching")).build()
		}

		val user = User.from(request)
		val newUser = this.keycloakService.createUser(user, request.password)
			?: return Response.status(Status.CONFLICT).entity(ErrorResponse("User already exists")).build()

		return Response.ok(newUser).build()
	}
}
