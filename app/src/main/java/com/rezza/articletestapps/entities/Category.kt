package com.rezza.articletestapps.entities

import com.rezza.articletestapps.R

enum class Category(val pName: String, val pIcon: Int) {

    GENERAL("General", R.drawable.ic_general), BUSINESS(
        "Business",
        R.drawable.ic_bussines
    ),
    SCIENCE("Science", R.drawable.ic_science), HEALTH(
        "Health",
        R.drawable.ic_health
    ),
    SPORTS("Sports", R.drawable.ic_sport), TECHNOLOGY(
        "Technology",
        R.drawable.ic_technology
    ),
    ENTERTAINMENT("Entertainment", R.drawable.ic_entertainment);

    val all: Array<Category>   get() = values()





}