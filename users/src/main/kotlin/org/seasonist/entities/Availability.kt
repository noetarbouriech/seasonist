package org.seasonist.entities

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.eclipse.microprofile.graphql.Ignore
import org.seasonist.entities.enums.JobCategory
import java.util.*

@Entity(name = "availabilities")
class Availability : PanacheEntityBase {
	companion object : PanacheCompanionBase<Availability, UUID> {}

	@Id
	@Ignore
	lateinit var userId: UUID
	lateinit var dateStart: Date
	lateinit var dateEnd: Date
	lateinit var jobCategory: JobCategory
	lateinit var searchArea: String
}