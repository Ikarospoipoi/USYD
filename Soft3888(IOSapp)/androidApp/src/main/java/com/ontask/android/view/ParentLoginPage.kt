package com.ontask.android

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.*
import androidx.constraintlayout.compose.*
import androidx.navigation.NavHostController

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.ontask.module.LoginModule


@Composable
fun parentLoginPage(navController: NavHostController,viewModel: LoginScreenViewModel = viewModel(), auth: FirebaseAuth) {

    val context = LocalContext.current
    val token = stringResource(R.string.web_client_id)


    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
            viewModel.signWithGoogleCredential(credential,navController)
        } catch (e: ApiException) {
            Log.w("TAG", "Google sign in failed", e)
        }
    }


    var login_username: String by remember { mutableStateOf("") }
    var login_password: String by remember { mutableStateOf("") }
    var paddingState by remember { mutableStateOf(16.dp) }
    val padding by animateDpAsState(
        targetValue = paddingState,
        tween(durationMillis = 1000)
    )


    Box(modifier = Modifier
        .fillMaxSize()
        .focusable()
        .background(color = Color.Transparent)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Image(
                    painter = painterResource(id = R.drawable.qr_code_icon),
                    contentDescription = "QR scan icon",
                    modifier = Modifier.height(50.dp).width(50.dp).padding(10.dp)
                )
            }

            Image(
                painter = painterResource(id = R.drawable.user_icon),
                contentDescription = "Parent icon",
                modifier = Modifier.height(150.dp).width(150.dp).padding(top = 4.dp)
            )

            Text(
                text = "Welcome back!",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp, bottom = 4.dp),
                style = TextStyle(
                    fontSize = 20.sp
                )
            )

            Row() {
                Text(
                    text = "If you don't have an account, ",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
                    style = TextStyle(
                        fontSize = 20.sp
                    )
                )
                ClickableText(
                    text = AnnotatedString("sign up"),
                    modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color(0.41f, 0.62f, 0.93f)
                    ),
                    onClick = {
                        // do something when sign up button clicked
                        navController.navigate("Register_screen")
                    }
                )
            }

            val localFocusManager = LocalFocusManager.current
            Modifier.padding(3.dp)
                .fillMaxWidth(0.8f)
                .onFocusChanged { focused ->
                    if (focused.isFocused) {
                        paddingState = 8.dp
                    } else {
                        paddingState = 16.dp
                    }
                }
                .apply {
                    login_username = emailInput(this, localFocusManager)
                    login_password = passwordInput(this, localFocusManager)
                }

            ClickableText(
                text = AnnotatedString("Forgot your password?"),
                modifier = Modifier.padding(top = padding, bottom = padding, start = 0.dp, end = 0.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color(0.41f, 0.62f, 0.93f)
                ),
                onClick = {
                    // do something when forgot password button clicked
                }
            )

            Button(
                onClick = {
                    val log = LoginModule()
                    var login_boolean: Boolean? = null
                    login_boolean = log.final_check(login_username,login_password)

                    if(login_boolean == true){
                        auth.signInWithEmailAndPassword(login_username,login_password).addOnCompleteListener { task->
                            if(task.isSuccessful){
                                Toast.makeText(context, "login valid and correct!", Toast.LENGTH_SHORT).show()
                                val user = auth.currentUser
                                // Then move to the dashboard.
                                navController.navigate("dashboard_screen")
                            }
                            else{
                                Toast.makeText(context, "login valid but failed!", Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                    else if(log.authenticate_1(login_username) == true && log.checkPassword(login_password)==false){
                        Toast.makeText(context, "login password invalid!", Toast.LENGTH_SHORT).show()
                    }
                    else if(log.authenticate_1(login_username) == false && log.checkPassword(login_password)==true){
                        Toast.makeText(context, "login username invalid!", Toast.LENGTH_SHORT).show()
                    }
                    else if(log.authenticate_1(login_username) == false && log.checkPassword(login_password)==false){
                        Toast.makeText(context, "login both invalid!", Toast.LENGTH_SHORT).show()
                        Toast.makeText(context, "password length: " + login_password.length, Toast.LENGTH_SHORT).show()
                    }

                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0.41f, 0.62f, 0.93f),
                    contentColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(50.dp)
            ) {
                Text(
                    text = "Log in",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
            }

            Row(modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    elevation = null,
                    onClick = {
                        Toast.makeText(context, "This button should redirect the user to a Google login page", Toast.LENGTH_SHORT).show()
                        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(token)
                            .requestEmail()
                            .build()

                        val googleSignInClient = GoogleSignIn.getClient(context, gso)
                        launcher.launch(googleSignInClient.signInIntent)
                    }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google_login_btn),
                        contentDescription = "Google logo icon",
                        modifier = Modifier.height(50.dp).width(50.dp).padding(10.dp)
                    )
                }
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    elevation = null,
                    onClick = {
                        Toast.makeText(context, "This button should redirect the user to a Facebook login page", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.facebook_login_btn),
                        contentDescription = "Facebook logo icon",
                        modifier = Modifier.height(50.dp).width(50.dp).padding(10.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun emailInput(modifier: Modifier = Modifier, localFocusManager: FocusManager): String {
    var email by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(value = email,
        onValueChange = { email = it },
        label = { Text(text = "Email address", textAlign = TextAlign.Center) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { localFocusManager.moveFocus(FocusDirection.Down) }),
        modifier = modifier,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.AlternateEmail,
                contentDescription = "phone leading icon",
                tint = Color(0xff656565)
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xff656565),
            unfocusedBorderColor = Color(0xff989898)
        )

    )

    return email
}

@Composable
fun passwordInput(modifier: Modifier, loaclFoucsManager: FocusManager): String{
    var password by remember { mutableStateOf("") }
    var passwordHidden by remember { mutableStateOf(true) }

    OutlinedTextField(value = password,
        onValueChange = { password = it },
        label = { Text(text = "Password") },
        visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {loaclFoucsManager.clearFocus()}),
        modifier = modifier,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = "password leading icon",
                tint = Color(0xff656565)
            ) },
        trailingIcon = {
            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                val visibilityIcon =
                    if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
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