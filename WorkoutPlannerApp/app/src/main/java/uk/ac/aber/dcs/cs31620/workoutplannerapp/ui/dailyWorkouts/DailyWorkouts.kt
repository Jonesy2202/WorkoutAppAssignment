package uk.ac.aber.dcs.cs31620.workoutplannerapp.ui.dailyWorkouts

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.workoutplannerapp.R
import uk.ac.aber.dcs.cs31620.workoutplannerapp.ui.components.AddDayExercise
import uk.ac.aber.dcs.cs31620.workoutplannerapp.model.DayList
import uk.ac.aber.dcs.cs31620.workoutplannerapp.model.SaveDayData
import uk.ac.aber.dcs.cs31620.workoutplannerapp.model.WorkoutList
import uk.ac.aber.dcs.cs31620.workoutplannerapp.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.workoutplannerapp.ui.components.WorkoutCard

//this sets up the page for the daily workouts when a say is selected

@Composable
fun DailyWorkouts(
    navController: NavHostController, days: DayList, savedDay: SaveDayData, workoutList: WorkoutList
) {
    var workouts by remember { mutableStateOf(days.getAllWorkouts(savedDay.getDay())) }
    Log.d("Saved day", savedDay.getDay())

    TopLevelScaffold(
        navController = navController
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column {
                Box {
                    Text(
                        text = savedDay.getDay(), fontWeight = FontWeight.Bold, //maybe change
                        fontSize = 30.sp, modifier = Modifier.padding(7.dp)

                    )
                }
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
//uses lazyverticalgrid to have the cards all follow beneath each other.
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        modifier = Modifier.padding(start = 2.dp, bottom = 2.dp)
                    ) {
//these logs are there to check to see if the workouts were displaying properly
                        Log.d("before loop", "before loop")
                        Log.d("workout data", "$workouts")
                        workouts?.let { it ->
                            items(it.toList()) {
                                Log.d("workout", "$it")
//loops round to build the workout cards with the data from workouts
                                WorkoutCard(exerciseName = it,
                                    modifier = Modifier.padding(end = 2.dp, top = 2.dp),
                                    sets = it,
                                    repetitions = it,
                                    weight = it,
                                    workoutList = WorkoutList(),
                                    onDelete = {
                                        days.removeWorkout(savedDay.getDay(), it)
                                    },
                                    isInDay = true)
                            }
                        }
                    }
//this makes the small add button in the bottom left corner to add a new daily workout from the workout pool available
                    var showDialog by remember { mutableStateOf(false) }
                    FloatingActionButton(modifier = Modifier
                        .size(70.dp)
                        .fillMaxWidth()
                        .align(Alignment.BottomEnd)
                        .padding(5.dp),
                        shape = RoundedCornerShape(16.dp),
                        onClick = { showDialog = true }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = stringResource(R.string.add_workout)
                        )
                    }
                    if (showDialog) {
//this adds the dialog to select the exercise you want
                        AddDayExercise(onDismiss = { showDialog = false },
                            onConfirm = { selectedWorkout ->
                                var setDay = savedDay.getDay()
                                var setWorkout = workoutList.getWorkoutByName(selectedWorkout)

                                if (setWorkout != null) {
                                    days.addWorkout(setDay, setWorkout)
                                }

                                showDialog = false
                            },
                            savedDay = savedDay,
                            workoutList = workoutList
                        )
                    }
                }
            }
        }
    }
}