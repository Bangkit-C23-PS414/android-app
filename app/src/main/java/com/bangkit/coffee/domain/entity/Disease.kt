package com.bangkit.coffee.domain.entity

data class Disease(
    val id: String,
    val name: String,
    val description: String,
    val controls: List<String>,
    val imageUrl: String,
)
