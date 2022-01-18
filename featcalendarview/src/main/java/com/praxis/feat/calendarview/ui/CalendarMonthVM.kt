package com.praxis.feat.calendarview.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mutualmobile.praxis.navigator.Navigator
import com.mutualmobile.praxis.navigator.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.baseio.libjetcalendar.data.JetDay
import dev.baseio.libjetcalendar.data.JetMonth
import dev.baseio.libjetcalendar.data.JetPagingSource
import dev.baseio.libjetcalendar.data.JetYear
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CalendarMonthVM @Inject constructor(
  savedStateHandle: SavedStateHandle,
  private val navigator: Navigator
) : ViewModel() {
  fun navBack() {
    navigator.navigateUp()
  }

  private val monthDate =
    savedStateHandle.get<Long>(Screen.CalendarMonthRoute.navArguments.first().name)!!
  private val selectedDate: LocalDate = LocalDate.ofEpochDay(monthDate)
  val titleText = selectedDate.year.toString()

  val firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
  val year = JetYear.current(selectedDate, firstDayOfWeek)

  private val monthsPager = Pager(PagingConfig(12, initialLoadSize = 3)) {
    JetPagingSource(selectedDate, firstDayOfWeek)
  }
  val lazyPagingMonths = monthsPager.flow

}