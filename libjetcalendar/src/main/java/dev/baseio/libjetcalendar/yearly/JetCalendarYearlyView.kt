package dev.baseio.libjetcalendar.yearly

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.*
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import dev.baseio.libjetcalendar.data.*
import dev.baseio.libjetcalendar.monthly.JetCalendarMonthlyView
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JetCalendarYearlyView(
  startingYear: JetYear,
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>,
  pagingFlow: Flow<PagingData<JetMonth>>
) {
  val lazyPagingMonths = pagingFlow.collectAsLazyPagingItems()
  // affected by https://stackoverflow.com/questions/69739108/how-to-save-paging-state-of-lazycolumn-during-navigation-in-jetpack-compose
  val listState = rememberLazyListState(startingYear.currentMonthPosition())

  YearViewInternal(
    listState,
    lazyPagingMonths,
    onDateSelected,
    selectedDates,
  )
}


@ExperimentalFoundationApi
@Composable
private fun YearViewInternal(
  listState: LazyListState,
  pagedMonths: LazyPagingItems<JetMonth>,
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>,
) {
  when (pagedMonths.itemCount) {
    0 -> CircularProgressIndicator(color = Color.Black, modifier = Modifier.padding(8.dp))
    else -> {
      LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        state = listState,
        modifier = Modifier
          .fillMaxWidth()
          .fillMaxHeight()
      ) {
        for (index in 0 until pagedMonths.itemCount) {
          when {
            index % 12 == 0 -> {
              item(span = { GridItemSpan(3) }) {
                Text(
                  text = pagedMonths[index]!!.year(),
                  modifier = Modifier.padding(8.dp),
                  style = TextStyle(
                    color = Color.Red,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                  )
                )
              }
              item {
                CalendarMonthlyBox(pagedMonths, index, onDateSelected, selectedDates)
              }
            }
            else -> {
              item {
                CalendarMonthlyBox(pagedMonths, index, onDateSelected, selectedDates)
              }
            }
          }
        }
      }
    }
  }

}

@Composable
private fun CalendarMonthlyBox(
  pagedMonths: LazyPagingItems<JetMonth>,
  index: Int,
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>,
) {
  Box() {
    JetCalendarMonthlyView(pagedMonths[index]!!,{
      onDateSelected(it)
    }, selectedDates, false, viewType = JetViewType.YEARLY)
  }
}
