package com.example.hs9

//import androidx.compose.ui.draw.EmptyBuildDrawCacheParams.size
//import kotlin.collections.EmptyList.size
import android.provider.ContactsContract.CommonDataKinds.Photo
import android.util.Log
import androidx.collection.emptyLongSet
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource as painterResource1
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.example2_jet.R
import kotlin.properties.PropertyDelegateProvider

//enum class ImageItems(
//    val id: String,
//    val image: Image
//){
//
//}

@Composable
fun Home()
{
    SideEffect { Log.d("compose-log", "Home") }
    val itemsIndexedList = rememberSaveable { mutableListOf("A", "B", "C", "D", "E")}
    //var activePhotoId by rememberSaveable { mutableStateOf<Int?>(null) }
    Column()
    {
        //地図枠(仮)
        Box()
        {
            Image(
                modifier = Modifier.padding(start = 0.dp, top = 0.dp,end=0.dp, bottom = 225.dp),
                painter = painterResource1(R.drawable.tizu_kakkokari1), contentDescription = "test"
            )
        }

        //投稿(仮)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
        )
        {
            itemsIndexed(itemsIndexedList)
            { index, item ->
                //var checked by rememberSaveable(index) { mutableStateOf(false)}
                var checked: MyDto by rememberSaveable(index,stateSaver = MyDtoSaver) { mutableStateOf(MyDto(false)) }
                //var checked: MyDto by rememberLazyListState(index,stateSaver = MyDtoSaver) { mutableStateOf(MyDto(false)) }
                Image(
                    painter = painterResource1(R.drawable.test1), contentDescription = "test"
                )
                Box()
                {
                    IconToggleButton(
//                        checked = checked,
//                        onCheckedChange = {checked = it },
                        onCheckedChange = {checked = MyDto(it) },
                        checked = checked.data,


                    )
                    {
                        val tint by animateColorAsState(if (checked.data/*checked*/) { Color(0xFFEC407A) } else { Color(0xFFB0BEC5) })
                        Icon(Icons.Filled.Favorite, contentDescription = "Localized description", tint = tint)
//                        if(checked.data==true)
//                        {
//                            SideEffect { Log.d("compose-log", "true") }
//                        }
//                        else
//                        {
//                            SideEffect { Log.d("compose-log", "false") }
//                        }
                    }
                }
            }
        }
//        if(activePhotoId!=null)
//        {
//            FullScreenImage(
//                index=itemsIndexedList.first{index==activePhotoId},
//                onDismiss
//            )
//        }
    }
}

//@Composable
fun HomeMap()
{

}

class MyDto(val data: Boolean)
    val MyDtoSaver = listSaver<MyDto, Any>(
        save = { mutableListOf(it.data) },
        restore = { MyDto(it[0] as Boolean) }
    )

//@Composable
fun ImageList(photos:List<Photo>)
{
//    var activePhotoId by rememberSavable { mutableStateOf<Int?>(null) }
//    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp)) {
//        items(photos, { it.id }) { photo ->
//            ImageItem(
//                photo,
//                Modifier.clickable { activePhotoId = photo.id }
//            )
//        }
//    }
//    if (activePhotoId != null) {
//        FullScreenImage(
//            photo = photos.first { it.id == activePhotoId },
//            onDismiss = { activePhotoId = null }
//        )
//    }

}

