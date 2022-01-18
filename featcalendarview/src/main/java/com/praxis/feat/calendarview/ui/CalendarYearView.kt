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
import dev.baseio.libjetcalendar.data.toJetDay
import dev.baseio.libjetcalendar.yearly.JetCalendarYearlyView
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@Composable
fun CalendarYearView(VM: CalendarYearVM = hiltViewModel()) {
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
    JetCalendarYearlyView(
      startingYear = VM.year,
      onDateSelected = {
        val startOfMonth = it.date.with(TemporalAdjusters.firstDayOfMonth())
        updateViewForViewType(VM, startOfMonth.toJetDay(true))
      },
      selectedDates = setOf(JetDay(LocalDate.now(), isPartOfMonth = true)),
      pagingFlow = VM.lazyPagingMonths,
      dayOfWeek =  VM.firstDayOfWeek
    )
  }


}

private fun updateViewForViewType(
  VM: CalendarYearVM,
  it: JetDay
) {
  VM.selectedDate = it
  VM.navigateMonth()
}
