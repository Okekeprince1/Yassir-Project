package com.example.yassirproject.utils

import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.yassirproject.constants.Constants

fun ImageView.load(imageUrl: String) {
    Glide.with(this.context)
        .load(Constants.IMAGE_PATH + imageUrl)
        .into(this);
}

fun String.getYearFromDate(): String {
    return this.split("-")[0]
}