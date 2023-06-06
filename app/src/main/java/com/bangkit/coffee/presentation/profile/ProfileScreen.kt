package com.bangkit.coffee.presentation.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bangkit.coffee.R
import com.bangkit.coffee.presentation.profile.components.ChangePasswordDialog
import com.bangkit.coffee.presentation.profile.components.EditProfileDialog
import com.bangkit.coffee.shared.components.pullrefresh.PullRefreshIndicator
import com.bangkit.coffee.shared.components.pullrefresh.pullRefresh
import com.bangkit.coffee.shared.components.pullrefresh.rememberPullRefreshState
import com.bangkit.coffee.shared.theme.AppTheme
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material3.fade
import com.google.accompanist.placeholder.material3.placeholder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    state: ProfileState = ProfileState(),
    actions: ProfileActions = ProfileActions()
) {
    val scrollState = rememberScrollState()
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> uri?.let { actions.updateAvatar(it) } },
    )

    val pullRefreshState = rememberPullRefreshState(state.refreshing, actions.refresh)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.profile)) }
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .pullRefresh(pullRefreshState)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 48.dp, bottom = 24.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    AsyncImage(
                        model = R.drawable.no_image,
                        contentDescription = stringResource(R.string.profile_photo),
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .testTag("Avatar")
                    )

                    FilledTonalIconButton(
                        onClick = {
                            singlePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                        modifier = Modifier.testTag("UpdateAvatar")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = stringResource(R.string.update_profile_photo)
                        )
                    }
                }

                Text(
                    text = state.user?.name ?: "Loading Name",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(bottom = 48.dp)
                        .placeholder(
                            visible = state.user == null,
                            highlight = PlaceholderHighlight.fade(),
                        )
                )

                ListItem(
                    modifier = Modifier
                        .clickable { actions.openEditProfile() }
                        .testTag("EditProfile"),
                    headlineContent = {
                        Text(text = stringResource(R.string.edit_profile))
                    },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = null
                        )
                    }
                )

                ListItem(
                    modifier = Modifier
                        .clickable { actions.openChangePassword() }
                        .testTag("ChangePassword"),
                    headlineContent = {
                        Text(text = stringResource(R.string.change_password))
                    },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Filled.Key,
                            contentDescription = null
                        )
                    }
                )

                Divider(thickness = 8.dp)

                ListItem(
                    modifier = Modifier
                        .clickable { actions.signOut() }
                        .testTag("SignOut"),
                    headlineContent = {
                        Text(text = stringResource(R.string.sign_out))
                    },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Filled.Logout,
                            contentDescription = null
                        )
                    },
                    colors = ListItemDefaults.colors(
                        headlineColor = Color.Red,
                        leadingIconColor = Color.Red,
                    )
                )
            }

            PullRefreshIndicator(
                state.refreshing,
                pullRefreshState,
                Modifier.align(Alignment.TopCenter)
            )
        }
    }

    if (state.editProfileVisible) {
        ProvideProfileActions(actions = actions) {
            EditProfileDialog(
                name = "Muhammad John Doe"
            )
        }
    }

    if (state.changePasswordVisible) {
        ProvideProfileActions(actions = actions) {
            ChangePasswordDialog()
        }
    }
}

@Composable
@Preview(name = "Profile", showBackground = true)
private fun ProfileScreenPreview() {
    AppTheme {
        ProfileScreen()
    }
}

