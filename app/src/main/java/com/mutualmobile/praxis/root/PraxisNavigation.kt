package com.mutualmobile.praxis.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.mutualmobile.praxis.commonui.theme.AlphaNearOpaque
import com.mutualmobile.praxis.commonui.theme.PraxisSurface
import com.mutualmobile.praxis.commonui.theme.PraxisTheme
import com.mutualmobile.praxis.navigator.Navigator
import com.mutualmobile.praxis.navigator.Screen
import com.praxis.feat.calendarview.ui.CalendarMonthView
import com.praxis.feat.calendarview.ui.CalendarYearView

@Composable
fun PraxisNavigation(navigator: Navigator) {
  ProvideWindowInsets {
    PraxisSurface(
      color = PraxisTheme.colors.statusBarColor.copy(alpha = AlphaNearOpaque),
      modifier = Modifier.fillMaxSize()
    ) {
      val navController = rememberNavController()

      LaunchedEffect(Unit) {
        navigator.handleNavigationCommands(navController)
      }

      NavHost(
        navController = navController,
        startDestination = Screen.CalendarYearRoute.route
      ) {
        composable(Screen.CalendarYearRoute.route) {
          CalendarYearView()
        }
        composable(
          Screen.CalendarMonthRoute.route, arguments = Screen.CalendarMonthRoute.navArguments
        ) {
          CalendarMonthView()
        }
      }
    }
  }

}

