package com.mfa.nav3test.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mfa.nav3test.navigation.Destination

@Composable
fun ProfileScreen(
    name: String,
    onNavigate: (Destination) -> Unit,
) {
    var times by rememberSaveable { mutableIntStateOf(0) }

    Column(
        Modifier
            .fillMaxSize()
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier.size(60.dp),
            imageVector = Icons.Default.Person,
            contentDescription = null,
        )

        Spacer(Modifier.height(8.dp))

        Text("Name: $name, changed: $times")

        Spacer(Modifier.height(8.dp))

        Row {
            OutlinedButton(
                onClick = {
                    times++
                    onNavigate(Destination.NameEditDialog)
                },
            ) {
                Text("Change Name")
            }

            Spacer(Modifier.width(4.dp))

            Button(
                onClick = {
                    times++
                    onNavigate(Destination.NameEditScreen)
                },
            ) {
                Text("Change photo")
            }
        }
    }
}

@Preview
@Composable
fun ProfilePreview() {
    ProfileScreen(name = "Cucuruxo", onNavigate = {})
}