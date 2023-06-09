package com.bangkit.coffee.domain.mapper

import com.bangkit.coffee.data.source.remote.model.RemoteUser
import com.bangkit.coffee.domain.entity.User

/* Remote <--> External Section */
fun RemoteUser.toExternal() = User(
    name = name,
    email = email,
    avatarUrl = avatarUrl.orEmpty(),
    blurHash = blurHash.orEmpty(),
)