package com.hyn.hyn.domain.usecases

import com.hyn.hyn.domain.repository.AuthenticationRepository
import javax.inject.Inject

class AuthState @Inject constructor(
    private val repository: AuthenticationRepository
) {
    operator fun invoke() = repository.getFirebaseAuthState()
}