import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.bangkit.coffee.presentation.components.SimpleScreen

fun NavGraphBuilder.detectionGraph(navController: NavHostController) {
    navigation(
        startDestination = "list",
        route = "detections"
    ) {
        composable("list") {
            SimpleScreen(
                text = "List Detections",
                action = { navController.navigate("detections/detail/42") }
            )
        }

        composable(
            "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("userId")
            SimpleScreen(text = "Detail Detection: $id")
        }
    }
}