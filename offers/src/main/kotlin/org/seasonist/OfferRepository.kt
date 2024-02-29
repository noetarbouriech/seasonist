package org.seasonist

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import java.util.*

@ApplicationScoped
class OfferRepository: PanacheRepositoryBase<Offer, UUID>