package com.ahmedapps.watchy.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahmedapps.watchy.R
import com.ahmedapps.watchy.auth.util.AuthResult
import com.ahmedapps.watchy.ui.theme.font
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onSingOut: () -> Unit
) {

    val profileViewModel = hiltViewModel<ProfileViewModel>()
    val profileScreenState = profileViewModel.profileScreenState.collectAsState().value

    val context = LocalContext.current

    LaunchedEffect(profileViewModel.signOutResultChannel, context) {
        profileViewModel.signOutResultChannel.collectLatest { result ->
            when (result) {
                is AuthResult.Authorized -> {
                }

                is AuthResult.Unauthorized -> {
                }

                is AuthResult.UnknownError -> {
                }

                is AuthResult.SingedOut -> {
                    onSingOut()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text(
                    modifier = Modifier.align(Center),
                    text = stringResource(R.string.profile),
                    fontFamily = font,
                    fontSize = 20.sp
                )
            }
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    start = 32.dp,
                    end = 32.dp,
                    top = it.calculateTopPadding()
                ),
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .size(180.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .align(CenterHorizontally),
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = profileScreenState.name.take(1),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontFamily = font,
                    fontSize = 80.sp
                )
            }

            Spacer(modifier = Modifier.height(64.dp))

            Text(
                modifier = Modifier
                    .alpha(0.7f),
                text = stringResource(R.string.name),
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = font,
                fontSize = 16.sp,
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = profileScreenState.name,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = font,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                modifier = Modifier
                    .alpha(0.7f),
                text = stringResource(R.string.email),
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = font,
                fontSize = 16.sp,
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = profileScreenState.email,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = font,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )


            Spacer(modifier = Modifier.height(64.dp))

            Button(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(horizontal = 50.dp),
                onClick = {
                    profileViewModel.onEvent(ProfileUiEvent.SignOut)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = stringResource(R.string.sign_out),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}







