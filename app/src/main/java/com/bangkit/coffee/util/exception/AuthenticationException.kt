package com.bangkit.coffee.util.exception

class AuthenticationException(
    authMessage: String
) : NetworkErrorException(errorMessage = authMessage) {}