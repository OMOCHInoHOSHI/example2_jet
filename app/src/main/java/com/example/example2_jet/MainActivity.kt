package com.example.example2_jet

import android.os.Bundle
import android.util.Log
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chaquo.python.Python
import com.example.example2_jet.ui.theme.Example2_JetTheme
import androidx.compose.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.example2_jet.ui.theme.Example2_JetTheme
import androidx.navigation.NavHost
import androidx.navigation.NavHostController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()

        // Pythonにより追加
        val py = Python.getInstance()
        val module = py.getModule("jikken")
        val tex1 = module.callAttr("hello_world")
        println(tex1)
        //ここもとりあえずいらない(?)
        //enableEdgeToEdge()
        setContent {
            Example2_JetTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DRAWER()
                }
            }
            //とりあえずこの辺空にしてAppScreen()作った方が楽
            //AppScreen()
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DRAWER(
    modifier: Modifier = Modifier
        .padding(top = 20.dp, start = 30.dp) ,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "main" // mainに変更
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        NavHost(navController = navController, startDestination = startDestination) {
            mainScreen() // 先程の拡張関数 mainScreenを呼び出す
        }
    }
}




//元あったやつは消す
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//

//ここはプレビューの表示に関わるところ(中身をAppScreen()に変える)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Example2_JetTheme {
        DRAWER()
    }
}