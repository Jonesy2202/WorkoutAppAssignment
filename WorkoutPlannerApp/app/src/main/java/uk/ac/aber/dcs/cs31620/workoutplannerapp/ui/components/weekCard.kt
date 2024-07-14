package uk.ac.aber.dcs.cs31620.workoutplannerapp.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.workoutplannerapp.R
import uk.ac.aber.dcs.cs31620.workoutplannerapp.model.SaveDayData
import uk.ac.aber.dcs.cs31620.workoutplannerapp.model.WorkoutDay
import uk.ac.aber.dcs.cs31620.workoutplannerapp.ui.navigation.Screen

//This creates the card formatting and functionality for the home page which contains
//the weekly planner that has each of the days of the week on it.

//They can be switched on and off with a switch that will also change some visual aspects
//to notify the user that the selected day is now a "Rest Day".

//The edit button allows the user to change the exercise focus and the time (in hours)
//for a set day.

//Clicking on the rest of the card will take the user to the exercises that have been set for
//that day. The exercises can be added, removed and edited in that part of the app.

@Composable
fun WeekCard(
    modifier: Modifier = Modifier,
    dayOfWeek: WorkoutDay,
    time: WorkoutDay,
    exerciseFocus: WorkoutDay,
    isSelected: WorkoutDay,
    navController: NavHostController,
    savedDay: SaveDayData
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .height(100.dp)
    ){
        ConstraintLayout(
            modifier = modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            val (clickAreaRef, dayOfWeekRef, timeRef, exerciseRef, editRef, selectRef) = createRefs()

//adds a box over the card that is clickable to take you to the daily workouts page.
//the day is saved in a view model
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(350.dp)
                    .constrainAs(clickAreaRef) {}
                    .clickable {
                        navController.navigate(Screen.DailyWorkouts.route){
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        savedDay.setDay(dayOfWeek.dayOfWeek)
                        Log.d("Saved day before change", savedDay.getDay())
                    }
            ){
            }

//Day of the week
            Text(text = dayOfWeek.dayOfWeek,
                fontWeight = FontWeight.Bold, //maybe change
                fontSize = 30.sp,
                modifier = Modifier
                    .constrainAs(dayOfWeekRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)

                    }
            )
//Session length (in hours)
//will remove time depending on whether it is a rest day or not
            var timeAvailable by rememberSaveable{ mutableStateOf("") }
            Text(text = timeAvailable,
                fontSize = 25.sp,
                modifier = Modifier
                    .constrainAs(timeRef){
                        bottom.linkTo(parent.bottom)
                        start.linkTo(exerciseRef.end, margin = 10.dp)
                    }
            )
//exercise focus
//will change to "REST DAY" if the switch is off
            var isRestDay by rememberSaveable{mutableStateOf("")}

            Text(text = isRestDay,
                fontSize = 25.sp,
                modifier = Modifier
                    .constrainAs(exerciseRef){
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
            )
//Edit button
//when clicked a dialog will appear that will allow the user to change the session
//length and the session focus.
            var showDialog by remember { mutableStateOf(false)}
            IconButton(
                onClick = { showDialog = true },
                modifier = modifier
                    .constrainAs(editRef) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = stringResource(R.string.edit_workout),
                    modifier = modifier
                        .size(30.dp)
                )
            }

            if (showDialog) {
//opens a dialog to edit the data in a day session
                EditDay(
                    onDismiss = {showDialog = false},
                    onConfirm = {updatedWorkoutDay ->
                        val updatedTime = updatedWorkoutDay.time
                        val updatedExerciseFocus = updatedWorkoutDay.exerciseFocus

                        time.time = updatedTime
                        exerciseFocus.exerciseFocus = updatedExerciseFocus


                        showDialog = false
                    },
                    dayOfWeek = dayOfWeek,
                    selected = isSelected

                )
            }
//Select on/off button
            var checked by remember { mutableStateOf(isSelected.isSelected) }
            Switch(
                modifier = Modifier
                    .constrainAs(selectRef){
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    },
                checked = checked,
                onCheckedChange = {
                    checked = it
                }
            )
//Check if the switch is on or off and change variables accordingly to display
//the correct data as well as updating the appropriate workoutDays objects.
            if(checked){
                isRestDay = exerciseFocus.exerciseFocus
                timeAvailable = "${time.time}" + " Hour(s)"
                isSelected.isSelected = true
            }else{
                isRestDay = "REST DAY"
                isSelected.isSelected = false
                timeAvailable = ""
            }
        }
    }
}


@Preview
@Composable
fun WeekCardPreview() {
    val sampleWorkoutDay = WorkoutDay(
        dayOfWeek = "Monday",
        time = 2,
        exerciseFocus = "Strength Training",
        isSelected = true

    )
    val navController = rememberNavController()
    val viewModel: SaveDayData = viewModel()
    WeekCard(
        dayOfWeek = sampleWorkoutDay,
        time = sampleWorkoutDay,
        exerciseFocus = sampleWorkoutDay,
        isSelected = sampleWorkoutDay,
        navController = navController,
        savedDay = viewModel
    )
}

