package com.example.hs9

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect

@Composable
fun Camera()
{
    SideEffect { Log.d("compose-log", "Camera") }
    Column()
    {
//        Button()
//        {
//
//        }
    }


    //val permissionState: PermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)




    //デバッグ用
    Text("main/camera")
    println("Camera")
}

fun StartCamera()
{
    //カメラ起動
}

fun StartPhoto()
{
    //スマホ内の画像で判定
}