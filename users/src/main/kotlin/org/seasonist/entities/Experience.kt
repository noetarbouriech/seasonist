package org.seasonist.entities

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.eclipse.microprofile.graphql.Ignore
import org.hibernate.annotations.UuidGenerator
import org.seasonist.entities.enums.JobCategory
import java.util.*

@Entity(name = "experiences")
class Experience : PanacheEntityBase {
	companion object : PanacheCompanionBase<Experience, UUID> {}

	@Id
	@UuidGenerator
	val id: UUID? = null

	lateinit var jobTitle: String
	lateinit var dateStart: Date
	var dateEnd: Date? = null // null if still working
	var description: String? = null
	var jobCategory: JobCategory? = null

	// foreign keys
	@Ignore
	var userId: UUID? = null
	var companyId: UUID? = null
}
