package com.example.datacentercreator.model

import com.google.gson.annotations.SerializedName

data class Component(
    @SerializedName("componentid")
    val id: Int,
    @SerializedName("componentname")
    val name: String,
    @SerializedName("componentprice")
    val price: Int,
    @SerializedName("componentimage")
    val imageUrl: String,
    @SerializedName("componentdescription")
    val description: String,
    @SerializedName("componentstatus")
    val status: Int,
)