package uk.ac.aber.dcs.cs31620.workoutplannerapp.ui.workouts

import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.workoutplannerapp.R
import uk.ac.aber.dcs.cs31620.workoutplannerapp.ui.components.AddOrEditWorkout
import uk.ac.aber.dcs.cs31620.workoutplannerapp.model.Workout
import uk.ac.aber.dcs.cs31620.workoutplannerapp.model.WorkoutList
import uk.ac.aber.dcs.cs31620.workoutplannerapp.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.workoutplannerapp.ui.components.WorkoutCard

//this sets up the page to display all of the available workouts.
//they can be added, edited and deleted here.

@Composable
fun Workouts(
    navController: NavHostController,
    workoutList: WorkoutList
){
    var workouts by remember { mutableStateOf(workoutList.getAllWorkouts()) }
    TopLevelScaffold(
        navController = navController,

    ){
            innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ){

            Box (
                modifier = Modifier
                    .fillMaxSize()
            ){
//loop to display all workut cards
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    modifier = Modifier
                        .padding(start = 2.dp, bottom = 2.dp)
                ) {

                    items(workouts) {
                        WorkoutCard(
                            exerciseName = it,
                            modifier = Modifier
                                .padding(end = 2.dp, top = 2.dp),
                            sets = it,
                            repetitions = it,
                            weight = it,
                            workoutList = WorkoutList(),
                            onDelete = {
                                workoutList.removeWorkoutByName(it.exerciseName)
                                workouts = workoutList.getAllWorkouts().toMutableList()
                            },
                            isInDay = false
                        )
                    }
                }
                var showDialog by remember { mutableStateOf(false) }
//add button to add more exercises to workout pool
                FloatingActionButton(
                    modifier = Modifier
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
                    AddOrEditWorkout(
                        addOrEdit = true,
                        onDismiss = {showDialog = false},
                        onConfirm = {updatedWorkout ->
                            var updatedExerciseName = updatedWorkout.exerciseName
                            var updatedReps = updatedWorkout.repetitions
                            var updatedSets = updatedWorkout.sets
                            var updatedWeight = updatedWorkout.weight

                            var newWorkout = Workout(
                                updatedExerciseName,
                                updatedSets,
                                updatedReps,
                                updatedWeight
                            )
                            workoutList.addWorkout(newWorkout)



                            showDialog = false
                        },
                        )
                }
            }
        }
    }
}