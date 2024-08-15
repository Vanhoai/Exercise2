package com.dyland.exercise2

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dyland.exercise2.folder.FolderView
import com.dyland.exercise2.main.MainUIEvents
import com.dyland.exercise2.main.MainUIState
import com.dyland.exercise2.main.MainView

@Composable
fun MainNavigation(
    mainUIState: MainUIState,
    onEvent: (MainUIEvents) -> Unit,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.MAIN_SCREEN
    ) {
        composable(Routes.MAIN_SCREEN) {
            MainView(navController = navController, mainUIState = mainUIState, onEvent = onEvent)
        }

        composable(
            "${Routes.FOLDER_SCREEN}/{name}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType }
            )
        ) {
            val name = it.arguments?.getString("name") ?: "Folder"

            FolderView(
                name = name,
                mainUIState = mainUIState,
                navController = navController,
                onEvent = onEvent
            )
        }
    }
}