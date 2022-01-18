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
import androidx.paging.compose.collectAsLazyPagingItems
import com.mutualmobile.praxis.commonui.theme.AlphaNearTransparent
import com.mutualmobile.praxis.commonui.theme.PraxisTheme
import dev.baseio.libjetcalendar.data.*
import dev.baseio.libjetcalendar.monthly.JetCalendarMonthlyView
import dev.baseio.libjetcalendar.monthly.WeekNames
import kotlinx.coroutines.flow.Flow
import java.time.DayOfWeek

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JetCalendarYearlyView(
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>,
  pagingFlow: Flow<PagingData<JetMonth>>,
  isGridView: Boolean = true,
  dayOfWeek: DayOfWeek,
  startIndex: Int = 0
) {
  val lazyPagingMonths = pagingFlow.collectAsLazyPagingItems()
  // affected by https://stackoverflow.com/questions/69739108/how-to-save-paging-state-of-lazycolumn-during-navigation-in-jetpack-compose
  val listState = rememberLazyListState(startIndex)

  YearViewInternal(
    listState,
    lazyPagingMonths,
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
  pagedMonths: LazyPagingItems<JetMonth>,
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>,
  isGridView: Boolean,
  dayOfWeek: DayOfWeek,
) {
  when (pagedMonths.itemCount) {
    0 -> CircularProgressIndicator(color = Color.Black, modifier = Modifier.padding(8.dp))
    else -> {
      if (isGridView) {
        GridViewYearly(listState, pagedMonths, onDateSelected, selectedDates, isGridView)
      } else {
        Column {
          if (!isGridView) {
            WeekNames(isGridView, dayOfWeek = dayOfWeek)
          }
          ListViewYearly(listState, pagedMonths, onDateSelected, selectedDates, isGridView)
        }
      }
    }
  }

}

@ExperimentalFoundationApi
@Composable
private fun ListViewYearly(
  listState: LazyListState,
  pagedMonths: LazyPagingItems<JetMonth>,
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
    for (index in 0 until pagedMonths.itemCount) {
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
  pagedMonths: LazyPagingItems<JetMonth>,
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
    for (index in 0 until pagedMonths.itemCount) {
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
  pagedMonths: LazyPagingItems<JetMonth>,
  index: Int
) {
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

@Composable
private fun CalendarMonthlyBox(
  pagedMonths: LazyPagingItems<JetMonth>,
  index: Int,
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>,
  isGridView: Boolean,
) {
  JetCalendarMonthlyView(
    pagedMonths[index]!!,
    {
      onDateSelected(it)
    },
    selectedDates, isGridView = isGridView
  )
}
