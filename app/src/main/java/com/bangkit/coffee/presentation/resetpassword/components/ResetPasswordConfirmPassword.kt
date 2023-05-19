package com.bangkit.coffee.presentation.resetpassword.components

import me.naingaungluu.formconductor.annotations.FieldValidation

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@FieldValidation(
    fieldType = String::class,
    validator = ResetPasswordConfirmPasswordRule::class
)
annotation class ResetPasswordConfirmPassword