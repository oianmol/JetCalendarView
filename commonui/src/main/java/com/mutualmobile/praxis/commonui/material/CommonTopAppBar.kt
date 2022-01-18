package com.mutualmobile.praxis.commonui.material

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mutualmobile.praxis.commonui.theme.PraxisSurface
import com.mutualmobile.praxis.commonui.theme.PraxisTheme

@Composable
fun CommonTopAppBar(
  title: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  navigationIcon: @Composable (() -> Unit)? = null,
  actions: @Composable RowScope.() -> Unit = {},
  backgroundColor: Color = PraxisTheme.colors.uiBackground,
  contentColor: Color = contentColorFor(backgroundColor),
  elevation: Dp = AppBarDefaults.TopAppBarElevation
  ) {
  PraxisSurface(
    color = PraxisTheme.colors.uiBackground,
    contentColor = PraxisTheme.colors.accent,
    elevation = 4.dp
  ) {
    TopAppBar(
      modifier = modifier,
      contentColor = contentColor,
      elevation = elevation,
      title = title,
      navigationIcon = navigationIcon,
      actions = actions,
      backgroundColor = backgroundColor,
    )
  }
}