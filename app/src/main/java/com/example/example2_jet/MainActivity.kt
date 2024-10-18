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
import com.example.example2_jet.ui.theme.Example2_JetTheme

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val db = Firebase.firestore

        db.collection("test")
            .get()
            .addOnSuccessListener { result ->
                //取得成功
                var str: String = ""
                for (document in result) { //for文で取得結果を1つずつ取り出す
                    // "document.id"と"document.data"を文字列に入れて追加
                    str += "id:${document.id} data:${document.data}\n"

                }
                Log.d("TAG", str)
            }
            .addOnFailureListener { exception ->
                //取得失敗
                Log.d("TAG",  exception.message.toString())
            }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Example2_JetTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android2024",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Example2_JetTheme {
        Greeting("Android")
    }
}