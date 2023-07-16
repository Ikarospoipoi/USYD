package com.ontask.android.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ontask.android.R

class ChildProfile : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            childProfilePage()
        }
    }
}

@Composable
@Preview
fun childProfilePage() {
    Box(modifier = Modifier
        .fillMaxSize()
        .focusable()
        .background(color = Color.White)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxSize()
                .padding(20.dp)
        ) {
            Row() {
                Text(
                    text = "{Child name}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.size(width = 180.dp, height = 0.dp)) // TODO: this needs to be changed so that its on the right side of the screen dependent on screen size
                ActionButton()
            }

            Spacer(modifier = Modifier.height(5.dp))

            Row() {

                Image(
                    painter = painterResource(id = R.drawable.daughter), // TODO: the icons from dashboard page branch need to be copied over to this branch
                    contentDescription = "daughter icon",
                    modifier = Modifier
                        .height(70.dp)
                        .width(70.dp)
                        .padding(10.dp)
                )

                Row(
                    modifier = Modifier
                        .height(70.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.medal_icon),
                        contentDescription = "medal icon",
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                            .padding(10.dp)
                    )

                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(text = "{20}")
                    }

                    Image(
                        painter = painterResource(id = R.drawable.target_icon),
                        contentDescription = "target icon",
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                            .padding(10.dp)
                    )

                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(text = "{20}")
                    }

                    Image(
                        painter = painterResource(id = R.drawable.trophy_icon),
                        contentDescription = "trophy icon",
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                            .padding(10.dp)
                    )

                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(text = "20")
                    }
                }
            }

            Text(text = "Date of birth: {dob}")
            Spacer(modifier = Modifier.padding(1.dp))
            Text(text = "Theme: {theme}")

            OutlinedButton(
                onClick = {
                    // TODO
                },
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(1f)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                border = BorderStroke(1.dp, Color(0xff989898))
            ) {
                Text(
                    text = "Edit profile",
                    color = Color(0xff878787),
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            Row() {
                Image(
                    painter = painterResource(id = R.drawable.reward_icon),
                    contentDescription = "reward icon",
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .padding(10.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(13.dp)
                ) {
                    Text(text = "Reward List")
                }
            }

            val configuration = LocalConfiguration.current
            val screenWidth = configuration.screenWidthDp.dp
            val cardWidth = screenWidth - 30.dp

            // TODO: add some dummy data rewards that the child has received.
            Card(
                modifier = Modifier
                    .size(width = cardWidth, height = 120.dp),
                border = BorderStroke(1.dp, Color(0xff878787)),
                //elevation = 10.dp, // shadow around box
                shape = RoundedCornerShape(10.dp)
            ) {}

            Spacer(modifier = Modifier.height(5.dp))

            Row() {
                Image(
                    painter = painterResource(id = R.drawable.checklist_icon),
                    contentDescription = "checklist icon",
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .padding(10.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(13.dp)
                ) {
                    Text(text = "Finished chores")
                }
            }

            //TODO: list of completed chores
            Card(
                modifier = Modifier
                    .size(width = cardWidth, height = 120.dp),
                border = BorderStroke(1.dp, Color(0xff878787)),
                //elevation = 10.dp, // shadow around box
                shape = RoundedCornerShape(10.dp)
            ) {}
        }
    }
}

// reference: https://www.geeksforgeeks.org/floating-action-button-in-android-using-jetpack-compose/
@Composable
fun ActionButton() {
    //    Column(
    //        modifier = Modifier
    //            .fillMaxSize()
    //            .fillMaxHeight()
    //            .fillMaxWidth()
    //            .padding(20.dp),
    //
    //        verticalArrangement = Arrangement.Bottom,
    //        horizontalAlignment = Alignment.End
    //    ) {
    //    }
    FloatingActionButton(
        onClick = {
            //TODO: click the plus button should do something here -- open up a menu with some options
        },
        backgroundColor = Color(0xff689FEC),
        contentColor = Color.White,
        elevation = FloatingActionButtonDefaults.elevation(15.dp)
    ) {
        Icon(Icons.Filled.Add, "plus icon")
    }
}
