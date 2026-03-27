package com.example.whatsappclone.DI

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {



//Firebase Auth Instance
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {

        return FirebaseAuth.getInstance()
    }

// Firebase DataBase Instance
    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase {

        return FirebaseDatabase.getInstance()

    }


}