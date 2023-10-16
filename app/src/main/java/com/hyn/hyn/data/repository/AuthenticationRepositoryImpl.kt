package com.hyn.hyn.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyn.hyn.domain.model.User
import com.hyn.hyn.domain.repository.AuthenticationRepository
import com.hyn.hyn.util.Constants
import com.hyn.hyn.util.Response
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : AuthenticationRepository {

    var operationSuccess : Boolean = false;

    override fun isUserAuthenticated(): Boolean {
        return auth.currentUser != null;
    }

    override fun getFirebaseAuthState(): Flow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener {
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    override fun firebaseSignIn(email: String, password: String): Flow<Response<Boolean>> = flow {
        operationSuccess = false;
        try {
            emit(Response.Loading)
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                operationSuccess = true
            }.await()
            emit(Response.Success(operationSuccess))
        } catch (e : Exception) {
            emit(Response.Error(e.localizedMessage?:"An Unexpected error has occurred!"))
        }
    }

    override fun firebaseSignOut(): Flow<Response<Boolean>> = flow{
        try {
            emit(Response.Loading)
            auth.signOut()
            emit(Response.Success(operationSuccess))
        } catch (e : Exception) {
            emit(Response.Error(e.localizedMessage?:"An Unexpected error has occurred!"))

        }
    }

    override fun firebaseSignUp(email: String, password: String, username: String): Flow<Response<Boolean>> = flow {
       operationSuccess = false;
        try {
            emit(Response.Loading)
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                operationSuccess = true;
            }
            if(operationSuccess) {
                val userId = auth.currentUser?.uid!!
                val obj = User(username = username, email=email, password=password, userId = userId)
                firestore.collection(Constants.COLLECTION_NAME_USERS).document(userId).set(obj).addOnSuccessListener {

                }.await()
                emit(Response.Success(operationSuccess))
            } else {
                Response.Success(operationSuccess)
            }
       }catch (e : Exception) {
            emit(Response.Error(e.localizedMessage?:"An Unexpected error has occurred!"))
        }
    }
}