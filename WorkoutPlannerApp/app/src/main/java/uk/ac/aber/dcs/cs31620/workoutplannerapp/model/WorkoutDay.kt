package uk.ac.aber.dcs.cs31620.workoutplannerapp.model

data class WorkoutDay (
    val dayOfWeek: String,
    var time: Int,
    var exerciseFocus: String,
    var isSelected: Boolean
){

}
data class SaveDay(
    var dayOfWeek: String
)