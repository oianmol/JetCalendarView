package dev.baseio.libjetcalendar.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.DayOfWeek
import java.time.LocalDate

class JetPagingSource(private val initialYear: LocalDate, private val firstDayOfWeek: DayOfWeek) : PagingSource<LocalDate, JetMonth>() {

  override suspend fun load(params: LoadParams<LocalDate>): LoadResult<LocalDate, JetMonth> {
    val initial = params.key ?: initialYear
    val years = withContext(Dispatchers.Default) {
      val current = JetYear.current(initial,firstDayOfWeek)
      current.yearMonths!!
    }
    return LoadResult.Page(
      data = years,
      prevKey = null,
      nextKey = initial.plusYears(1)
    )
  }

  override fun getRefreshKey(state: PagingState<LocalDate, JetMonth>): LocalDate? {
    return state.anchorPosition?.let { anchorPosition ->
      state.closestItemToPosition(anchorPosition)?.startDate
    }
  }
}