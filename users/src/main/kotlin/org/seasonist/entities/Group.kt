package org.seasonist.entities

enum class Group {
	EMPLOYER {
		override fun toString() = "employers"
	},
	WORKER {
		override fun toString() = "workers"
	},
	ADMIN {
		override fun toString() = "admins"
	};

	companion object {
		fun toEnum(value: String): Group {
			println("value: $value")
			return when (value) {
				"employers" -> EMPLOYER
				"admins" -> ADMIN
				"workers" -> WORKER
				else -> WORKER
			}
		}
	}
}
