package org.seasonist.entities

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import org.eclipse.microprofile.graphql.Ignore
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity(name = "recommendations")
class Recommendation : PanacheEntityBase {
	companion object : PanacheCompanionBase<Recommendation, UUID> {}

	@Id
	@UuidGenerator
	val id: UUID? = null

	lateinit var firstname: String
	lateinit var lastname: String
	var email: String? = null
	var phone: String? = null

	// foreign keys
	@Ignore
	var userId: UUID? = null
	var companyId: UUID? = null
}
