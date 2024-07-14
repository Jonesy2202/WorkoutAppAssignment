package uk.ac.aber.dcs.cs31620.workoutplannerapp.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.workoutplannerapp.model.SaveDayData
import uk.ac.aber.dcs.cs31620.workoutplannerapp.model.workoutDays
import uk.ac.aber.dcs.cs31620.workoutplannerapp.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.workoutplannerapp.ui.components.WeekCard

//this sets up the home screen that displays the day sessions

@Composable
fun HomeScreen(
    navController: NavHostController,
    savedDay: SaveDayData
){

    TopLevelScaffold(
        navController = navController
    ){
        innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ){
//loops through the days to display the days in cards
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier
                    .padding(start = 2.dp, bottom = 2.dp)
            ){
                items(workoutDays){
                    WeekCard(
                        dayOfWeek = it,
                        modifier = Modifier
                            .padding(end = 2.dp, top = 2.dp),
                        time = it,
                        isSelected = it,
                        exerciseFocus = it,
                        navController = navController,
                        savedDay = savedDay


                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview(){
    val savedDay: SaveDayData = viewModel()
    val navController = rememberNavController()
    HomeScreen(navController, savedDay)
}