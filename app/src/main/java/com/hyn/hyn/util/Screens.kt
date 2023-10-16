package com.hyn.hyn.util

sealed class Screens(val route: String) {
    object SplashScreen: Screens("splash_screen")
    object LoginScreen: Screens("login_screen")
    object SignUpScreen: Screens("signup_screen")
    object FeedScreen: Screens("feed_screen")
}