package com.ontask.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun CreateContractPage(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        content = {
            CreateContractPageContents(navController = navController)
        }
    )
}

@Composable
fun CreateContractPageContents(navController: NavHostController) {
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
            Text(
                text = "{child name}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Row() {
                Image(
                    painter = painterResource(id = R.drawable.son),
                    contentDescription = "child icon",
                    modifier = Modifier
                        .height(70.dp)
                        .width(70.dp)
                        .padding(10.dp)
                        .clickable { navController.navigate("childProfile_screen") }
                )

                Spacer(modifier = Modifier.width(190.dp))

                Image(
                    painter = painterResource(id = R.drawable.contract_icon),
                    contentDescription = "target icon",
                    modifier = Modifier
                        .height(70.dp)
                        .width(70.dp)
                        .padding(10.dp)
                )
            }

            Column(
                modifier = Modifier.verticalScroll(state = rememberScrollState())
            ) {
                Row() {
                    Image(
                        painter = painterResource(id = R.drawable.target_icon),
                        contentDescription = "target icon",
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                            .padding(10.dp)
                    )

                    Row(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(
                            text = "Target",
                            fontSize = 16.sp
                        )
                    }
                }

                Modifier.padding(3.dp)
                    .fillMaxWidth(0.72f)
                    .apply {
                        maxPoints(this, LocalFocusManager.current)
                    }

                Row() {
                    Column() {
                        Award(1)
                        Modifier.padding(3.dp)
                            .fillMaxWidth(0.6f)
                            .apply {
                                AwardInput(this, LocalFocusManager.current)
                            }
                    }
                    Column() {
                        Points()
                        Modifier.padding(3.dp)
                            .fillMaxWidth(1f)
                            .apply {
                                PointsInput(this, LocalFocusManager.current)
                            }
                    }
                }

                Row() {
                    Column() {
                        Award(2)
                        Modifier.padding(3.dp)
                            .fillMaxWidth(0.6f)
                            .apply {
                                AwardInput(this, LocalFocusManager.current)
                            }
                    }
                    Column() {
                        Points()
                        Modifier.padding(3.dp)
                            .fillMaxWidth(1f)
                            .apply {
                                PointsInput(this, LocalFocusManager.current)
                            }
                    }
                }

                Row() {
                    Column() {
                        Award(3)
                        Modifier.padding(3.dp)
                            .fillMaxWidth(0.6f)
                            .apply {
                                AwardInput(this, LocalFocusManager.current)
                            }
                    }
                    Column() {
                        Points()
                        Modifier.padding(3.dp)
                            .fillMaxWidth(1f)
                            .apply {
                                PointsInput(this, LocalFocusManager.current)
                            }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        //TODO this + button needs to add a row for the award/points thing
                    }, modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF689FEC))
                ) {
                    Text(
                        text = "+",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row() {
                    Image(
                        painter = painterResource(id = R.drawable.description_icon),
                        contentDescription = "description icon",
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                            .padding(10.dp)
                    )

                    Row(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(
                            text = "Description",
                            fontSize = 16.sp
                        )
                    }
                }

                Modifier.padding(3.dp)
                    .fillMaxWidth(1f)
                    .apply {
                        DescriptionBox(this, LocalFocusManager.current)
                    }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        //TODO
                    },
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF689FEC))
                ) {
                    Text(
                        text = "Set contract",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 25.sp,
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
fun maxPoints(modifier: Modifier = Modifier, localFocusManager: FocusManager): String {
    var maxPoints by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(value = maxPoints,
        onValueChange = { maxPoints = it },
        label = {
            Text(
                text = "Maximum points",
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

    return maxPoints
}

@Composable
fun Award(awardNum: Int) {
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
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = "Award $awardNum:",
                fontSize = 16.sp
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AwardInput(modifier: Modifier = Modifier, localFocusManager: FocusManager): String {
    var reward by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(value = reward,
        onValueChange = { reward = it },
        label = {
            Text(
                text = "Reward",
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

    return reward
}

@Composable
fun Points() {
    Row() {
        Image(
            painter = painterResource(id = R.drawable.medal_icon),
            contentDescription = "medal icon",
            modifier = Modifier
                .height(50.dp)
                .width(50.dp)
                .padding(10.dp)
        )

        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = "Points:",
                fontSize = 16.sp
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PointsInput(modifier: Modifier = Modifier, localFocusManager: FocusManager): String {
    var points by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(value = points,
        onValueChange = { points = it },
        label = {
            Text(
                text = "Points",
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

    return points
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DescriptionBox(modifier: Modifier = Modifier, localFocusManager: FocusManager): String {
    var descriptionText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(value = descriptionText,
        onValueChange = { descriptionText = it },
        label = {
            Text(
                text = "Contract description",
                color = Color(0xff878787),
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { localFocusManager.moveFocus(FocusDirection.Down) }),
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(1f)
            .defaultMinSize(100.dp), // TODO: is it possible to make this textbox bigger?
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xff656565),
            unfocusedBorderColor = Color(0xff989898)
        ),
        textStyle = TextStyle(color = Color(0xff878787), fontFamily = FontFamily.SansSerif)
    )

    return descriptionText
}
