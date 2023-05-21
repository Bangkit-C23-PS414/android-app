package com.bangkit.coffee.presentation.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.bangkit.coffee.presentation.components.SimpleScreen

@Composable
fun ProfileScreen(
    state: ProfileState = ProfileState(),
    actions: ProfileActions = ProfileActions()
) {
    SimpleScreen(text = "Profile")
}

@Composable
@Preview(name = "Profile")
private fun ProfileScreenPreview() {
    ProfileScreen()
}

