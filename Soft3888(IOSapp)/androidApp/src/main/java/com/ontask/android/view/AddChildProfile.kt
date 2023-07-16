package com.ontask.android

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import viewModel.AddChildProfileViewModel
import java.util.*

@Composable
fun addChildProfile(navController: NavHostController, auth: FirebaseAuth) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        content = {
            addChildProfileContents(navController = navController, auth = auth)
        }
    )
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun addChildProfileContents(navController: NavHostController, auth: FirebaseAuth) {
    var child_name: String by remember { mutableStateOf("") }
    var child_birthdate: String by remember { mutableStateOf("") }

    var paddingState by remember { mutableStateOf(16.dp) }
    val padding by animateDpAsState(
        targetValue = paddingState,
        tween(durationMillis = 1000)
    )

    val context = LocalContext.current
    val addChildProfileViewModel = AddChildProfileViewModel()

    Box(modifier = Modifier
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
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.add_child_profile_icon),
                    contentDescription = "Add child profile icon",
                    modifier = Modifier
                        .height(150.dp)
                        .padding(10.dp)
                        .clickable { navController.navigate("dashboard_screen") }
                )
            }

            Text(
                text = "Add a child profile",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 2.dp, bottom = 16.dp),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                ),
                color = Color.Black
                //color = Color(0xFFADADAD)
            )

            val localFocusManager = LocalFocusManager.current
            Modifier.padding(3.dp)
                .fillMaxWidth(0.72f)
                .apply {
                    child_name = childNameInput(this, localFocusManager)
                    child_birthdate = childDateOfBirth(this, localFocusManager)
                    //themesDropDown(this) // TODO: uncomment this for the themes dropdown menu
                }

            Button(
                onClick = {
                    // add child to firestore database
                    addChildProfileViewModel.addChild(auth, child_name, child_birthdate, "", context)
                    Toast.makeText(context, "child successfully added", Toast.LENGTH_SHORT).show()

                    // Then move to the dashboard.
                    navController.navigate("dashboard_screen")

                },
                modifier = Modifier
                    .fillMaxWidth(0.71f)
                    .height(50.dp),
                    //.padding(10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF689FEC)),
            ) {
                Text(
                    text = "Add child",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                    ),
                    color = Color.White
                )
            }
        }
    }
}

// reference: https://www.geeksforgeeks.org/drop-down-menu-in-android-using-jetpack-compose/
@Composable
fun themesDropDown(modifier: Modifier) {
    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var mExpanded by remember { mutableStateOf(false) }

    // Create a string value to store the selected city
    var mSelectedText by remember { mutableStateOf("") }

    //var mTextFieldSize by remember { mutableStateOf(Size.Zero)}

    // Create a list of cities TODO change this to the themes available. --> have this call the list of themes from backend?
    val mCities = listOf("Delhi", "Mumbai", "Chennai", "Kolkata", "Hyderabad", "Bengaluru", "Pune")

    // Up Icon when expanded and down icon when collapsed
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    // Create an Outlined Text Field
    // with icon and not expanded
    OutlinedTextField(
        value = mSelectedText,
        onValueChange = { mSelectedText = it },
        modifier = modifier,
        label = {
            Text(
                text = "Select theme",
                color = Color(0xff878787),
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp
            )
        },
        trailingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "contentDescription",
                Modifier.clickable { mExpanded = !mExpanded })
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xff656565),
            unfocusedBorderColor = Color(0xff989898)
        ),
        textStyle = TextStyle(color = Color(0xff878787), fontFamily = FontFamily.SansSerif)
    )

    // Create a drop-down menu with list of cities,
    // when clicked, set the Text Field text as the city selected
    DropdownMenu(
        expanded = mExpanded,
        onDismissRequest = { mExpanded = false },
        //modifier = Modifier
        //.width(with(LocalDensity.current){mTextFieldSize.width.toDp()})
    ) {
        mCities.forEach { label ->
            DropdownMenuItem(onClick = {
                mSelectedText = label
                mExpanded = false
            }) {
                Text(text = label)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun childNameInput(modifier: Modifier, localFocusManager: FocusManager): String {
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

// reference: https://www.geeksforgeeks.org/date-picker-in-android-using-jetpack-compose/
@Composable
fun childDateOfBirth(modifier: Modifier, localFocusManager: FocusManager): String {
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
