package org.seasonist

import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import java.util.Date
import java.util.UUID

@Entity
data class Company(
        @Id
        @UuidGenerator
        val id: UUID? = null,

        var name: String? = null,

        var address: String? = null,

        @UpdateTimestamp
        val updatedAt: Date? = null,

        @CreationTimestamp
        val createdAt: Date? = null
) : PanacheEntityBase()
