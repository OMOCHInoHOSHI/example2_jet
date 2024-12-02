package com.example.example2_jet

import ImageAnalyzer
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
import com.chaquo.python.Python
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
import com.chaquo.python.android.AndroidPlatform
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

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
                // Chaquopyを初期化
                if (!Python.isStarted()) {
                    Python.start(AndroidPlatform(this))
                }

                ResNet_page()

                //ImageClassifierApp()

//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        // Pythonにより変更
//                        print_py = module.toString(),
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
            }
            // とりあえずこの辺空にしてAppScreen()作った方が楽
            //AppScreen()

        }



    }

}


// Pythonにより変更
//メインで書くところ
@Composable
//fun Greeting(print_py: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "$print_py!",
//        modifier = modifier
//    )
//}
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


// 画像選択画面の設定(ResNet)---------------------------------------------------------------
@Composable
fun ResNet_page() {
    // 関数の開始を確認
    Log.d("ResNet_page", "ResNet_Start")

    // 現在のコンテキストを取得
    val context = LocalContext.current
    // ImageAnalyzerのインスタンスを作成
    val imageAnalyzer = remember { ImageAnalyzer(context) }
    // 選択された画像を保持するための状態を定義
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    // 解析結果を保持するための状態を定義
    var result by remember { mutableStateOf("") }
    // コルーチンスコープを作成
    val coroutineScope = rememberCoroutineScope()

    // 画像選択のランチャーを作成
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            // 選択された画像のURIからビットマップを取得
            val inputStream = context.contentResolver.openInputStream(it)
            bitmap = BitmapFactory.decodeStream(inputStream)
        }
    }

    // マテリアルテーマを適用
    MaterialTheme {
        // 画面全体を覆うSurfaceを作成
        Surface(modifier = Modifier.fillMaxSize()) {
            // 縦に並べるColumnを作成
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // ビットマップが存在する場合、画像を表示
                bitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.size(224.dp)
                    )
                }
                // スペースを追加
                Spacer(modifier = Modifier.height(16.dp))
                // 画像選択ボタンを作成
                Button(onClick = { launcher.launch("image/*") }) {
                    Text("写真を選択")
                }
                // スペースを追加
                Spacer(modifier = Modifier.height(16.dp))
                // 画像解析ボタンを作成
                Button(onClick = {
                    bitmap?.let { bmp ->
                        // コルーチンを起動して画像を解析
                        coroutineScope.launch {
                            try {
                                Log.d("ResNet_page", "imageAnalyzer")
                                result = imageAnalyzer.analyzePhoto(bmp)
                            } catch (e: Exception) {
                                Log.e("ResNet_page", "Error analyzing photo", e)
                            }
                        }
                    }
                }) {
                    Text("解析")
                }
                // スペースを追加
                Spacer(modifier = Modifier.height(16.dp))
                // 解析結果を表示
                Text(text = result)
            }
        }
    }
}