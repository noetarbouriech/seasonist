package org.seasonist

import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import java.util.*

enum class JobCategory(val category: String) {
    AGRICULTURE_VITICULTURE_PECHERIE("Agriculture, Viticulture, Pêche"),
    HOTELLERIE_PLEIN_AIR_CLUB_VACANCES_CAMPING_ANIMATION("Hôtellerie de plein air, Club vacances, Camping, Animation"),
    HOTELS_CAFES_BARS_RESTAURANTS("Hôtels, cafés, bars, restaurants"),
    EVENEMENTIEL("Evenementiel"),
    CASINOS_PARCS_ATTRACTION("Casinos, Parcs d'attraction"),
    ADMINISTRATION_ESPACES_CULTURELS_TOURISME("Administration, Espaces culturels, Tourisme"),
    MONTAGNE_SKI("Montagne, Ski"),
    MER_PLONGEE_SPORTS_LOISIRS_NAUTIQUES("Mer, Plongée, Sports/Loisirs Nautiques"),
    SECURITE_GARDIENNAGE("Sécurité, Gardiennage"),
    LOGISTIQUE_TRANSPORT("Logistique, Transport"),
    BABY_SITTING_SERVICES_PERSONNE("Baby sitting, Services à la personne"),
    COMMERCE_ACHATS_VENTE("Commerce, Achats, Vente"),
    SPA_ESTHETIQUE_COIFFURE("SPA, Esthétique, Coiffure")
}

@Entity
data class Offer(
    @Id
    @UuidGenerator
    val id: UUID? = null,

    var title: String? = null,

    var authorId: UUID? = null,

    var jobCategory: JobCategory? = null,

    @ElementCollection
    var benefits: List<String>? = null,

    var companyId: UUID? = null,

    var body: String? = null,

    var open: Boolean = false,

    @UpdateTimestamp
    val updatedAt: Date? = null,

    @CreationTimestamp
    val createdAt: Date? = null
) : PanacheEntityBase()
