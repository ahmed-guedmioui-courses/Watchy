package com.ahmedapps.watchy.auth.domain.usecase

data class FormValidatorUseCase(
    val validName: ValidateNameUseCase,
    val validEmail: ValidateEmailUseCase,
    val validPassword: ValidatePasswordUseCase,
)
