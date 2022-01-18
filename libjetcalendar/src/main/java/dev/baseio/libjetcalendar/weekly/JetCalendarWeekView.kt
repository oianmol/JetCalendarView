package dev.baseio.libjetcalendar.weekly

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mutualmobile.praxis.commonui.theme.PraxisTheme
import dev.baseio.libjetcalendar.data.JetDay
import dev.baseio.libjetcalendar.data.JetWeek

@Composable
fun JetCalendarWeekView(
  modifier: Modifier,
  week: JetWeek,
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>,
  isGridView: Boolean,
) {
  Row(
    modifier = modifier.fillMaxWidth().padding(bottom = 8.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {

    week.days?.forEach { date ->
      Box(
        modifier = Modifier
          .size(if(!isGridView) 40.dp else 16.dp)
          .clip(CircleShape)
          .clickable {
            if (date.isPartOfMonth) {
              onDateSelected(date)
            }
          }
          .background(bgColor(selectedDates, date)),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = date.date.dayOfMonth.toString(),
          style = TextStyle(
            fontSize = if(isGridView) 8.sp else 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (date.isPartOfMonth) MaterialTheme.typography.body1.color else Color.Transparent
          )
        )
      }
    }
  }
}

@Composable
private fun bgColor(
  selectedDates: Set<JetDay>,
  date: JetDay
) = if (selectedDates.contains(date)) Color.Red else PraxisTheme.colors.uiBackground