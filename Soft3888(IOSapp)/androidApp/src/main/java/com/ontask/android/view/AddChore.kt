package com.ontask.android

import androidx.compose.foundation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun AddChoresPage(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        content = {
            AddChoresPageContents(navController = navController)
        }
    )
}

@Composable
//@Preview
fun AddChoresPageContents(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .focusable()
            .background(color = Color.White)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

            Row {
                Image(
                    painter = painterResource(id = R.drawable.cleaning),
                    contentDescription = "cleaning icon",
                    modifier = Modifier
                        .height(70.dp)
                        .width(70.dp)
                        .padding(10.dp)
                        .clickable { navController.navigate("dashboard_screen") }
                )

                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(
                        text = "Add chore",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Row {
                Image(
                    painter = painterResource(id = R.drawable.housekeeping),
                    contentDescription = "vacuum icon",
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .padding(10.dp)
                )

                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(
                        text = "Chore name",
                        fontSize = 16.sp
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {

                Modifier.padding(3.dp)
                    .fillMaxWidth(1f)
                    .apply {
                        AddChoreInput(this, LocalFocusManager.current)
                    }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        //TODO
                    }, modifier = Modifier
                        .fillMaxWidth(0.985f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF689FEC))
                ) {
                    Text(
                        text = "Add chore",
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
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddChoreInput(modifier: Modifier = Modifier, localFocusManager: FocusManager): String {
    var newChore by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(value = newChore,
        onValueChange = { newChore = it },
        label = {
            Text(
                text = "Chore name",
                color = Color(0xff878787),
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { localFocusManager.moveFocus(FocusDirection.Down) }),
        modifier = modifier,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xff656565),
            unfocusedBorderColor = Color(0xff989898)
        ),
        textStyle = TextStyle(color = Color(0xff878787), fontFamily = FontFamily.SansSerif)
    )

    return newChore
}

