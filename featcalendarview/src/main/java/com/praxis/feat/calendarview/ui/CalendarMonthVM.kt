package com.praxis.feat.calendarview.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mutualmobile.praxis.navigator.Navigator
import com.mutualmobile.praxis.navigator.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.baseio.libjetcalendar.data.JetMonth
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarMonthVM @Inject constructor(
  savedStateHandle: SavedStateHandle,
  private val navigator: Navigator
) : ViewModel() {
  private val monthDate =
    savedStateHandle.get<Long>(Screen.CalendarMonthRoute.navArguments.first().name)!!
  val selectedDate = LocalDate.ofEpochDay(monthDate)
  var uiState = MutableStateFlow<UiState>(UiState.LoadingState)
    private set

  init {
    uiState.value = UiState.SuccessState(JetMonth.current(selectedDate))
  }

  sealed class UiState {
    object LoadingState : UiState()
    data class SuccessState(
      val jetMonth: JetMonth,
    ) : UiState()
    object ErrorState : UiState()
  }
}