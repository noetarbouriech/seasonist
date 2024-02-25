package org.seasonist.entities

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity(name = "experiences")
class Experience : PanacheEntityBase {
	@Id
	@UuidGenerator
	val id: UUID? = null

	lateinit var jobTitle: String
	lateinit var dateStart: Date

	var dateEnd: Date? = null // null if still working
	var description: String? = null
	var companyId: UUID? = null
}
