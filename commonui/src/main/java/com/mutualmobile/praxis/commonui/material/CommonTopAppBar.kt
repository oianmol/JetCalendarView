package com.mutualmobile.praxis.commonui.material

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.mutualmobile.praxis.commonui.theme.PraxisSurface
import com.mutualmobile.praxis.commonui.theme.PraxisTheme

@Composable
fun CommonTopAppBar(
  title: @Composable () -> Unit,
  actions: @Composable RowScope.() -> Unit = {},
  ) {
  PraxisSurface(
    color = PraxisTheme.colors.uiBackground,
    contentColor = PraxisTheme.colors.accent,
    elevation = 4.dp
  ) {
    TopAppBar(
      title = {
        title()
      },
      actions = {
        actions()
      },
      backgroundColor = PraxisTheme.colors.uiBackground,
    )
  }
}