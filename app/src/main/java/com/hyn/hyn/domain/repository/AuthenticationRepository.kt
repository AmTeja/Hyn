package com.hyn.hyn.domain.repository

import com.hyn.hyn.util.Response

import kotlinx.coroutines.flow.Flow


interface AuthenticationRepository {

    fun isUserAuthenticated() : Boolean

    fun getFirebaseAuthState() : Flow<Boolean>

    fun firebaseSignIn(email:String, password:String) : Flow<Response<Boolean>>

    fun firebaseSignOut() : Flow<Response<Boolean>>

    fun firebaseSignUp(email: String, password: String, username: String,) : Flow<Response<Boolean>>
}