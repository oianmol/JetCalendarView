package dev.baseio.libjetcalendar.monthly

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.baseio.libjetcalendar.data.*
import dev.baseio.libjetcalendar.weekly.JetCalendarWeekView
import java.time.DayOfWeek

@Composable
fun JetCalendarMonthlyView(
  jetMonth: JetMonth,
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>,
  showWeekView: Boolean = true,
  isGridView: Boolean,
  dayOfWeek: DayOfWeek
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .wrapContentHeight()
      .padding(4.dp),
    verticalArrangement = Arrangement.SpaceAround,
  ) {
    Text(
      text = jetMonth.name(),
      style = TextStyle(
        fontSize = if(isGridView) 16.sp else 18.sp,
        fontWeight = FontWeight.Medium,
        color = colorCurrentMonthSelected(selectedDates, jetMonth)
      ),
      modifier = Modifier.padding(8.dp)
    )
    jetMonth.monthWeeks?.forEach { week ->
      Column {
        if (week.isFirstWeek && showWeekView) {
          WeekNames(isGridView, dayOfWeek = dayOfWeek)
        }
        JetCalendarWeekView(
          modifier = Modifier.fillMaxWidth(),
          week = week,
          onDateSelected = onDateSelected,
          selectedDates = selectedDates,
          isGridView
        )
      }
    }
  }
}

@Composable
fun colorCurrentMonthSelected(selectedDates: Set<JetDay>, jetMonth: JetMonth): Color {
  return if (isSameMonth(jetMonth, selectedDates)) Color.Red else MaterialTheme.typography.body1.color
}

@Composable
private fun isSameMonth(
  jetMonth: JetMonth,
  selectedDates: Set<JetDay>
) =
  jetMonth.endDate.monthValue == selectedDates.first().date.monthValue && jetMonth.endDate.year == selectedDates.first().date.year


@Composable
fun WeekNames(isGridView: Boolean, dayOfWeek: DayOfWeek) {
  Row(
    modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    dayNames(dayOfWeek = dayOfWeek).forEach {
      Box(
        modifier = Modifier
          .padding(2.dp),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = it, modifier = Modifier.padding(2.dp),
          style = TextStyle(
            fontSize = if (isGridView) 8.sp else 12.sp,
            fontWeight = FontWeight.Bold
          )
        )
      }

    }
  }
}