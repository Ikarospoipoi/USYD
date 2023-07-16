package com.ontask.android

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.ontask.model.Child
import com.ontask.model.Theme

@Composable
fun dashboardPage(navController: NavHostController, auth: FirebaseAuth) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        content = {
            dashboardPageContents(navController = navController, auth = auth)
        }
    )
}

@Composable
fun dashboardPageContents(navController: NavHostController, auth: FirebaseAuth) {
    // male/female icon
    Box(modifier = Modifier
        .fillMaxSize()
        .focusable()
        .background(color = Color.White)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.width(10.dp))

                Image(
                    painter = painterResource(id = R.drawable.woman),
                    contentDescription = "parent icon",
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp)
                        .padding(10.dp)
                        .clickable {
                            navController.navigate("parentProfile_screen")
                        }
                )

                Box(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "Welcome,\n{username}!", //TODO: put the parent's name here
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.size(width = 100.dp, height = 0.dp)) // TODO: the space here needs to be device-dependent OR make the floating action button bottom right overlayed the actual screen

                DashboardActionButton(navController)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.padding(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "family icon",
                    modifier = Modifier
                        .height(60.dp)
                        .width(60.dp)
                        .padding(10.dp)
                )

                Box(modifier = Modifier.padding(20.dp))
                {
                    Text(
                        text = "Family list",
                        fontSize = 17.sp
                    )
                }
            }

            // TODO: dummy data
            val childList = listOf(
                Child("1", "Anna", "", Theme(""), "daughter"),
                Child("2", "Bob", "", Theme(""), "son"),
                Child("3", "Carol", "", Theme(""), "daughter"),
                Child("4", "Denise", "", Theme(""), "daughter"),
                Child("5", "Emily", "", Theme(""), "daughter"),
                Child("6", "Felix", "", Theme(""), "son")
            )

            Column(
                modifier = Modifier.verticalScroll(state = rememberScrollState())
            ) {
                Spacer(modifier = Modifier.size(5.dp))

                for (child in childList)
                    childProfileCard(child = child,navController)
            }
        }
    }
}

@Composable
fun childProfileCard(child: Child, navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val cardWidth = screenWidth - 30.dp

    Card(
        modifier = Modifier
            .size(width = cardWidth, height = 120.dp)
            .clickable { /* TODO each card needs to be clickable to the relevant child profile. */
                navController.navigate("childProfile_screen")
            },
        border = BorderStroke(1.5.dp, Color.White),
        elevation = 10.dp, //shadow around box
        shape = RoundedCornerShape(10.dp)
    ) {
        Column() {
            Row() {
                Image(
                    painter = painterResource(id = R.drawable.daughter),
                    contentDescription = "daughter icon",
                    modifier = Modifier
                        .height(70.dp)
                        .width(70.dp)
                        .padding(10.dp)
                )

                Column(
                    modifier = Modifier
                        //.height(23.dp)
                        .padding(23.dp)
                ) {
                    Text(
                        text = child.name,
                        fontSize = 15.sp
                    )
                }
            }

            Row() {
                Image(
                    painter = painterResource(id = R.drawable.medal_icon),
                    contentDescription = "medal icon",
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp)
                        .padding(10.dp)
                )

                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = "{20}")
                }

                Image(
                    painter = painterResource(id = R.drawable.target_icon),
                    contentDescription = "target icon",
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp)
                        .padding(10.dp)
                )

                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = "{20}")
                }

                Image(
                    painter = painterResource(id = R.drawable.trophy_icon),
                    contentDescription = "trophy icon",
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp)
                        .padding(10.dp)
                )

                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = "{20}")
                }
            }
        }
    }
    Spacer(modifier = Modifier.padding(5.dp))
}

// reference: https://www.geeksforgeeks.org/floating-action-button-in-android-using-jetpack-compose/
@Composable
fun DashboardActionButton(
    navController: NavHostController
) {
    var showMenu by remember {
        mutableStateOf(false)
    }
    val list = listOf("Add Child","Add Chore")
    var context = LocalContext.current
    var selectedItem = remember{mutableStateOf("")}

    //    Column(
    //        modifier = Modifier
    //            .fillMaxSize()
    //            .fillMaxHeight()
    //            .fillMaxWidth()
    //            .padding(20.dp),
    //
    //        verticalArrangement = Arrangement.Bottom,
    //        horizontalAlignment = Alignment.End
    //    ) {}

    Box(){
        FloatingActionButton(
            onClick = {

                //TODO: click the plus button should do something here -- open up a menu with some options
                //navController.navigate("addChildProfile_screen")
                showMenu = !showMenu
            },
            backgroundColor = Color(0xff689FEC),
            contentColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation(15.dp),
            modifier = Modifier.padding(5.dp)
        ) {
            Icon(Icons.Filled.Add, "plus icon")
        }
        DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false/*TODO*/ }) {
            DropdownMenuItem(onClick = {navController.navigate("addChildProfile_screen")}) {
                Text(text = "Add Children")
            }

            DropdownMenuItem(onClick = { navController.navigate("addChore_screen") }) {
                Text(text = "Add Chores")
            }
        }
    }
}
