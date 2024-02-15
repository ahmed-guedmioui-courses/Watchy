package com.ahmedapps.watchy.auth.presentation.signin

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahmedapps.watchy.R
import com.ahmedapps.watchy.auth.util.AuthResult
import com.ahmedapps.watchy.main.presentation.core.MainActivity
import com.ahmedapps.watchy.ui.theme.font
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    activity: MainActivity,
    onAuthorized: () -> Unit,
    onSignUpClick: () -> Unit,
) {

    BackHandler {
        activity.finish()
    }

    val signInViewModel: SignInViewModel = hiltViewModel()
    val signInState = signInViewModel.signInState.collectAsState().value

    val context = LocalContext.current

    LaunchedEffect(signInState, context) {
        signInViewModel.authResultChannel.collectLatest { result ->
            when (result) {
                is AuthResult.Authorized -> {
                    onAuthorized()
                }

                is AuthResult.Unauthorized -> {
                }

                is AuthResult.UnknownError -> {
                    Toast.makeText(
                        context,
                        context.getString(com.ahmedapps.watchy.R.string.invalid_email_or_password),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is AuthResult.SingedOut -> {
                }
            }
        }
    }

    LaunchedEffect(signInViewModel.invalidCredentialsToastChannel) {
        signInViewModel.invalidCredentialsToastChannel.collectLatest { showToasts ->
            if (showToasts) {
                Toast.makeText(
                    context,
                    context.getString(com.ahmedapps.watchy.R.string.invalid_credentials),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Spacer(modifier = Modifier.height(50.dp))

        Image(
            modifier = Modifier
                .size(180.dp)
                .clip(RoundedCornerShape(20.dp))
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.app_icon),
            contentDescription = stringResource(R.string.app_icon)
        )
        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = stringResource(com.ahmedapps.watchy.R.string.sign_in_to_your_account),
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = font,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(com.ahmedapps.watchy.R.string.sign_in),
            fontSize = 16.sp,
            fontFamily = font,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))


        TextField(
            value = signInState.signInEmail,
            onValueChange = {
                signInViewModel.onEvent(SignInUiEvent.SignInEmailChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = stringResource(com.ahmedapps.watchy.R.string.email),
                    fontFamily = font
                )
            },
            textStyle = TextStyle(
                fontFamily = font,
                fontSize = 15.sp
            ),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(16.dp))

        var passwordVisible by rememberSaveable {
            mutableStateOf(false)
        }

        TextField(
            value = signInState.signInPassword,
            onValueChange = {
                signInViewModel.onEvent(SignInUiEvent.SignInPasswordChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = stringResource(com.ahmedapps.watchy.R.string.password),
                    fontFamily = font
                )
            },
            textStyle = TextStyle(
                fontFamily = font,
                fontSize = 16.sp
            ),
            maxLines = 1,
            visualTransformation = if (passwordVisible)
                VisualTransformation.None
            else PasswordVisualTransformation(),

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Rounded.Visibility
                else Icons.Rounded.VisibilityOff

                val description =
                    if (passwordVisible) stringResource(com.ahmedapps.watchy.R.string.hide_password)
                    else stringResource(com.ahmedapps.watchy.R.string.show_password)

                IconButton(onClick = {
                    passwordVisible = !passwordVisible
                }) {
                    Icon(
                        imageVector = image,
                        contentDescription = description
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(22.dp))

        if (signInState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = {
                    signInViewModel.onEvent(SignInUiEvent.SignIn)
                },
            ) {
                Text(
                    text = stringResource(com.ahmedapps.watchy.R.string.sign_in),
                    fontSize = 16.sp,
                    fontFamily = font
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(
                text = stringResource(com.ahmedapps.watchy.R.string.don_t_have_an_account),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = stringResource(com.ahmedapps.watchy.R.string.sign_up),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                fontFamily = font,
                modifier = Modifier.clickable {
                    onSignUpClick()
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

    }
}












