package com.praxis.feat.calendarview.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.mutualmobile.praxis.commonui.material.CommonTopAppBar
import com.mutualmobile.praxis.commonui.theme.PraxisTheme
import dev.baseio.libjetcalendar.data.JetDay
import dev.baseio.libjetcalendar.yearly.JetCalendarYearlyView
import java.time.LocalDate

@Composable
fun CalendarYearView(viewModel: CalendarYearVM = hiltViewModel()) {
  Scaffold(
    backgroundColor = PraxisTheme.colors.uiBackground,
    contentColor = PraxisTheme.colors.textSecondary,
    modifier = Modifier
      .statusBarsPadding()
      .navigationBarsPadding(),
    topBar = {
      CommonTopAppBar(title = {
      }, actions = {
        IconButton(modifier = Modifier.then(Modifier.padding(8.dp)),
          onClick = {
          }, content = {
            Icon(
              Icons.Filled.Search,
              "search",
              tint = Color.Red
            )
          })
        IconButton(modifier = Modifier.then(Modifier.padding(8.dp)),
          onClick = {
          }, content = {
            Icon(
              Icons.Filled.Add,
              "add events",
              tint = Color.Red
            )
          })
      })
    }) {
    val yearState by viewModel.yearState.collectAsState()
    yearState?.let { jetYear ->
      JetCalendarYearlyView(
        onDateSelected = {
          updateViewForViewType(viewModel, it)
        },
        selectedDates = setOf(JetDay(LocalDate.now(), isPartOfMonth = true)),
        jetYear = jetYear,
        dayOfWeek = viewModel.firstDayOfWeek,
        isGridView = false
      )
    }

  }


}

private fun updateViewForViewType(
  calendarYearVM: CalendarYearVM,
  jetDay: JetDay
) {
  calendarYearVM.navigateMonth(jetDay)
}
