package dev.baseio.libjetcalendar.monthly

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
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

@Composable
fun JetCalendarMonthlyView(
  jetMonth: JetMonth,
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>,
  showWeekView: Boolean = true,
  viewType: JetViewType
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
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = colorCurrentMonthSelected(selectedDates, jetMonth)
      ),
      modifier = Modifier.padding(8.dp)
    )
    jetMonth.monthWeeks?.forEach { week ->
      Column {
        if (week.isFirstWeek && showWeekView) {
          WeekNames(week,viewType)
        }
        JetCalendarWeekView(
          modifier = Modifier.fillMaxWidth(),
          week = week,
          onDateSelected = onDateSelected,
          selectedDates = selectedDates,
          viewType
        )
      }
    }
  }
}

@Composable
fun colorCurrentMonthSelected(selectedDates: Set<JetDay>, jetMonth: JetMonth): Color {
  return if (jetMonth.endDate.monthValue == selectedDates.first().date.monthValue) Color.Red else MaterialTheme.typography.body1.color
}

@Composable
private fun NextButton(onNext: () -> Unit) {
  IconButton(onClick = {
    onNext()
  }) {
    Icon(
      Icons.Filled.ArrowForward,
      "next",
      tint = Color.Black
    )
  }
}

@Composable
private fun PrevButton(onPrevious: () -> Unit) {
  IconButton(onClick = {
    onPrevious()

  }) {
    Icon(
      Icons.Filled.ArrowBack,
      "previous",
      tint = Color.Black
    )
  }
}

@Composable
fun WeekNames(week: JetWeek, viewType: JetViewType) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    week.dayNames(viewType).forEach {
      Box(
        modifier = Modifier
          .padding(2.dp),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = it, modifier = Modifier.padding(2.dp),
          style = TextStyle(
            fontSize = if(viewType == JetViewType.YEARLY ) 8.sp else 12.sp, fontWeight = FontWeight.Bold
          )
        )
      }

    }
  }
}