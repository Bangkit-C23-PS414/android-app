package com.bangkit.coffee.shared.exception

class AuthenticationException(
    authMessage: String
) : NetworkErrorException(errorMessage = authMessage)