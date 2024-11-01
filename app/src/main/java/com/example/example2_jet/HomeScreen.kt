package com.example.example2_jet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.*
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.example2_jet.ui.theme.Example2_JetTheme

enum class MainScreenTab(
    val id: String,
    val icon: ImageVector,
    val label: String
) {
    Home(
        id = "main/home",
        icon = Icons.Outlined.Home,
        label = "Home"
    ),
    List(
        id = "main/camera",
        icon = Icons.Outlined.Camera,
        label = "Camera"
    ),
    Settings(
        id = "main/map",
        icon = Icons.Outlined.Map,
        label = "Map"
    )
}

//レイアウトはここに追加していく
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var drawerState by remember { mutableStateOf(DrawerState(initialValue = DrawerValue.Closed)) }
    val nestedNavController = rememberNavController()
    val navBackStackEntry by nestedNavController.currentBackStackEntryAsState()
    val currentTab = navBackStackEntry?.destination?.route
    Scaffold(
        //ドロワーメニュー----------------------------------------------------------------------
        topBar = {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    DismissibleDrawerSheet(
                        modifier = Modifier.width(200.dp)
                    ) {
                        Text(text = "ナビゲーションドロワー")
                        MainScreenTab.entries.forEachIndexed { index, item ->
                            NavigationDrawerItem(
                                icon = { Icon(item.icon, contentDescription = item.label) },
                                label = { Text(item.label) },
                                selected = currentTab == item.id,
                                onClick = {
                                    //デバッグ用
                                    //println(item.id)
                                    nestedNavController.navigate(item.id)
                                }
                            )
                        }
                    }
                },
            )
            {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    NavHost(
                        navController = nestedNavController,
                        startDestination = "main/home",
                        modifier = Modifier,
                    ) {
                        //デバッグ用
                        println("check")

                        screenMode()
                    }
                    //ドロワーメニューのアイコン----------------------------------------
                    IconButton(
                        modifier = Modifier.padding(start = 30.dp, top = 20.dp, end = 20.dp),
                        onClick = { drawerState = DrawerState(initialValue = DrawerValue.Open) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier
                                .height(60.dp)
                                .width(60.dp)
                        )
                    }
                    //ドロワーメニューのアイコン----------------------------------------
                }
            }
        },
        //ドロワーメニュー----------------------------------------------------------------------
        //ナビゲーションバー--------------------------------------------------------------------
        bottomBar = {
            NavigationBar {
                MainScreenTab.entries.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = currentTab == item.id,
                        onClick = {
                            //デバッグ用
                            println(item.id)

                            nestedNavController.navigate(item.id)
                        }
                    )
                }
            }
        },
        //ナビゲーションバー--------------------------------------------------------------------
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavHost(
                navController = nestedNavController,
                startDestination = "main/home",
                modifier = Modifier,
            ) {
                screenMode()
            }
        }
    }
}


fun NavGraphBuilder.mainScreen() {
    navigation(route = "main", startDestination = "main/entry") {
        composable("main/entry") {
            MainScreen()
        }
    }
}

//各画面の処理------------------------------------------------
fun NavGraphBuilder.screenMode(){
    composable("main/home") {


        //デバッグ用
        Text("main/home")
        println("home")
    }
    composable("main/camera") {


        //デバッグ用
        Text("main/camera")
        println("Camera")
    }
    composable("main/map") {


        //デバッグ用
        Text("main/map")
        println("map")
    }
}
//各画面の処理------------------------------------------------

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    Example2_JetTheme {
        MainScreen()
    }
}