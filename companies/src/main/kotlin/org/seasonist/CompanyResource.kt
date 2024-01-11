package org.seasonist

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.eclipse.microprofile.graphql.Description
import org.eclipse.microprofile.graphql.GraphQLApi
import org.eclipse.microprofile.graphql.Mutation
import org.eclipse.microprofile.graphql.Query
import java.util.UUID

@GraphQLApi
@ApplicationScoped
class CompanyResource(val repository: CompanyRepository) {

    @Query
    @Description("Find a company by id")
    fun findById(id: String): Company? {
        try { UUID.fromString(id) } catch (e: IllegalArgumentException) { return null }
        return repository.findById(UUID.fromString(id))
    }

    @Query
    @Description("List all companies")
    fun list(): List<Company>? = repository.findAll().list()

    @Mutation
    @Transactional
    @Description("Add company")
    fun addCompany(name: String, address: String): Company {
        val company = Company(name = name, address = address)
        repository.persist(company)
        return company
    }

    @Mutation
    @Transactional
    @Description("Update company")
    fun updateCompany(id: String, name: String, address: String): Company? {
        try { UUID.fromString(id) } catch (e: IllegalArgumentException) { return null }
        val company = repository.findById(UUID.fromString(id))
        if (company != null) {
            company.name = name
            company.address = address
            repository.persist(company)
            return company
        }
        return null
    }

    @Mutation
    @Transactional
    @Description("Delete company")
    fun deleteCompany(id: String): Company? {
        try { UUID.fromString(id) } catch (e: IllegalArgumentException) { return null }
        val company = repository.findById(UUID.fromString(id))
        if (company != null) {
            repository.delete(company)
            return company
        }
        return null
    }
}