package uk.ac.aber.dcs.cs31620.workoutplannerapp.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

//saves the day name to be passed to other pages so that it can be used to get the workouts
//for the right session as well as display the right day
class SaveDayData() : ViewModel(){
    private var savedDay by mutableStateOf(SaveDay("Monday"))

    fun setDay(day: String){
        savedDay = SaveDay(day)
    }

    fun getDay(): String {
        return savedDay.dayOfWeek
    }
}

//these are all the preset sessions
val workoutDays = listOf(
    WorkoutDay(
        "Monday",
        1,
        "Back and Biceps",
        true
    ),
    WorkoutDay(
        "Tuesday",
        1,
        "Back and Biceps",
        true
    ),
    WorkoutDay(
        "Wednesday",
        1,
        "Back and Biceps",
        false
    ),
    WorkoutDay(
        "Thursday",
        1,
        "Back and Biceps",
        true
    ),
    WorkoutDay(
        "Friday",
        1,
        "Back and Biceps",
        true
    ),
    WorkoutDay(
        "Saturday",
        1,
        "Back and Biceps",
        true
    ),
    WorkoutDay(
        "Sunday",
        1,
        "Back and Biceps",
        true
    )
)