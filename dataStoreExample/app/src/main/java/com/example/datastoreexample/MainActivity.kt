package com.example.datastoreexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.datastoreexample.ui.theme.DataStoreExampleTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val userDataStore = UserDataStore(context = this)
            val darkMode by userDataStore.isDarkMode.collectAsState(initial = false)
            DataStoreExampleTheme(
                darkTheme = darkMode
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(userDataStore = userDataStore, isDarkMode = darkMode)
                }
            }
        }
    }
}

@Composable
fun Greeting(
    userDataStore: UserDataStore,
    isDarkMode: Boolean
) {
    val scope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        var email by rememberSaveable { mutableStateOf("") }
        val userEmail by userDataStore.getEmail.collectAsState(initial = "")
        TextField(
            value = email,
            onValueChange = { email = it },
            keyboardOptions = KeyboardOptions().copy(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        )
        Spacer(modifier = Modifier.height(25.dp))
        Button(onClick = {
            scope.launch {
                userDataStore.saveEmail(email)
            }
        }) {
            Text("Guardar email")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(userEmail)
        Spacer(modifier = Modifier.height(32.dp))

        Switch(checked = isDarkMode, onCheckedChange = { isChecked ->
            scope.launch {
                userDataStore.saveDarkMode(isDarkMode = isChecked)
            }
        })

    }
}