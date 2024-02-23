package org.seasonist.entities

enum class Nationality {
	FRANCE {
		override fun toString() = "france"
	},
	BELGIUM {
		override fun toString() = "belgium"
	},
	GERMANY {
		override fun toString() = "germany"
	},
	ITALY {
		override fun toString() = "italy"
	},
	SPAIN {
		override fun toString() = "spain"
	},
	SWEDEN {
		override fun toString() = "sweden"
	},
	SWISS {
		override fun toString() = "swiss"
	},
	UNITED_KINGDOM {
		override fun toString() = "united-kingdom"
	},
	OTHER {
		override fun toString() = "other"
	};

	companion object {
		fun toEnum(value: String): Nationality? {
			return when (value) {
				"france" -> FRANCE
				"belgium" -> BELGIUM
				"germany" -> GERMANY
				"italy" -> ITALY
				"spain" -> SPAIN
				"sweden" -> SWEDEN
				"swiss" -> SWISS
				"united-kingdom" -> UNITED_KINGDOM
				"other" -> OTHER
				else -> null
			}
		}
	}
}
