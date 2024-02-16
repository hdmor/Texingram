package m.eight.texingram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import m.eight.texingram.presentation.chat.ChatScreen
import m.eight.texingram.presentation.username.UsernameScreen
import m.eight.texingram.ui.theme.TexingramTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TexingramTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "username_screen") {
                    composable(route = "username_screen") { UsernameScreen(onNavigate = navController::navigate) }
                    composable(
                        route = "chat_screen/{username}",
                        arguments = listOf(navArgument("username") {
                            type = NavType.StringType
                            nullable = true
                        })
                    ) { ChatScreen(username = it.arguments?.getString("username")) }
                }
            }
        }
    }
}