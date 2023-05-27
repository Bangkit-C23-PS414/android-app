package com.bangkit.coffee.presentation.profile.components

import me.naingaungluu.formconductor.annotations.Form
import me.naingaungluu.formconductor.annotations.MinLength

@Form
data class EditProfileForm(
    @MinLength(1)
    val name: String = "",
)