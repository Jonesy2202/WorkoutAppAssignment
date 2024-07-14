package uk.ac.aber.dcs.cs31620.workoutplannerapp.model

data class Workout(
    var exerciseName: String,
    var sets: Int,
    var repetitions: Int,
    var weight: Int
){

}

data class Day(
    val dayOfWeek: String,
    val workouts: MutableList<Workout> = mutableListOf()
)

