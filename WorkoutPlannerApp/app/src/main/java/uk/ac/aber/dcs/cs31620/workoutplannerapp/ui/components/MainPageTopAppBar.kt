package uk.ac.aber.dcs.cs31620.workoutplannerapp.ui.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import uk.ac.aber.dcs.cs31620.workoutplannerapp.R

// adds teh title at the top of the screen
@Composable
fun MainPageTopAppBar
    (onClick: () -> Unit = {})
{
    CenterAlignedTopAppBar(
        title = {
            Text(stringResource(id = R.string.workout_planner))
        }
    )
}

@Preview
@Composable
fun MainPageTopAppBarPreview() {
    MainPageTopAppBar()

}