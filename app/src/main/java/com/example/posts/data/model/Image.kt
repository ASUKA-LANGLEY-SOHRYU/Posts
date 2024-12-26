package com.example.posts.data.model

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("id"       ) var id       : Int?    = null,
    @SerializedName("name"     ) var name     : String? = null,
    @SerializedName("fileName" ) var fileName : String? = null,
    @SerializedName("type"     ) var type     : String? = null
)