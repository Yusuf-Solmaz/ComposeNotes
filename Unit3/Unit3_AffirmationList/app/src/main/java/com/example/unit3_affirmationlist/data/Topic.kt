package com.example.unit3_affirmationlist.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Topic(
    @StringRes val stringResourceId: Int,
    val numberOfCourses: Int,
    @DrawableRes val imageResourceId: Int
)