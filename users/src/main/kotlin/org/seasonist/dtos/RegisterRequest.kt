package org.seasonist.dtos

import org.seasonist.entities.enums.Gender
import org.seasonist.entities.enums.Nationality

data class RegisterRequest(
	val email: String,
	val password: String,
	val confirmPassword: String,
	val firstname: String,
	val lastname: String,
	val phone: String?,
	val address: String?,
	val nationality: Nationality?,
	val gender: Gender?,
)
