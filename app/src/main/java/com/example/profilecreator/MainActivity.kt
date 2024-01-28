package com.example.profilecreator

import android.os.Bundle
import android.service.autofill.OnClickAction
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
            appNavigator()
        }
    }
}

@Composable
fun appNavigator(userList: List<UserProfile> = userProfileList) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main-screen") {
        composable("main-screen") {
            MainScreen(userList, navController)
        }

        composable(
            "user_profile/{userId}",
            arguments = listOf(navArgument("userId") {
                type = NavType.IntType
            })
        ) {
            val parm = it.arguments?.getInt("userId")
            UserScreen(userProfileList[parm!!],navController)
        }
    }
}

@Composable
fun MainScreen(userList: List<UserProfile>, navController: NavHostController?) {

    Scaffold(topBar = {
        AppToolBar(toolBarTitle = "Colleague Status", icon = Icons.Filled.Home) {

        }
    }) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), color = Color.LightGray
        ) {

            LazyColumn() {
                items(userList) { user ->
                    ProfileCard(user) {
                        navController?.navigate("user_profile/${user.id}")
                    }
                }
            }
        }
    }
}


@Composable
fun AppToolBar(toolBarTitle: String, icon: ImageVector, onIconClick: () -> Unit) {
    CenterAlignedTopAppBar(title = {
        Text(text = "$toolBarTitle", color = Color.White)
    },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = PaleBlue),
        navigationIcon = {
            Icon(
                imageVector = icon, contentDescription = stringResource(
                    id = R.string.menu_title,
                ), tint = Color.White,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .clickable { onIconClick.invoke() }
            )
        })
}

@Composable
fun ProfileCard(user: UserProfile, clickAction: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 4.dp, start = 10.dp, end = 10.dp)
            .wrapContentHeight(align = Alignment.Top)
            .clickable { clickAction.invoke() },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),

        ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfilePicture(user, 60.dp)
            ProfileContent(user, Alignment.Start)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfilePicture(user: UserProfile, picSize: Dp) {

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
            modifier = Modifier.size(picSize),
            contentScale = ContentScale.Crop

        )
    }
}

@Composable
fun ProfileContent(user: UserProfile, position: Alignment.Horizontal) {
    val black = Color.Black
    val gray = Color.Gray
    val statusTextColor: Color = if (user.status) {
        black
    } else {
        gray
    }
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = position
    ) {
        Text(text = user.name, style = MaterialTheme.typography.headlineMedium)
        Text(
            text = if (user.status) "Active Now" else "Offline",
            style = MaterialTheme.typography.bodyMedium,
            color = statusTextColor
        )
    }
}

@Composable
fun UserScreen(user: UserProfile, navController: NavHostController?) {

    Scaffold(topBar = {
        AppToolBar(
            toolBarTitle = "User Status",
            icon = Icons.Filled.ArrowBack
        ) {
            navController?.navigateUp()
        }
    }) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), color = Color.LightGray
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfilePicture(user, 210.dp)
                ProfileContent(user, Alignment.CenterHorizontally)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfilePreview() {
    ProfileCreatorTheme {
        UserScreen(userProfileList[0],null)
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ProfileCreatorTheme {
        MainScreen(userProfileList, null)
    }
}