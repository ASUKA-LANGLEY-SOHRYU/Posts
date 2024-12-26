package com.example.posts.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id"        ) var id        : Int?              = null,
    @SerializedName("login"     ) var login     : String?           = null,
    @SerializedName("email"     ) var email     : String?           = null,
    @SerializedName("firstName" ) var firstName : String?           = null,
    @SerializedName("lastName"  ) var lastName  : String?           = null,
    @SerializedName("phone"     ) var phone     : String?           = null,
    @SerializedName("roles"     ) var roles     : ArrayList<String> = arrayListOf(),
    @SerializedName("imageUUID" ) var imageUUID : String?           = null
)
