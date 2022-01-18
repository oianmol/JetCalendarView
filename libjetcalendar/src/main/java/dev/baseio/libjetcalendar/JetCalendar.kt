package dev.baseio.libjetcalendar

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.baseio.libjetcalendar.data.*
import dev.baseio.libjetcalendar.monthly.JetCalendarMonthlyView
import dev.baseio.libjetcalendar.monthly.WeekNames
import dev.baseio.libjetcalendar.weekly.JetCalendarWeekView
import java.time.DayOfWeek
import java.time.temporal.WeekFields
import java.util.*

@Deprecated("dont use this!")
@Composable
fun JetCalendar(
  modifier: Modifier,
  viewType: JetViewType = JetViewType.YEARLY,
  firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
  today: JetDay,
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>,
  onViewChanged: (JetMonth) -> Unit
) {
  when (viewType) {
    JetViewType.MONTHLY -> JetCalendarMonthlyView(
      jetMonth = JetMonth.current(today.date, firstDayOfWeek),
      onDateSelected = onDateSelected,
      selectedDates = selectedDates,
      viewType = viewType
    )
    JetViewType.WEEKLY -> Column {
      Column {
        val week = JetWeek.current(today.date, isFirstWeek = true, dayOfWeek = firstDayOfWeek)
        WeekNames(week = week, viewType = viewType)
        JetCalendarWeekView(
          modifier = modifier,
          week = week,
          onDateSelected = onDateSelected,
          selectedDates = selectedDates,
          viewType = viewType,
        )
      }
    }
    JetViewType.YEARLY -> {

    }
  }
}

