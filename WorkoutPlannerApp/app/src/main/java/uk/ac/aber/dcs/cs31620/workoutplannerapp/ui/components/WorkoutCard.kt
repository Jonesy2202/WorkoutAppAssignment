package uk.ac.aber.dcs.cs31620.workoutplannerapp.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import uk.ac.aber.dcs.cs31620.workoutplannerapp.R
import uk.ac.aber.dcs.cs31620.workoutplannerapp.model.Workout
import uk.ac.aber.dcs.cs31620.workoutplannerapp.model.WorkoutList

//Displays all of the data in a card for the user to see about their single workout.
// this is called multiple times in a lazycolumn to make a column of cards
@Composable
fun WorkoutCard(
    modifier: Modifier = Modifier,
    exerciseName: Workout,
    repetitions: Workout,
    sets: Workout,
    weight: Workout,
    workoutList: WorkoutList,
    onDelete: () -> Unit,
    isInDay: Boolean

) {
    var expanded by remember { mutableStateOf(false) }
    var cardSize by remember { mutableStateOf(70.dp) }
    val rotateArrow by animateFloatAsState(targetValue = if (expanded) 180f else 0f, label = "")

    Card(modifier = modifier
        .fillMaxSize()
        .height(cardSize),

        onClick = {
            expanded = !expanded
        }

    ) {
        ConstraintLayout(
            modifier = modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            val (dropsetRef, expandArrowRef, exerciseNameRef, repRef, setRef, weightRef, editRef, deleteRef) = createRefs()


//Exercise name
            Text(text = exerciseName.exerciseName, fontWeight = FontWeight.Bold, //maybe change
                fontSize = 30.sp, modifier = Modifier.constrainAs(exerciseNameRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    })
            IconButton(modifier = Modifier
                .rotate(rotateArrow)
                .constrainAs(expandArrowRef) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }, onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Filled.ArrowDownward,
                    contentDescription = stringResource(R.string.expand_card_arrow),
                    modifier = modifier.size(30.dp)
                )
            }
//Displays repetitions
//will remove time depending on whether it is a rest day or not
            if (expanded) {

//change card size to fit content
                cardSize = 160.dp

                Text(text = "Repetitions: " + "${repetitions.repetitions}",
                    fontSize = 25.sp,
                    modifier = Modifier.constrainAs(repRef) {
                            top.linkTo(exerciseNameRef.bottom)
                            start.linkTo(exerciseNameRef.start)
                        })
//number of sets

                Text(text = "Sets: " + "${sets.sets}",
                    fontSize = 25.sp,
                    modifier = Modifier.constrainAs(setRef) {
                            top.linkTo(repRef.bottom)
                            start.linkTo(repRef.start)
                        })

//Weight in Kg
                Text(text = "Weight: " + "${weight.weight}" + "Kg",
                    fontSize = 25.sp,
                    modifier = Modifier.constrainAs(weightRef) {
                            top.linkTo(setRef.bottom)
                            start.linkTo(setRef.start)
                        })
//Edit button
//Edit exercise with a dialog
                var showDialog by remember { mutableStateOf(false) }
                IconButton(onClick = { showDialog = true },
                    modifier = modifier.constrainAs(editRef) {
                            top.linkTo(expandArrowRef.bottom, margin = 1.dp)
                            end.linkTo(parent.end)
                        }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = stringResource(R.string.edit_workout),
                        modifier = modifier.size(30.dp)
                    )
                }
                if (showDialog) {
//opens up a dialog to add or edit workouts depending on whether addOrEdit is true or false
//false = edit
                    AddOrEditWorkout(
                        addOrEdit = false,
                        onDismiss = { showDialog = false },
                        onConfirm = { updatedWorkout ->
                            var updatedExerciseName = updatedWorkout.exerciseName
                            var updatedReps = updatedWorkout.repetitions
                            var updatedSets = updatedWorkout.sets
                            var updatedWeight = updatedWorkout.weight

                            exerciseName.exerciseName = updatedExerciseName
                            repetitions.repetitions = updatedReps
                            sets.sets = updatedSets
                            weight.weight = updatedWeight

                            showDialog = false
                        },

                        )
                }
//DeleteButton
                IconButton(onClick = {
                    onDelete.invoke()
                }, modifier = modifier.constrainAs(deleteRef) {
                        top.linkTo(editRef.bottom, margin = 1.dp)
                        end.linkTo(parent.end)
                    }) {
                    Icon(
                        imageVector = Icons.Filled.DeleteOutline,
                        contentDescription = stringResource(R.string.delete_workout),
                        modifier = modifier.size(30.dp)
                    )
                }

                if(isInDay){
                    var showDialog1 by remember { mutableStateOf(false) }
                    IconButton(
                        onClick = { showDialog1 = true },
                        modifier = Modifier
                            .constrainAs(dropsetRef){
                                top.linkTo(deleteRef.top, margin = 1.dp)
                                end.linkTo(deleteRef.start)
                            }
                    ){
                        Icon(
                            imageVector = Icons.Filled.LocalFireDepartment,
                            contentDescription = stringResource(R.string.dropset),
                            modifier = modifier.size(30.dp)
                        )
                    }
                    if(showDialog1){
                        ShowDropset(
                            onDismiss = { showDialog1 = false },
                            weight = weight.weight
                        )
                    }
                }
            } else {
                cardSize = 60.dp
            }
        }
    }
}