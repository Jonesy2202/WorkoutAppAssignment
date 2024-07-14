package uk.ac.aber.dcs.cs31620.workoutplannerapp.ui.navigation

sealed class Screen(
    val route: String
){
    object Home: Screen("home")
    object Workouts: Screen("workouts")
    object DailyWorkouts: Screen("dailyWorkouts")
}
val screens = listOf(
    Screen.Home,
    Screen.Workouts,
)