package org.seasonist.entities

import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.seasonist.dtos.RegisterRequest
import org.seasonist.entities.enums.Gender
import org.seasonist.entities.enums.Group
import org.seasonist.entities.enums.Nationality
import org.seasonist.entities.enums.SubscriptionKind
import java.util.*

class User {
	// keycloak id
	var id: UUID? = null

	// keycloak fields
	lateinit var firstname: String
	lateinit var lastname: String
	lateinit var email: String
	lateinit var phone: String
	lateinit var address: String
	var group: Group = Group.WORKER
	var gender: Gender = Gender.PREFER_NOT_TO_STATE
	var subscriptionKind: SubscriptionKind = SubscriptionKind.FREE
	var bio: String = ""
	var nationality: Nationality? = null

	// database fields
	var recommendations: MutableList<Recommendation> = mutableListOf()
	var experiences: MutableList<Experience> = mutableListOf()
	var availability: Availability? = null

	fun toRepresentation(password: String): UserRepresentation {
		val userRepr = UserRepresentation()
		userRepr.username = this.email
		userRepr.firstName = this.firstname
		userRepr.lastName = this.lastname
		userRepr.email = this.email
		userRepr.credentials = listOf(
			CredentialRepresentation().apply {
				this.type = CredentialRepresentation.PASSWORD
				this.value = password
				this.isTemporary = false
			}
		)
		userRepr.attributes = mapOf(
			"phone" to listOf(this.phone),
			"address" to listOf(this.address),
			"group" to listOf(this.group.toString()),
			"gender" to listOf(this.gender.toString()),
			"subscriptionKind" to listOf(this.subscriptionKind.toString()),
			"bio" to listOf(this.bio),
			"nationality" to listOf(this.nationality?.toString()),
		)
		userRepr.groups = listOf(this.group.toString())
		userRepr.isEmailVerified = true
		userRepr.isEnabled = true

		return userRepr
	}

	fun fetchRecommendations() {
		val userId = this.id ?: return
		this.recommendations = Recommendation.list("userId", userId).toMutableList()
	}

	fun fetchExperiences() {
		val userId = this.id ?: return
		this.experiences = Experience.list("userId", userId).toMutableList()
	}

	fun fetchAvailability() {
		val userId = this.id ?: return
		this.availability = Availability.findById(userId)
	}

	companion object {
		fun from(request: RegisterRequest): User {
			return User().apply {
				this.firstname = request.firstname
				this.lastname = request.lastname
				this.email = request.email
				this.phone = request.phone ?: ""
				this.address = request.address ?: ""
			}
		}

		fun from(representation: UserRepresentation): User {
			return User().apply {
				this.id = UUID.fromString(representation.id)
				this.firstname = representation.firstName
				this.lastname = representation.lastName
				this.email = representation.email
				this.phone = representation.attributes["phone"]?.firstOrNull() ?: ""
				this.address = representation.attributes["address"]?.firstOrNull() ?: ""
				this.group = representation.attributes["group"]?.firstOrNull()?.let { Group.toEnum(it) } ?: Group.WORKER
				this.gender = representation.attributes["gender"]?.firstOrNull()?.let { Gender.toEnum(it) } ?: Gender.PREFER_NOT_TO_STATE
				this.subscriptionKind = representation.attributes["subscriptionKind"]?.firstOrNull()?.let { SubscriptionKind.toEnum(it) } ?: SubscriptionKind.FREE
				this.bio = representation.attributes["bio"]?.firstOrNull() ?: ""
				this.nationality = representation.attributes["nationality"]?.firstOrNull()?.let { Nationality.toEnum(it) }
			}
		}
	}
}
