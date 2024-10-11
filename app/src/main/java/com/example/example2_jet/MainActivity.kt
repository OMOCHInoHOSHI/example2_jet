package com.example.example2_jet

import android.os.Bundle
import android.util.Log
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
import androidx.compose.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //ここもとりあえずいらない(?)
        //enableEdgeToEdge()
        setContent {
            //とりあえずこの辺空にしてAppScreen()作った方が楽
//            MyApplicationTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
            AppScreen()
        }
    }
}


//メインで書くところ
@Composable
fun AppScreen(){
    //Columnは縦置き、
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        var count by remember { /*mutableStateOfは値の変更を監視する為のもの*/mutableStateOf(0) }
        Text(
            text="$count"
        )
        Row{
            Button(
                //コンマを忘れずに書く↓
                onClick={count++},
                //ボタンを四角にする
                shape =  RoundedCornerShape(0.dp),
                //↓位置調整(Modifierの中身はコンマいらない)
                modifier=Modifier
                    .offset(x=0.dp,y=50.dp)
            )
            {
                //処理
                Text(text="Button1")
            }
            Button(
                //コンマを忘れずに書く↓
                onClick={count++},
                //↓位置調整
                modifier=Modifier.offset(x=0.dp,y=50.dp),
            )
            {
                Text(text="Button2")
            }
            Button(
                //コンマを忘れずに書く↓
                onClick={count++},
                //↓位置調整
                modifier=Modifier.offset(x=0.dp,y=50.dp),
            )
            {
                Text(text="Button3")
            }
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
    Example2_JetTheme() {
        AppScreen()
    }
}