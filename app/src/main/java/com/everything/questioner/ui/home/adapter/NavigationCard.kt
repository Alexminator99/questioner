package com.everything.questioner.ui.home.adapter

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.IntegerRes
import com.everything.questioner.R

data class NavigationCard(val title: String, @DrawableRes val icon: Int, @IdRes val navigationAction: Int)

val listOfNavigationCards = arrayListOf(
    NavigationCard(
        title = "Cuestionario 1",
        icon = R.drawable.ic_launcher_foreground,
        navigationAction = R.id.action_HomeFragment_to_FirstQuestionerFragment
    ),
    NavigationCard(
        title = "Cuestionario 2",
        icon = R.drawable.ic_launcher_foreground,
        navigationAction = R.id.action_HomeFragment_to_SecondQuestionerFragment
    ),
)
