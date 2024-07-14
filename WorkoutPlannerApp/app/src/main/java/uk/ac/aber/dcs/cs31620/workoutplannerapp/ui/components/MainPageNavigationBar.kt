package uk.ac.aber.dcs.cs31620.workoutplannerapp.ui.components

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.SportsMartialArts
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.SportsMartialArts
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import uk.ac.aber.dcs.cs31620.workoutplannerapp.R
import uk.ac.aber.dcs.cs31620.workoutplannerapp.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.workoutplannerapp.ui.navigation.screens

data class IconGroup(
    val filledIcon: ImageVector,
    val outlineIcon: ImageVector,
    val label: String
)

//sets up the navigation bar with the symbols and then puts them in a navigation bar
@Composable
fun MainPageNavigationBar(
    navController: NavController
) {
    val icons = mapOf(
        Screen.Home to IconGroup(
            filledIcon = Icons.Filled.CalendarMonth,
            outlineIcon = Icons.Outlined.CalendarMonth,
            label = stringResource(id = R.string.planner)
        ),
        Screen.Workouts to IconGroup(
            filledIcon = Icons.Filled.SportsMartialArts,
            outlineIcon = Icons.Outlined.SportsMartialArts,
            label = stringResource(id = R.string.my_exercises)
        )
    )
    NavigationBar {
        val navBackStackEntry by
            navController.currentBackStackEntryAsState()
        val currentDestination =
            navBackStackEntry?.destination
        screens.forEach { screen ->
            val isSelected = currentDestination
                ?.hierarchy?.any{it.route == screen.route} == true
            val labelText = icons[screen]!!.label

        NavigationBarItem(
            icon = {
                Icon(imageVector = (
                        if (isSelected)
                            icons[screen]!!.filledIcon
                                    else
                                        icons[screen]!!.outlineIcon),
                    contentDescription = labelText
                )
                   },label = { Text(labelText) },
            selected = isSelected,

            onClick = {
                Log.d("MainPageNavigationBar", "Navigating to ${screen.route}")

                navController.navigate(screen.route){
                    popUpTo(navController.graph.findStartDestination().id){
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        }
    }

}

@Preview
@Composable
fun MainPageNavigationBarPreview(){
    // MainPageNavigationBar()
}