package org.seasonist.entities

enum class SubscriptionKind {
	FREE {
		override fun toString() = "free"
	},
	SILVER {
		override fun toString() = "silver"
	},
	GOLD {
		override fun toString() = "gold"
	},
	PLATINUM {
		override fun toString() = "platinum"
	};

	companion object {
		fun toEnum(value: String): SubscriptionKind {
			return when (value) {
				"free" -> FREE
				"silver" -> SILVER
				"gold" -> GOLD
				"platinum" -> PLATINUM
				else -> FREE
			}
		}
	}
}
