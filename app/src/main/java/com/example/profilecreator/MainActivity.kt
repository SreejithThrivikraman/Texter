package com.example.profilecreator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.codingtroops.profilecardlayout.UserProfile
import com.codingtroops.profilecardlayout.userProfileList
import com.example.profilecreator.ui.theme.ProfileCreatorTheme
import com.example.profilecreator.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(userProfileList)
        }
    }
}

@Composable
fun MainScreen(userList: List<UserProfile>) {

    Scaffold(topBar = { AppToolBar(stringResource(id = R.string.application_title)) }) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = Color.LightGray
        ) {

            LazyColumn() {
                items(userList) { user ->
                    ProfileCard(user)
                }
            }
        }
    }
}


@Composable
fun AppToolBar(toolBarTitle: String) {
    CenterAlignedTopAppBar(title = {
        Text(text = "$toolBarTitle", color = Color.White)
    },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = PaleBlue),
        navigationIcon = {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = stringResource(
                    id = R.string.menu_title,
                ),
                tint = Color.White,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    )
}

@Composable
fun ProfileCard(user: UserProfile) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 4.dp, start = 10.dp, end = 10.dp)
            .wrapContentHeight(align = Alignment.Top),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),

        ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            ProfilePicture(user)
            ProfileContent(user)


        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfilePicture(user: UserProfile) {

    val green = Color.Green
    val red = Color.Red

    val statusColor: Color = if (user.status) {
        green
    } else {
        red
    }

    Card(
        modifier = Modifier.padding(16.dp),
        border = BorderStroke(width = 2.dp, statusColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = CircleShape
    ) {

        GlideImage(
            model = user.pictureUrl,
            contentDescription = null,
            modifier = Modifier.size(60.dp),
            contentScale = ContentScale.Crop

        )


//        Image(
//            painter = painterResource(id = user.drawable),
//            contentDescription = "Content Description",
//            modifier = Modifier.size(60.dp),
//            contentScale = ContentScale.Crop
//        )
    }
}

@Composable
fun ProfileContent(user: UserProfile) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(text = user.name, style = MaterialTheme.typography.headlineMedium)
        Text(
            text = if (user.status) "Active Now" else "Offline",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProfileCreatorTheme {
        MainScreen(userProfileList)
    }
}