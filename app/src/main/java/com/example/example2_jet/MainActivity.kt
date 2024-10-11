package com.example.example2_jet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chaquo.python.Python
import com.example.example2_jet.ui.theme.Example2_JetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Pythonにより追加
        val py = Python.getInstance()
        val module = py.getModule("jikken").callAttr("hello_world")

        setContent {
            Example2_JetTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        // Pythonにより変更
                        print_py = module.toString(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// Pythonにより変更
@Composable
fun Greeting(print_py: String, modifier: Modifier = Modifier) {
    Text(
        text = "$print_py!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    //こんにちは
    var tex = "aaa"
    Example2_JetTheme {
        Greeting("Android $tex")
    }
}