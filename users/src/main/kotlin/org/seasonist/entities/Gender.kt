package org.seasonist.entities

enum class Gender {
	MALE {
		override fun toString() = "male"
	},
	FEMALE {
		override fun toString() = "female"
	},
	OTHER {
		override fun toString() = "other"
	},
	PREFER_NOT_TO_STATE {
		override fun toString() = "prefer-not-to-state"
	};

	companion object {
		fun toEnum(value: String): Gender {
			return when (value) {
				"male" -> MALE
				"female" -> FEMALE
				"other" -> OTHER
				"prefer-not-to-state" -> PREFER_NOT_TO_STATE
				else -> PREFER_NOT_TO_STATE
			}
		}
	}
}
