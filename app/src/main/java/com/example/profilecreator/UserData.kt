package com.example.profilecreator

data class UserData(val name :String, val status : Boolean, val drawable: Int)

val UserList = arrayListOf<UserData>(
    UserData(name = "Sreejith", status = true, drawable = R.drawable.profile_picture),
    UserData(name = "Jane Doe", status = false, drawable = R.drawable.women),
    UserData(name = "Jay", status = false, drawable = R.drawable.men)
)