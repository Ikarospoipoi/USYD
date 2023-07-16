package com.ontask.android

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavMap(navController: NavHostController, starDest:String, auth: FirebaseAuth) {
    androidx.navigation.compose.NavHost(navController = navController, startDestination = starDest){
        composable(route = "Register_screen"){
            Register(navController = navController, auth = auth)
        }
        composable(route = "Login_screen"){
            parentLoginPage(navController = navController, viewModel = LoginScreenViewModel(), auth = auth)
        }
        composable(route = "dashboard_screen"){
            dashboardPage(navController = navController, auth = auth)
        }
        composable(route = "addChildProfile_screen"){
            addChildProfile(navController = navController, auth = auth)
        }
        composable(route = "parentProfile_screen"){
            AdultProfilePage(navController = navController)
        }
        composable(route = "childProfile_screen"){
            childProfilePage(navController = navController)
        }
        composable(route = "addChore_screen"){
            AddChoresPage(navController = navController)
        }
        composable(route = "assignChore_screen"){
            assignChoresPage(navController = navController)
        }
        composable(route = "createContract_screen"){
            CreateContractPage(navController = navController)
        }
    }
}
