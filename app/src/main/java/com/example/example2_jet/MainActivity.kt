package com.example.example2_jet

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.example2_jet.ui.theme.Example2_JetTheme

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent()
        {
            Example2_JetTheme()
            {
                Surface(
                    color = MaterialTheme.colorScheme.background
                )
                {
                    SideEffect { Log.d("compose-log", "Surface") }
                    DRAWER()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DRAWER(
    modifier: Modifier = Modifier
        .padding(top = 20.dp, start = 30.dp),
    navController: NavHostController = rememberNavController(),
    startDestination: String = "main" // mainに変更
)
{
    SideEffect { Log.d("compose-log", "DRAWER") }
    Box(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        SideEffect { Log.d("compose-log", "Box") }
        NavHost(navController = navController, startDestination = startDestination)
        {
            mainScreen() // 先程の拡張関数 mainScreenを呼び出す
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview()
{
    Example2_JetTheme()
    {
        DRAWER()
    }
}