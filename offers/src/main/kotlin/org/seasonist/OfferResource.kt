package org.seasonist

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.eclipse.microprofile.graphql.Description
import org.eclipse.microprofile.graphql.GraphQLApi
import org.eclipse.microprofile.graphql.Mutation
import org.eclipse.microprofile.graphql.Query
import java.util.*

@GraphQLApi
@ApplicationScoped
class OfferResource(val repository: OfferRepository) {

    @Query
    @Description("Find an offer by id")
    fun findById(id: String): Offer? {
        try { UUID.fromString(id) } catch (e: IllegalArgumentException) { return null }
        return repository.findById(UUID.fromString(id))
    }

    @Query
    @Description("List all offers")
    fun list(): List<Offer>? = repository.findAll().list()

    @Mutation
    @Transactional
    @Description("Add an offer")
    fun addOffer(
        title: String,
        authorId: String,
        jobCategory: JobCategory,
        benefits: List<String>?,
        companyId: String,
        body: String,
        open: Boolean
    ): Offer {
        val offer = Offer(
            title = title,
            authorId = UUID.fromString(authorId),
            jobCategory = jobCategory,
            benefits = benefits,
            companyId = UUID.fromString(companyId),
            body = body,
            open = open
        )
        repository.persist(offer)
        return offer
    }

    @Mutation
    @Transactional
    @Description("Update offer")
    fun updateOffer(
        id: String,
        title: String,
        authorId: String,
        jobCategory: JobCategory,
        benefits: List<String>?,
        companyId: String,
        body: String,
        open: Boolean
    ): Offer? {
        try { UUID.fromString(id) } catch (e: IllegalArgumentException) { return null }
        val offer = repository.findById(UUID.fromString(id))
        if (offer != null) {
            offer.title = title
            offer.authorId = UUID.fromString(authorId)
            offer.jobCategory = jobCategory
            offer.benefits = benefits
            offer.companyId = UUID.fromString(companyId)
            offer.body = body
            offer.open = open
            repository.persist(offer)
            return offer
        }
        return null
    }

    @Mutation
    @Transactional
    @Description("Delete offer")
    fun deleteOffer(id: String): Offer? {
        try { UUID.fromString(id) } catch (e: IllegalArgumentException) { return null }
        val offer = repository.findById(UUID.fromString(id))
        if (offer != null) {
            repository.delete(offer)
            return offer
        }
        return null
    }

    @Mutation
    @Transactional
    @Description("Apply to an offer")
    fun applyOffer(id: String): Offer? {
        try { UUID.fromString(id) } catch (e: IllegalArgumentException) { return null }
        val offer = repository.findById(UUID.fromString(id))
        if (offer != null && offer.open) {
            // do something
            return offer
        }
        return null
    }
}