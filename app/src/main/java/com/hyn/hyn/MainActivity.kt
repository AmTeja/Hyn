package com.hyn.hyn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hyn.hyn.ui.presentation.Authentication.AuthenticationViewModel
import com.hyn.hyn.ui.presentation.Authentication.LoginScreen
import com.hyn.hyn.ui.presentation.Authentication.SignUpScreen
import com.hyn.hyn.ui.presentation.Feed.FeedScreen
import com.hyn.hyn.ui.presentation.SplashScreen
import com.hyn.hyn.ui.theme.HynTheme
import com.hyn.hyn.util.Screens
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HynTheme {
                val navController = rememberNavController()
                val authViewModel : AuthenticationViewModel = hiltViewModel();
                HynApp(navController, authViewModel)
            }
        }
    }
}

@Composable
fun HynApp(navController: NavHostController, authViewModel: AuthenticationViewModel) {
    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
        composable(route = Screens.LoginScreen.route) {
            LoginScreen(navController, authViewModel)
        }

        composable(Screens.SignUpScreen.route) {
            SignUpScreen(navController, authViewModel)
        }

        composable(Screens.FeedScreen.route) {
            FeedScreen(navController)
        }

        composable(Screens.SplashScreen.route) {
            SplashScreen(navController, authViewModel)
        }
    }
}