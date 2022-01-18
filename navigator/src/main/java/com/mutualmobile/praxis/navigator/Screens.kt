package com.mutualmobile.praxis.navigator

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
  baseRoute: String,
  val navArguments: List<NamedNavArgument> = emptyList()
) {
  val route: String = baseRoute.appendArguments(navArguments)

  object CalendarYearRoute :
    Screen("yearView")

  object CalendarMonthRoute :
    Screen(
      "monthView",
      navArguments = listOf(navArgument("date") { type = NavType.LongType })
    ) {
    fun createRoute(date: Long) =
      route.replace("{${navArguments.first().name}}","$date")
  }
}

private fun String.appendArguments(navArguments: List<NamedNavArgument>): String {
  val mandatoryArguments = navArguments.filter { it.argument.defaultValue == null }
    .takeIf { it.isNotEmpty() }
    ?.joinToString(separator = "/", prefix = "/") { "{${it.name}}" }
    .orEmpty()
  val optionalArguments = navArguments.filter { it.argument.defaultValue != null }
    .takeIf { it.isNotEmpty() }
    ?.joinToString(separator = "&", prefix = "?") { "${it.name}={${it.name}}" }
    .orEmpty()
  return "$this$mandatoryArguments$optionalArguments"
}