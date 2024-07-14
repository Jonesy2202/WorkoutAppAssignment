package uk.ac.aber.dcs.cs31620.workoutplannerapp.model

//These are all of the preset workouts that can be edited. the functions below add, remove
//and get all workouts so that the data can be used and edited.
class WorkoutList {

    fun addWorkout(workout: Workout) {
        workouts.add(workout)
    }

    fun getAllWorkouts(): List<Workout> {
        return workouts.toList()
    }

    fun getWorkoutByName(exerciseName: String): Workout? {
        return workouts.find { it.exerciseName == exerciseName }
    }

    fun removeWorkoutByName(exerciseName: String): Boolean {
        val deleteWorkout = getWorkoutByName(exerciseName)

        if (deleteWorkout != null) {
            workouts.remove(deleteWorkout)
            return true
        }
        return false
    }

    private val workouts = mutableListOf<Workout>(
        Workout(
            "Dead lifts",
            3,
            10,
            100
        ),
        Workout(
            "Bench press",
            3,
            10,
            50
        ),
        Workout(
            "Bicep curls",
            3,
            10,
            15
        ),
        Workout(
            "Russian twists",
            3,
            10,
            20
        ),
        Workout(
            "Leg press",
            3,
            10,
            80
        ),
        Workout(
            "Lat pull-downs",
            3,
            10,
            70
        ),
        Workout(
            "Shoulder press",
            3,
            10,
            15
        ),
    )
}

//This stores all the exercises in the days for the day sessions it is a mutable list with a mutable
//list inside of it
class DayList(

) {
    private val days: MutableList<Day> = mutableListOf(
        Day(
            "Monday",
            mutableListOf(
                Workout(
                    "Shoulder press",
                    3,
                    10,
                    15
                ),
            )
        ),
        Day(
            "Tuesday",
            mutableListOf(
                Workout(
                    "Shoulder press",
                    3,
                    10,
                    15
                ),
            )
        ),
        Day(
            "Wednesday",
            mutableListOf(
                Workout(
                    "Shoulder press",
                    3,
                    10,
                    15
                ),
            )
        ),
        Day(
            "Thursday",
            mutableListOf(
                Workout(
                    "Shoulder press",
                    3,
                    10,
                    15
                ),
            )
        ),
        Day(
            "Friday",
            mutableListOf(
                Workout(
                    "Shoulder press",
                    3,
                    10,
                    15
                ),
            )
        ),
        Day(
            "Saturday",
            mutableListOf(
                Workout(
                    "Shoulder press",
                    3,
                    10,
                    15
                ),
            )
        ),
        Day(
            "Sunday",
            mutableListOf(
                Workout(
                    "Shoulder press",
                    3,
                    10,
                    15
                ),
            )
        )
    )

//These functions add, remove and get all the workouts inside of the class
    //they have to first get the day and then access the list inside of it.
    fun addWorkout(dayName: String, workout: Workout) {
        val day = days.find { it.dayOfWeek == dayName }
        day?.workouts?.add(workout)
    }

    fun removeWorkout(dayName: String, workout: Workout) {
        val day = days.find { it.dayOfWeek == dayName }
        day?.workouts?.remove(workout)
    }

    fun getAllWorkouts(dayName: String): List<Workout>? {
        val day = days.find { it.dayOfWeek == dayName }
        if (day != null) {
            return day.workouts.toList()
        }
        return null
    }
}
