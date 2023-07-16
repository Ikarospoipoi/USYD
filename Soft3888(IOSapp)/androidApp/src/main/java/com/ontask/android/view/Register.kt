package com.ontask.android

import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ontask.module.RegisterModule
import java.util.*


@Composable
fun Register(navController: NavHostController, auth: FirebaseAuth) {
    var signup_email: String by remember { mutableStateOf("") }
    var signup_password: String by remember { mutableStateOf("") }
    var signup_username: String by remember { mutableStateOf("") }
    var signup_birth: String by remember { mutableStateOf("") }
    var paddingState by remember { mutableStateOf(16.dp) }
    val padding by animateDpAsState(
        targetValue = paddingState,
        tween(durationMillis = 1000)
    )

    val context = LocalContext.current

    Box(modifier = Modifier
        .fillMaxSize()
        .focusable()
        .background(color = Color.Transparent)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {

            Text(
                text = "Let's get started",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 60.dp, bottom = 16.dp),
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                )
            )

            Text(
                text = "Create an account with On Task Achievers",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 2.dp, bottom = 16.dp),
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                ),
                color = Color(0xFFADADAD)
            )

            val localFocusManager = LocalFocusManager.current
            Modifier.padding(3.dp)
                .fillMaxWidth(0.72f)
                .apply {
                    signup_username = usernameInput(this, localFocusManager)
                    signup_email = registerEmailInput(this, localFocusManager)
                    signup_password = registerPasswordInput(this, localFocusManager)
                    signup_birth = dateOfBirth(this, localFocusManager)
                }

            Row(){
                Text(
                    text = "Have an account? ",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = padding, bottom = padding, start = padding, end = 0.dp),
                    fontSize = 16.sp
                )
                ClickableText(
                    text = AnnotatedString("Log in"),
                    modifier = Modifier.padding(top = padding, bottom = padding, end = padding, start = 0.dp),
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color(0xFF0000EE)
                    ),
                    onClick = {
                        // do something when sign up button clicked
                        navController.navigate("Login_screen")
                    }
                )
            }

// TODO: https://developer.android.com/jetpack/compose/layouts/material -- add shadow on the button?
            Button(
                onClick = {
                    val log = RegisterModule()
                    var signup_boolean: Boolean? = null

                    signup_boolean = log.final_check(signup_email,signup_password)
                    if(signup_boolean == true){
                        Toast.makeText(context, "Entering!", Toast.LENGTH_SHORT).show()
                        auth.createUserWithEmailAndPassword(signup_email,signup_password).addOnCompleteListener { task->
                            if(task.isSuccessful){
                                Toast.makeText(context, "signup valid and correct!", Toast.LENGTH_SHORT).show()
                                val user = auth.currentUser // get current user
                                // add user to firestore database

                                val userInfo = hashMapOf(
                                    "email" to signup_email,
                                    "name" to signup_username,
                                    "userID" to (user?.getUid() ?: ""),
                                    "dateOfBirth" to signup_birth
                                )

                                val db = Firebase.firestore
                                db.collection("users")
                                    .document((user?.getUid() ?: "").toString())
                                    .set(userInfo)
                                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                                    .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                                Toast.makeText(context, "signup valid and correct! database updated", Toast.LENGTH_SHORT).show()

                                // Then move to the dashboard.
                                navController.navigate("dashboard_screen")
                            }
                            else{
                                Toast.makeText(context, "signup valid but failed!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    else if(log.authenticate_1(signup_email) == false && log.checkPassword(signup_password) == true){
                        Toast.makeText(context, "signup email invalid!", Toast.LENGTH_SHORT).show()
                    }
                    else if(log.authenticate_1(signup_email) == true && log.checkPassword(signup_password) == false){
                        Toast.makeText(context, "signup password invalid!", Toast.LENGTH_SHORT).show()
                    }
                    else if(log.authenticate_1(signup_email) == false && log.checkPassword(signup_password) == false){
                        Toast.makeText(context, "signup email and password invalid!", Toast.LENGTH_SHORT).show()
                    }

                }, modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF689FEC))
            ) {
                Text(
                    text = "Register",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    color = Color.White
                )
            }

            Row(){
                Text(
                    text = "or continue with:",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = padding, bottom = padding, start = padding, end = 0.dp),
                    fontSize = 16.sp
                )
            }

            Row(modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    painter = painterResource(id = R.drawable.apple_login_btn),
                    contentDescription = "Apple logo icon",
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .padding(10.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.google_login_btn),
                    contentDescription = "Google logo icon",
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .padding(10.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.facebook_login_btn),
                    contentDescription = "Facebook logo icon",
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .padding(10.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun usernameInput(modifier: Modifier = Modifier, localFocusManager: FocusManager): String {
    var username by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(value = username,
        onValueChange = { username = it },
        label = {
            Text(
                text = "First name",
                color = Color(0xff878787),
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { localFocusManager.moveFocus(FocusDirection.Down) }),
        modifier = modifier,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "profile leading icon",
                tint = Color(0xff656565)
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xff656565),
            unfocusedBorderColor = Color(0xff989898)
        ),
        textStyle = TextStyle(color = Color(0xff878787), fontFamily = FontFamily.SansSerif)
    )

    return username

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun registerEmailInput(modifier: Modifier = Modifier, localFocusManager: FocusManager): String {
    var email by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = {
            Text(
                text = "Email address",
                color = Color(0xff878787),
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { localFocusManager.moveFocus(FocusDirection.Down) }),
        modifier = modifier,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.AlternateEmail,
                contentDescription = "email address leading icon",
                tint = Color(0xff656565)
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xff656565),
            unfocusedBorderColor = Color(0xff989898)
        ),
        textStyle = TextStyle(color = Color(0xff878787), fontFamily = FontFamily.SansSerif)
    )

    return email

}

@Composable
fun registerPasswordInput(modifier: Modifier, localFocusManager: FocusManager): String{
    var password by remember { mutableStateOf("") }
    var passwordHidden by remember { mutableStateOf(true) }

    OutlinedTextField(value = password,
        onValueChange = { password = it },
        label = {
            Text(
                text = "Password",
                color = Color(0xff878787),
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp
            )
        },
        visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {localFocusManager.clearFocus()}),
        modifier = modifier,
        leadingIcon = { Icon(
            imageVector = Icons.Filled.Lock,
            contentDescription = "password leading icon",
            tint = Color(0xff656565)
        ) },
        trailingIcon = {
            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                val visibilityIcon = if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                // Please provide localized description for accessibility services
                val description = if (passwordHidden) "Show password" else "Hide password"
                Icon(imageVector = visibilityIcon, contentDescription = description)
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xff656565),
            unfocusedBorderColor = Color(0xff989898)
        )
    )

    return password
}

// reference: https://www.geeksforgeeks.org/date-picker-in-android-using-jetpack-compose/
@Composable
fun dateOfBirth(modifier: Modifier, localFocusManager: FocusManager): String {
    // Fetching the Local Context
    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val mDate = remember { mutableStateOf("Date of Birth") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
        }, mYear, mMonth, mDay
    )

    OutlinedButton(
        onClick = {
            mDatePickerDialog.show()
        },
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(0.755f)
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        border = BorderStroke(1.dp, Color(0xff989898))
    )
    {
        Box(
            modifier = Modifier.fillMaxWidth(1f)
        ) {
            Row() {
                Icon(
                    imageVector = Icons.Filled.CalendarToday,
                    contentDescription = "calendar leading icon",
                    modifier = Modifier.size(25.dp),
                    tint = Color(0xff656565)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "${mDate.value}",
                    color = Color(0xff878787),
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }

    return mDate.value
}

