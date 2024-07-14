package uk.ac.aber.dcs.cs31620.workoutplannerapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import uk.ac.aber.dcs.cs31620.workoutplannerapp.R
import uk.ac.aber.dcs.cs31620.workoutplannerapp.model.SaveDayData
import uk.ac.aber.dcs.cs31620.workoutplannerapp.model.Workout
import uk.ac.aber.dcs.cs31620.workoutplannerapp.model.WorkoutDay
import uk.ac.aber.dcs.cs31620.workoutplannerapp.model.WorkoutList

//show a dialog to change the session focus and the amount of time dedicated to the session
@Composable
fun EditDay(
    onDismiss: () -> Unit,
    onConfirm: (WorkoutDay) -> Unit,
    dayOfWeek: WorkoutDay,
    selected: WorkoutDay


) {
    val sessionFocus = rememberSaveable { mutableStateOf("") }
    val sessionLength = rememberSaveable { mutableStateOf("") }

    Dialog(
        onDismissRequest = { onDismiss() },
    ) {
        Surface(
            modifier = Modifier
                .width(300.dp)
                .height(240.dp), shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = dayOfWeek.dayOfWeek, fontSize = 30.sp
                )

//edit the session focus by renaming it
                OutlinedTextField(value = sessionFocus.value, label = {
                    Text(text = stringResource(R.string.change_session_focus))
                }, onValueChange = { newValue ->
                    sessionFocus.value = newValue
                })

//only allows numbers to be inputted when going to insert a time
//uses keyboard options to only allow numbers
//also converts the string into an int when inserted
                OutlinedTextField(value = sessionLength.value, label = {
                    Text(text = stringResource(R.string.change_session_length))
                }, onValueChange = { newValue ->
                    sessionLength.value = newValue
                }, keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {

//Dismiss button for the dialog
                    TextButton(onClick = { onDismiss() }) {
                        Text(text = stringResource(R.string.dismiss))
                    }
//Confirm button for the dialog
//checks that the fields are not blank then gets all the data that needs changing and gets it ready
//to pass to the function that called it.
                    TextButton(modifier = Modifier.padding(start = 30.dp), onClick = {
                        if (sessionFocus.value.isNotBlank() && sessionLength.value.isNotBlank()) {
                            val updatedTime = sessionLength.value.toInt()
                            val updatedSessionFocus = sessionFocus.value
                            val updatedSelected = selected.isSelected
                            val updatedWorkoutDay = WorkoutDay(
                                dayOfWeek.dayOfWeek,
                                updatedTime,
                                updatedSessionFocus,
                                updatedSelected
                            )
                            onConfirm(updatedWorkoutDay)
                        }
                        onDismiss()
                    }) {
                        Text(text = stringResource(R.string.confirm))
                    }
                }
            }
        }
    }
}

//gets a true or false when function is called to decide whether the function will display add or
//edit on the header and buttons.

//the data will not be processed in here, it is just used for the user input.

@Composable
fun AddOrEditWorkout(
    onDismiss: () -> Unit, onConfirm: (Workout) -> Unit, addOrEdit: Boolean
    //Add = true, edit = false
) {
    val exerciseName = rememberSaveable { mutableStateOf("") }
    val sets = rememberSaveable { mutableStateOf("") }
    val reps = rememberSaveable { mutableStateOf("") }
    val weight = rememberSaveable { mutableStateOf("") }

    var editOrAdd = if (!addOrEdit) {
        "Edit"
    } else {
        "Add"
    }
    Dialog(
        onDismissRequest = { onDismiss() },
    ) {
        Surface(
            modifier = Modifier
                .width(300.dp)
                .height(375.dp), shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = "$editOrAdd Exercise", fontSize = 30.sp
                )
//gets input from user for the exercise name
                OutlinedTextField(
                    value = exerciseName.value,
                    label = {
                        Text(text = stringResource(R.string.exercise_name))
                    },
                    onValueChange = { newValue ->
                        exerciseName.value = newValue
                    },
                )
//gets the reps from the user
                OutlinedTextField(value = reps.value, label = {
                    Text(text = stringResource(R.string.repetitions))
                }, onValueChange = { newValue ->
                    reps.value = newValue
                }, keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
                )
//gets the number of sets from the user
                OutlinedTextField(value = sets.value, label = {
                    Text(text = stringResource(R.string.sets))
                }, onValueChange = { newValue ->
                    sets.value = newValue
                }, keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
                )
//gets the weight from the user
                OutlinedTextField(value = weight.value, label = {
                    Text(text = stringResource(R.string.weight))
                }, onValueChange = { newValue ->
                    weight.value = newValue
                }, keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
//dismiss button
                    TextButton(onClick = { onDismiss() }) {
                        Text(text = stringResource(R.string.dismiss))
                    }
//Confirm button
                    TextButton(modifier = Modifier.padding(start = 30.dp), onClick = {
                        if (exerciseName.value.isNotBlank() && reps.value.isNotBlank() && sets.value.isNotBlank() && weight.value.isNotBlank()) {
                            val updatedExerciseName = exerciseName.value
                            val updatedReps = reps.value.toInt()
                            val updatedSets = sets.value.toInt()
                            val updatedWeight = weight.value.toInt()
                            val updatedWorkout = Workout(
                                exerciseName = updatedExerciseName,
                                repetitions = updatedReps,
                                sets = updatedSets,
                                weight = updatedWeight
                            )
                            onConfirm(updatedWorkout)
                        }

                        onDismiss()
                    }) {
                        Text(text = stringResource(R.string.confirm))
                    }
                }
            }
        }
    }
}

//creates a dialog that has a dropdownmenu in it to select an already created exercise.

@Composable
fun AddDayExercise(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    savedDay: SaveDayData,
    workoutList: WorkoutList

) {

//gets the workouts using the getAllWorkouts() function
//remembers whether the dropdown is expanded or not. default is false so it isnt automatically opened
//selectedWorkout is used as a way to get the suer input and then pass it to the function that called it
    var workouts by remember { mutableStateOf(workoutList.getAllWorkouts()) }
    var expanded by remember { mutableStateOf(false) }
    var selectedWorkout by remember { mutableStateOf("Select Workout") }

    Dialog(
        onDismissRequest = { onDismiss() },
    ) {
        Surface(
            modifier = Modifier
                .width(300.dp)
                .height(240.dp), shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = savedDay.getDay(), fontSize = 30.sp
                )
//used an outlinedtextfield for teh user to put in data
// the textfield then has an arrow that can drop down to form a dropdownmenu that has the
//alreasdy created exercises in it.
                OutlinedTextField(value = selectedWorkout,
                    onValueChange = {},
                    label = { Text(stringResource(R.string.select_workout)) },
                    trailingIcon = {
                        Icon(imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = stringResource(R.string.dropdown_arrow),
                            modifier = Modifier.clickable { expanded = !expanded })
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    workouts.forEach {
                        DropdownMenuItem(text = {
                            Text(
                                text = it.exerciseName, color = Color.Black, fontSize = 20.sp
                            )
                        }, onClick = {
                            selectedWorkout = it.exerciseName
                            expanded = false
                        }, modifier = Modifier.border(1.dp, Color.Black)

                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
//dismiss button
                    TextButton(onClick = { onDismiss() }) {
                        Text(text = stringResource(R.string.dismiss))
                    }
//confirm button that passes selectedWorkout when clicked
                    TextButton(modifier = Modifier.padding(start = 30.dp), onClick = {
                        onConfirm(selectedWorkout)
                        onDismiss()
                    }) {
                        Text(text = stringResource(R.string.confirm))
                    }
                }
            }

        }

    }
}
