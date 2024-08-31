package eu.androidudemyclass.eventaggregatorapp.screens

sealed class ScreenForApp(val route:String) {
    data object SignUp:ScreenForApp("SignUp")
    data object SignIn:ScreenForApp("SignIn")
    data object Welcome:ScreenForApp("welcome")
    data object AboutUS:ScreenForApp("about_us")
    data object Home:ScreenForApp("home")
    data object Contact:ScreenForApp("contact")

}