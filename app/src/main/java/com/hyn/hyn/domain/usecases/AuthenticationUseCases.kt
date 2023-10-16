package com.hyn.hyn.domain.usecases

data class AuthenticationUseCases(
    val isUserAuthenticated: IsUserAuthenticated,
    val signIn: FirebaseSignIn,
    val signUp: FirebaseSignUp,
    val signOut: FirebaseSignOut,
    val authState: AuthState,
)
