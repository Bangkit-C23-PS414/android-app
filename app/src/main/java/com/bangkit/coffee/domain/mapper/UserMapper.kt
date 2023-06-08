package com.bangkit.coffee.domain.mapper

import com.bangkit.coffee.data.source.remote.response.profile.ProfileResponse
import com.bangkit.coffee.domain.entity.User

/* Remote <--> External Section */
fun ProfileResponse.toExternal() = User(
    name = name,
    email = email
)