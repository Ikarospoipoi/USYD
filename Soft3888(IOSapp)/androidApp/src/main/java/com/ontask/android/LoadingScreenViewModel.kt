package com.ontask.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginScreenViewModel : ViewModel() {



    fun signWithGoogleCredential(credential: AuthCredential, navController: NavHostController) = viewModelScope.launch {
        try {
            //loadingState.emit(LoadingState.LOADING)
            Firebase.auth.signInWithCredential(credential).await()
            //loadingState.emit(LoadingState.LOADED)
            navController.navigate("dashboard_screen")
        } catch (e: Exception) {
            //loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }

}