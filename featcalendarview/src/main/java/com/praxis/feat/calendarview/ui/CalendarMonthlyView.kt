package com.praxis.feat.calendarview.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
import dev.baseio.libjetcalendar.monthly.JetCalendarMonthlyView
import dev.baseio.libjetcalendar.yearly.JetCalendarYearlyView
import java.time.LocalDate

@Composable
fun CalendarMonthlyView(viewModel: CalendarMonthVM = hiltViewModel()) {
  Scaffold(
    backgroundColor = PraxisTheme.colors.uiBackground,
    contentColor = PraxisTheme.colors.textSecondary,
    modifier = Modifier
      .statusBarsPadding()
      .navigationBarsPadding(),
    topBar = {
      CommonTopAppBar(title = {
        AppBarTitle(viewModel)
      },
        navigationIcon = {
          BackButton(viewModel)
        },
        actions = {
          SearchIcon()
          AddEvents()
        })
    }) {
    MainContent(viewModel)
  }


}

@Composable
private fun AppBarTitle(viewModel: CalendarMonthVM) {
  val titleText by viewModel.titleState.collectAsState()
  Text(
    text = titleText,
    style = MaterialTheme.typography.h6.copy(color = Color.Red)
  )
}

@Composable
private fun MainContent(viewModel: CalendarMonthVM) {
  val month by viewModel.month.collectAsState()
  month?.let { jetMonth ->
    JetCalendarMonthlyView(
      jetMonth = jetMonth,
      onDateSelected = {
        viewModel.dateSelected(it.date)
      },
      selectedDates = setOf(viewModel.selectedDate.toJetDay(true)),
      isGridView = false,
      onNextMonth = {
        viewModel.nextMonth()
      },
      onPreviousMonth = {
        viewModel.previousMonth()
      },
      needsMonthNavigator = true
    )
  }
}

@Composable
private fun AddEvents() {
  IconButton(modifier = Modifier.then(Modifier.padding(8.dp)),
    onClick = {
    }, content = {
      Icon(
        Icons.Filled.Add,
        "add events",
        tint = Color.Red
      )
    })
}

@Composable
private fun SearchIcon() {
  IconButton(modifier = Modifier.then(Modifier.padding(8.dp)),
    onClick = {
    }, content = {
      Icon(
        Icons.Filled.Search,
        "search",
        tint = Color.Red
      )
    })
}

@Composable
private fun BackButton(viewModel: CalendarMonthVM) {
  Icon(
    imageVector = Icons.Filled.ArrowBack,
    contentDescription = "Back",
    modifier = Modifier
      .padding(16.dp)
      .clickable {
        // Implement back action here
        viewModel.navBack()
      },
    tint = Color.Red
  )
}
