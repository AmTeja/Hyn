package com.hyn.hyn.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.hyn.hyn.data.repository.AuthenticationRepositoryImpl
import com.hyn.hyn.domain.repository.AuthenticationRepository
import com.hyn.hyn.domain.usecases.AuthState
import com.hyn.hyn.domain.usecases.AuthenticationUseCases
import com.hyn.hyn.domain.usecases.FirebaseSignIn
import com.hyn.hyn.domain.usecases.FirebaseSignOut
import com.hyn.hyn.domain.usecases.FirebaseSignUp
import com.hyn.hyn.domain.usecases.IsUserAuthenticated
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HynModule {

    @Singleton
    @Provides
    fun providesFirebaseAuthentication() : FirebaseAuth {
        return FirebaseAuth.getInstance();
    }

    @Singleton
    @Provides
    fun providesFirebaseFirestore() : FirebaseFirestore {
        return FirebaseFirestore.getInstance();
    }

    @Singleton
    @Provides
    fun providesFirebaseStorage() : FirebaseStorage {
        return FirebaseStorage.getInstance();
    }

    @Singleton
    @Provides
    fun providesAuthenticationRepository(auth: FirebaseAuth, firestore: FirebaseFirestore) : AuthenticationRepository {
        return AuthenticationRepositoryImpl(auth = auth, firestore = firestore)
    }

    @Singleton
    @Provides
    fun providesAuthenticationUseCases(repository: AuthenticationRepository) = AuthenticationUseCases(
        isUserAuthenticated = IsUserAuthenticated(repository),
        signIn = FirebaseSignIn(repository),
        signOut = FirebaseSignOut(repository),
        signUp = FirebaseSignUp(repository),
        authState = AuthState(repository)
    )

}