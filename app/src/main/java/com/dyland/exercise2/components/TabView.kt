package com.dyland.exercise2.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.dyland.exercise2.models.TabItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabView(
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    tabs: List<TabItem>
) {
    TabRow(selectedTabIndex = pagerState.currentPage) {
        tabs.forEachIndexed { index, item ->
            Tab(
                text = { Text(text = item.text) },
                selected = pagerState.currentPage == index,
                onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } }
            )
        }
    }
}