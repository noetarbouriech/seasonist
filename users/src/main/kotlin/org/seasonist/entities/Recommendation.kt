package org.seasonist.entities

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity(name = "recommendations")
class Recommendation : PanacheEntityBase {
	@Id
	@UuidGenerator
	val id: UUID? = null

	lateinit var firstname: String
	lateinit var lastname: String
	var email: String? = null
	var phone: String? = null

	// External uuids
	var companyId: UUID? = null
	var userId: UUID? = null
}
