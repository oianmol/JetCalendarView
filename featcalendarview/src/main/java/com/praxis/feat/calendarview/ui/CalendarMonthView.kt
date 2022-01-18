package com.praxis.feat.calendarview.ui

import androidx.compose.foundation.layout.Column
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
import com.mutualmobile.praxis.commonui.material.CommonTopAppBar
import com.mutualmobile.praxis.commonui.theme.PraxisTheme
import dev.baseio.libjetcalendar.data.JetViewType
import dev.baseio.libjetcalendar.data.toJetDay
import dev.baseio.libjetcalendar.monthly.JetCalendarMonthlyView

@Composable
fun CalendarMonthView(viewModel: CalendarMonthVM = hiltViewModel()) {
  Scaffold(
    backgroundColor = PraxisTheme.colors.uiBackground,
    contentColor = PraxisTheme.colors.textSecondary,
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
    Column {
      val uiState by viewModel.uiState.collectAsState()
      if (uiState is CalendarMonthVM.UiState.LoadingState) {
        CircularProgressIndicator()
      } else if (uiState is CalendarMonthVM.UiState.SuccessState) {
        JetCalendarMonthlyView(
          jetMonth = (uiState as CalendarMonthVM.UiState.SuccessState).jetMonth,
          onDateSelected = {

          },
          selectedDates = setOf(viewModel.selectedDate.toJetDay(true)),
          viewType = JetViewType.MONTHLY
        )
      }
    }
  }


}
