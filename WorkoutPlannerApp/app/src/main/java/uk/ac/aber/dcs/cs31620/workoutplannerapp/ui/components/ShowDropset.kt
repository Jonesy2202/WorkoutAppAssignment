package uk.ac.aber.dcs.cs31620.workoutplannerapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import uk.ac.aber.dcs.cs31620.workoutplannerapp.R

//shows the drop set option in a dialog with instructions on how to do it and the weights that can be used
@Composable
fun ShowDropset(
    onDismiss: () -> Unit,
    weight: Int
) {
    Dialog(
        onDismissRequest = { onDismiss() },
    ) {
        Surface(
            modifier = Modifier
                .width(300.dp)
                .height(400.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text(
                    text = stringResource(R.string.drop_set),
                    fontSize = 30.sp
                )

                Text(
                    text = "SET 1: " + "$weight" + "Kg",
                    fontSize = 25.sp
                )
                Text(
                    text = "SET 2: " + "${(weight * 0.75).toInt()}" + "Kg",
                    fontSize = 25.sp
                )
                Text(
                    text = "SET 3: " + "${(weight * 0.6).toInt()}" + "Kg",
                    fontSize = 25.sp
                )
                Text(
                    text = "1) Do as many reps as you can until failure.",
                    fontSize = 25.sp
                )
                Text(
                    text = "2) Leave a 10 second rest between each set.",
                    fontSize = 25.sp
                )
                Text(
                    text = "3) Leave 3 minutes between each drop set (full set of 3 sets)",
                    fontSize = 25.sp
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = { onDismiss() }
                    ) {
                        Text(text = stringResource(R.string.dismiss))
                    }
                }
            }
        }
    }
}