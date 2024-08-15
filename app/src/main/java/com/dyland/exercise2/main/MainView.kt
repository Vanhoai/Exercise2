package com.dyland.exercise2.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.dyland.exercise2.components.FoldersView
import com.dyland.exercise2.components.TabInstalledApps
import com.dyland.exercise2.components.TabRecentApps
import com.dyland.exercise2.components.TabView
import com.dyland.exercise2.models.TabItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainView(
    navController: NavController,
    mainUIState: MainUIState,
    onEvent: (MainUIEvents) -> Unit
) {
    val tabs = listOf(
        TabItem(
            text = "Installed Apps",
            screen = {}
        ),
        TabItem(
            text = "Recent Apps",
            screen = {}
        ),
    )
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { tabs.size }, initialPage = 0)

    Scaffold(
        topBar = {},
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {

                FoldersView(
                    mainUIState = mainUIState,
                    navController = navController,
                    onEvent = onEvent
                )

                TabView(
                    pagerState = pagerState,
                    coroutineScope = coroutineScope,
                    tabs = tabs
                )

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    when (page) {
                        0 -> TabInstalledApps(
                            mainUIState = mainUIState,
                            navController = navController,
                            onEvent = onEvent
                        )

                        1 -> TabRecentApps(
                            mainUIState = mainUIState,
                            navController = navController,
                            onEvent = onEvent
                        )
                    }
                }
            }
        }
    )
}
