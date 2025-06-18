package com.mfa.nav3test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mfa.nav3test.navigation.NavigationRoot
import com.mfa.nav3test.ui.theme.Nav3TestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Nav3TestTheme {
                NavigationRoot()
            }
        }
    }
}
