package com.hyn.hyn.ui.presentation.Authentication

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyn.hyn.domain.usecases.AuthenticationUseCases
import com.hyn.hyn.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authUseCases: AuthenticationUseCases
) : ViewModel() {

    val isUserAuthenticated get() = authUseCases.isUserAuthenticated()

    private val _signInState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signInState : State<Response<Boolean>> = _signInState

    private val _signUpState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signUpState : State<Response<Boolean>> = _signUpState

    private val _signOutState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signOutState : State<Response<Boolean>> = _signOutState

    private val _authState = mutableStateOf<Boolean>(false)
    val authState : State<Boolean> = _authState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            authUseCases.signIn(email, password).collect {
                _signInState.value = it
            }
        }
    }

    fun signUp(email: String, password: String, username: String) {
        viewModelScope.launch {
            authUseCases.signUp(email, password, username).collect() {
                _signUpState.value = it
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authUseCases.signOut().collect() {
                _signOutState.value = it
                if(it == Response.Success(true)) {
                    _signInState.value = Response.Success(false)
                    _signOutState.value = Response.Success(false)
                }
            }
        }
    }

    fun getAuthState() {
        viewModelScope.launch {
            authUseCases.authState().collect(){
                _authState.value = it
            }
        }
    }
}