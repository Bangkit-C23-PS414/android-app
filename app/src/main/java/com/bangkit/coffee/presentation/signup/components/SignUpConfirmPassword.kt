package com.bangkit.coffee.presentation.signup.components

import me.naingaungluu.formconductor.annotations.FieldValidation

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@FieldValidation(
    fieldType = String::class,
    validator = SignUpConfirmPasswordRule::class
)
annotation class SignUpConfirmPassword