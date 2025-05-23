package com.unpa.calificaciones.providers

import android.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthProvider {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun register(email: String, pass: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, pass)
    }
    fun login(email: String, pass: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, pass)
    }
    fun getId(): String? {
        return auth.currentUser?.uid
    }
    fun isAlive(): Boolean {
        return auth.currentUser != null
    }

    fun logOut() {
        return auth.signOut()
    }

}