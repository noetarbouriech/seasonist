package org.seasonist

import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.validation.constraints.Max
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.ManyToAny
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import org.w3c.dom.Text
import java.util.*

@Entity
data class Offer(
    @Id
    @UuidGenerator
    val id: UUID? = null,

    var title: String? = null,

    var authorId: UUID? = null,

    @ElementCollection
    var jobCategoryIds: List<UUID>? = null,

    var companyId: UUID? = null,

    var body: String? = null,

    var open: Boolean = false,

    @UpdateTimestamp
    val updatedAt: Date? = null,

    @CreationTimestamp
    val createdAt: Date? = null
) : PanacheEntityBase()
