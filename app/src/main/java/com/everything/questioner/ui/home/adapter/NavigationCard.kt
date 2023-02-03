package com.everything.questioner.ui.home.adapter

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.navigation.NavDirections
import com.everything.questioner.R
import com.everything.questioner.ui.home.HomeFragment
import com.everything.questioner.ui.home.HomeFragmentDirections

data class NavigationCard(
    val title: String,
    @DrawableRes val icon: Int,
    val navigationAction: NavDirections,
)

val listOfNavigationCards = arrayListOf(
    NavigationCard(
        title = "Cuestionarios",
        icon = R.drawable.questioners,
        navigationAction = HomeFragmentDirections.actionHomeFragmentToFirstQuestionerFragment(fullQuestioner = true),
    ),
    NavigationCard(
        title = "Cuestionario 1",
        icon = R.drawable.single_questioner,
        navigationAction = HomeFragmentDirections.actionHomeFragmentToFirstQuestionerFragment(fullQuestioner = false),
    ),
    NavigationCard(
        title = "Cuestionario 2",
        icon = R.drawable.single_questioner,
        navigationAction = HomeFragmentDirections.actionHomeFragmentToSecondQuestionerFragment(comesFromFirstQuestioner = false),
    ),
    NavigationCard(
        title = "Datos del especialista",
        icon = R.drawable.specialist_data,
        navigationAction = HomeFragmentDirections.actionHomeFragmentToProfessionalDataFragment(),
    ),
)
