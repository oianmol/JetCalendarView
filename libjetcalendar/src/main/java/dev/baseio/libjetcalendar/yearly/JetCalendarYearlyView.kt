package dev.baseio.libjetcalendar.yearly

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.*
import androidx.paging.compose.LazyPagingItems
import com.mutualmobile.praxis.commonui.theme.AlphaNearTransparent
import com.mutualmobile.praxis.commonui.theme.PraxisTheme
import dev.baseio.libjetcalendar.data.*
import dev.baseio.libjetcalendar.monthly.JetCalendarMonthlyView
import dev.baseio.libjetcalendar.monthly.WeekNames
import java.time.DayOfWeek

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JetCalendarYearlyView(
  jetYear: JetYear,
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>,
  isGridView: Boolean = true,
  dayOfWeek: DayOfWeek,
  startIndex: Int = 0
) {
  // affected by https://stackoverflow.com/questions/69739108/how-to-save-paging-state-of-lazycolumn-during-navigation-in-jetpack-compose
  val listState = rememberLazyListState(startIndex)

  YearViewInternal(
    listState,
    jetYear,
    onDateSelected,
    selectedDates,
    isGridView,
    dayOfWeek = dayOfWeek
  )
}


@ExperimentalFoundationApi
@Composable
private fun YearViewInternal(
  listState: LazyListState,
  jetYear: JetYear,
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>,
  isGridView: Boolean,
  dayOfWeek: DayOfWeek,
) {
  when (jetYear.yearMonths.size) {
    0 -> CircularProgressIndicator(color = Color.Black, modifier = Modifier.padding(8.dp))
    else -> {
      if (isGridView) {
        GridViewYearly(listState, jetYear.yearMonths, onDateSelected, selectedDates, isGridView)
      } else {
        Column {
          WeekNames(isGridView, dayOfWeek = dayOfWeek)
          ListViewYearly(listState, jetYear.yearMonths, onDateSelected, selectedDates, isGridView)
        }
      }
    }
  }

}

@ExperimentalFoundationApi
@Composable
private fun ListViewYearly(
  listState: LazyListState,
  pagedMonths: List<JetMonth>,
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>,
  isGridView: Boolean
) {
  LazyColumn(
    state = listState,
    modifier = Modifier
      .fillMaxWidth()
      .fillMaxHeight()
  ) {
    for (index in pagedMonths.indices) {
      item {
        CalendarMonthlyBox(
          pagedMonths,
          index,
          onDateSelected,
          selectedDates,
          isGridView
        )
      }
    }
  }
}

@ExperimentalFoundationApi
@Composable
private fun GridViewYearly(
  listState: LazyListState,
  pagedMonths: List<JetMonth>,
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>,
  isGridView: Boolean,
) {
  LazyVerticalGrid(
    cells = GridCells.Fixed(3),
    state = listState,
    modifier = Modifier
      .fillMaxWidth()
      .fillMaxHeight()
  ) {
    for (index in pagedMonths.indices) {
      when {
        index % 12 == 0 -> {
          item(span = { GridItemSpan(3) }) {
            YearHeader(pagedMonths, index)
          }
          item {
            CalendarMonthlyBox(
              pagedMonths,
              index,
              onDateSelected,
              selectedDates,
              isGridView
            )
          }
        }
        else -> {
          item {
            CalendarMonthlyBox(
              pagedMonths,
              index,
              onDateSelected,
              selectedDates,
              isGridView
            )
          }
        }
      }
    }
  }
}

@Composable
private fun YearMonthHeader(
  pagedMonths: LazyPagingItems<JetMonth>,
  index: Int
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .background(color = Color.White.copy(alpha = AlphaNearTransparent))
  ) {
    Text(
      text = pagedMonths[index]!!.monthYear(),
      modifier = Modifier.padding(8.dp),
      textAlign = TextAlign.Center,
      style = TextStyle(
        color = PraxisTheme.colors.textPrimary,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
      ),
    )
  }
}

@Composable
private fun YearHeader(
  pagedMonths: List<JetMonth>,
  index: Int
) {
  Text(
    text = pagedMonths[index].year(),
    modifier = Modifier.padding(8.dp),
    style = TextStyle(
      color = Color.Red,
      fontSize = 24.sp,
      fontWeight = FontWeight.SemiBold
    )
  )
}

@Composable
private fun CalendarMonthlyBox(
  pagedMonths: List<JetMonth>,
  index: Int,
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>,
  isGridView: Boolean,
) {
  JetCalendarMonthlyView(
    pagedMonths[index],
    {
      onDateSelected(it)
    },
    selectedDates, isGridView = isGridView
  )
}
