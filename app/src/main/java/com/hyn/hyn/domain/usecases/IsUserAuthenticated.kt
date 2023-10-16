package com.hyn.hyn.domain.usecases

import com.hyn.hyn.domain.repository.AuthenticationRepository
import javax.inject.Inject

class IsUserAuthenticated @Inject constructor(
    private val repository: AuthenticationRepository
) {
    operator fun invoke() = repository.isUserAuthenticated();
}