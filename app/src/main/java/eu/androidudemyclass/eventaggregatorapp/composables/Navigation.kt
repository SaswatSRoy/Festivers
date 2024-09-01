package eu.androidudemyclass.eventaggregatorapp.composables

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import eu.androidudemyclass.eventaggregatorapp.model.AuthViewModel
import eu.androidudemyclass.eventaggregatorapp.repository.UserRepository
import eu.androidudemyclass.eventaggregatorapp.screens.ScreenForApp
import eu.androidudemyclass.eventaggregatorapp.signUp.SignUpScreen
import eu.androidudemyclass.signIn.SignInScreen

@Composable
fun Navigation(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userRepo: UserRepository
){
    NavHost(navController = navController, startDestination = ScreenForApp.Welcome.route){
        composable(route =ScreenForApp.Welcome.route){
            AnimatedSplashScreen(onNavigateToAboutUs = {
                navController.navigate(ScreenForApp.AboutUS.route)
            })
        }
        composable(route = ScreenForApp.AboutUS.route){
            AboutUsScreen(onNavigateToSignUp = { navController.navigate(ScreenForApp.SignUp.route) }, authViewModel = authViewModel)
        }
        composable(route = ScreenForApp.SignUp.route){
            SignUpScreen(
                onNavigateToLogin = {
                    navController.navigate(ScreenForApp.SignIn.route)
                },
                authViewModel = authViewModel
            )

        }

        composable(route = ScreenForApp.SignIn.route){
            SignInScreen(onNavigateToSignUp = {
                navController.navigate(ScreenForApp.SignUp.route)
            }, authViewModel = authViewModel)
        }
        composable(route = ScreenForApp.Home.route) {
            HomePage(authViewModel, userRepo) {
                navController.navigate(ScreenForApp.Contact.route)
            }
        }
        composable(route = ScreenForApp.Contact.route) {
            ContactUsScreen(authViewModel =authViewModel)
        }


    }
    LaunchedEffect(key1 = authViewModel.navigateToHome) {
        authViewModel.navigateToHome.collect {
            Log.d("Navigation", "Received navigateToHome signal, navigating to Home")
            navController.navigate(ScreenForApp.Home.route) {
                popUpTo(ScreenForApp.SignIn.route) { inclusive = true }
            }
        }

    }






}