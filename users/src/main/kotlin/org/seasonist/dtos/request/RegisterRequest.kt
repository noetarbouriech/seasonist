package org.seasonist.dtos.request

import org.seasonist.entities.Gender
import org.seasonist.entities.Nationality

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
