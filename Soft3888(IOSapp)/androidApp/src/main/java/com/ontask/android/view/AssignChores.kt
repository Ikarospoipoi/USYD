package com.ontask.android

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ontask.model.Achievement
import com.ontask.model.ChoreTask

@Composable
fun assignChoresPage(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        content = {
            assignChoresPageContents(navController = navController)
        }
    )
}

@Composable
fun assignChoresPageContents(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .focusable()
            .background(color = Color.White)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.padding(10.dp))
            }

            // TODO: get a list of chores
            // dummy data
            val choreList = arrayOf(
                ChoreTask("1", "Wash the dishes", "dishes", Achievement(1, ""), "dish_washing_icon.png"),
                ChoreTask("1", "Make bed", "bed", Achievement(1, ""), "bed_icon.png"),
                ChoreTask("1", "Clean room", "clean", Achievement(1, ""), "vacuum_icon.png")
            )

            for (chore in choreList)
                choreCard(choreTask = chore)

            Button(
                onClick = {
                    navController.navigate("childProfile_screen")
                }, modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF689FEC)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Done",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun choreCard(choreTask: ChoreTask) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val cardWidth = screenWidth - 30.dp

    Card(
        modifier = Modifier
            .size(width = cardWidth, height = 140.dp),
        border = BorderStroke(1.5.dp, Color.Gray),
        elevation = 10.dp, // shadow around box
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ){
            Row() {
                Image(
                    painter = painterResource(id = R.drawable.dish_washing_icon), //TODO: the icon needs to be different for each chore
                    contentDescription = "chore icon",
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp)
                )
                Spacer(modifier = Modifier.padding(5.dp))

                Box(modifier = Modifier.padding(8.dp)) {
                    Text(text = choreTask.name, fontSize = 16.sp)
                }
            }

            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                //TODO: make it so that if the user clicks one button and then clicks another, the first button that is clicked automatically unclicks
                onePointButton()
                Spacer(modifier = Modifier.padding(23.dp))
                twoPointButton()
                Spacer(modifier = Modifier.padding(23.dp))
                threePointButton()
            }
        }
    }
    Spacer(modifier = Modifier.padding(10.dp))
}

// reference: https://stackoverflow.com/questions/66048620/how-to-change-button-background-color-on-click-of-the-button
@Composable
fun onePointButton() {

    var selected by remember { mutableStateOf(false) }
    val color = if (selected) Color(0xffA195E9) else Color.White

    Button(
        onClick = { selected = !selected },
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        modifier = Modifier
            .size(40.dp),
        shape = CircleShape,
        border = BorderStroke(0.5.dp, Color.Gray)
    ) {
        Text(text = "1")
    }
}

// reference: https://stackoverflow.com/questions/66048620/how-to-change-button-background-color-on-click-of-the-button
@Composable
fun twoPointButton() {
    var selected by remember { mutableStateOf(false) }
    val color = if (selected) Color(0xffA195E9) else Color.White

    Button(
        onClick = { selected = !selected },
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        modifier = Modifier
            .size(40.dp),
        shape = CircleShape,
        border = BorderStroke(0.5.dp, Color.Gray)
    ) {
        Text(text = "2")
    }
}

// reference: https://stackoverflow.com/questions/66048620/how-to-change-button-background-color-on-click-of-the-button
@Composable
fun threePointButton() {
    var selected by remember { mutableStateOf(false) }
    val color = if (selected) Color(0xffA195E9) else Color.White

    Button(
        onClick = { selected = !selected },
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        modifier = Modifier
            .size(40.dp),
        shape = CircleShape,
        border = BorderStroke(0.5.dp, Color.Gray)
    ) {
        Text(text = "3")
    }
}
