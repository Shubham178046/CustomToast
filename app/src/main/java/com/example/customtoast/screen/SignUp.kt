package com.example.customtoast.screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.customtoast.R
import com.example.customtoast.screen.ui.theme.CustomToastTheme
import com.example.customtoast.util.CommonUtil
import com.example.customtoast.util.MdToastType
import com.example.customtoast.viewmodel.UserViewModel

class SignUp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomToastTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    SignUpScreen(this)
                    val views: ComposeView = ComposeView(this)
                    views.setContent {

                    }
                }
            }
        }
    }
}

@Composable
fun SignUpScreen(context: ComponentActivity) {
    var viewModel: UserViewModel = UserViewModel()
    val nameValue = remember { mutableStateOf("") }
    val phoneValue = remember { mutableStateOf("") }
    val emailValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    val confirmPasswordValue = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }
    val confirmPasswordVisibility = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    var uiState = viewModel.uiState.observeAsState()
    var uiErrorMeg = viewModel.uiErrorMsg.observeAsState()
    if (uiState.value == 0) {
        CommonUtil.showToast(
            activity = context,
            message = uiErrorMeg.value.toString(),
            types = MdToastType.ERROR
        )
    } else if (uiState.value == 1) {
        CommonUtil.showToast(context, "Registration Sucessfull", MdToastType.SUCCESS)
        context.startActivity(
            Intent(context, MainActivity::class.java)
        )
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = "Sign Up",
                style = TextStyle(fontWeight = FontWeight.Bold, letterSpacing = 2.sp),
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.padding(10.dp))
            OutlinedTextField(
                value = nameValue.value,
                onValueChange = { nameValue.value = it },
                label = { Text(text = "Name") },
                placeholder = { Text(text = "Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Next) })
            )
            OutlinedTextField(
                value = emailValue.value,
                onValueChange = { emailValue.value = it },
                label = { Text(text = "Email Address") },
                placeholder = { Text(text = "Email Address") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Next) })
            )
            OutlinedTextField(
                value = phoneValue.value,
                onValueChange = { if (phoneValue.value.length < 10) phoneValue.value = it },
                label = { Text(text = "Phone Number") },
                placeholder = { Text(text = "Phone Number") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Next) })
            )
            OutlinedTextField(
                value = passwordValue.value,
                onValueChange = { passwordValue.value = it },
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility.value = passwordVisibility.value.not()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_password_toggle_foreground),
                            contentDescription = "",
                            tint = if (passwordVisibility.value) Color.Black else Color.Gray
                        )
                    }
                },
                label = { Text(text = "Password") },
                placeholder = { Text(text = "Password") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(onDone = { focusManager.moveFocus(focusDirection = FocusDirection.Next) }),
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            )
            OutlinedTextField(
                value = confirmPasswordValue.value,
                onValueChange = { confirmPasswordValue.value = it },
                trailingIcon = {
                    IconButton(onClick = {
                        confirmPasswordVisibility.value = confirmPasswordVisibility.value.not()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_password_toggle_foreground),
                            contentDescription = "",
                            tint = if (confirmPasswordVisibility.value) Color.Black else Color.Gray
                        )
                    }
                },
                label = { Text(text = "Confirm Password") },
                placeholder = { Text(text = "Confirm Password") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                visualTransformation = if (confirmPasswordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            )
            Spacer(modifier = Modifier.padding(20.dp))
            Button(
                onClick = {
                    viewModel.checkRegistrationValidation(
                        nameValue.value,
                        emailValue.value,
                        phoneValue.value,
                        passwordValue.value,
                        confirmPasswordValue.value
                    )
                }, modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
            ) {
                Text(text = "Sign Up", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Text(text = "Login Instead", modifier = Modifier.clickable {
                context.startActivity(
                    Intent(context, MainActivity::class.java)
                )
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    CustomToastTheme {
        SignUpScreen(SignUp())
    }
}