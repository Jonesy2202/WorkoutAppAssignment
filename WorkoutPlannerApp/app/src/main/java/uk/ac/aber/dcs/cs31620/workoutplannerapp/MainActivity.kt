package uk.ac.aber.dcs.cs31620.workoutplannerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.workoutplannerapp.model.DayList
import uk.ac.aber.dcs.cs31620.workoutplannerapp.model.SaveDayData
import uk.ac.aber.dcs.cs31620.workoutplannerapp.model.WorkoutList
import uk.ac.aber.dcs.cs31620.workoutplannerapp.ui.dailyWorkouts.DailyWorkouts
import uk.ac.aber.dcs.cs31620.workoutplannerapp.ui.home.HomeScreen
import uk.ac.aber.dcs.cs31620.workoutplannerapp.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.workoutplannerapp.ui.theme.WorkoutPlannerAppTheme
import uk.ac.aber.dcs.cs31620.workoutplannerapp.ui.workouts.Workouts

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutPlannerAppTheme(dynamicColor = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BuildNavigationGraph()
                }
            }
        }
    }
}

@Composable
private fun BuildNavigationGraph() {
    val navController = rememberNavController()
    val workoutList = WorkoutList()
    val dayList = DayList()
    val savedDay: SaveDayData = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(Screen.Home.route){ HomeScreen(navController, savedDay)}
        composable(Screen.Workouts.route){ Workouts(navController, workoutList)}
        composable(Screen.DailyWorkouts.route){ DailyWorkouts(navController, dayList, savedDay, workoutList) }
    }
}
