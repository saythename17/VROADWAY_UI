/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alphacircle.vroadway.ui.home.discover

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alphacircle.vroadway.data.category.Content
import com.alphacircle.vroadway.data.category.HighLevelCategory
import com.alphacircle.vroadway.ui.home.category.VRCategory
import com.alphacircle.vroadway.ui.theme.EnglishTypography
import com.alphacircle.vroadway.ui.theme.Keyline1
import com.alphacircle.vroadway.ui.theme.VroadwayColors

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Discover(
    navigateToPlayer: (String) -> Unit,
    navigateToInfo: (Long, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: DiscoverViewModel = viewModel()
    val viewState by viewModel.state.collectAsStateWithLifecycle()

    val selectedCategory = viewState.selectedCategory
    if (viewState.categories.isNotEmpty() && selectedCategory != null) {
        Column(modifier) {
            CategoryTabs(
                categories = viewState.categories,
                selectedCategory = selectedCategory,
                onCategorySelected = viewModel::onCategorySelected,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            Crossfade(
                targetState = selectedCategory,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), label = ""
            ) { category ->
                /**
                 * TODO, need to think about how this will scroll within the outer VerticalScroller
                 */
                VRCategory(
                    lowLevelCategoryList = category.lowLevelCategoryList,
                    categoryId = selectedCategory.lowLevelCategoryList[0].id.toLong(),
                    navigateToPlayer = navigateToPlayer,
                    navigateToInfo = navigateToInfo,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

private val emptyTabIndicator: @Composable (List<TabPosition>) -> Unit = {}

@Composable
private fun CategoryTabs(
    categories: List<HighLevelCategory>,
    selectedCategory: HighLevelCategory,
    onCategorySelected: (HighLevelCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedIndex = categories.indexOfFirst { it == selectedCategory }
    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        divider = {}, /* Disable the built-in divider */
        edgePadding = Keyline1,
        indicator = emptyTabIndicator,
        modifier = modifier
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = index == selectedIndex,
                onClick = { onCategorySelected(category) }
            ) {
                ChoiceCategoryChip(
                    text = category.name,
                    selected = index == selectedIndex,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun MyCategoryTabs(
    categories: List<HighLevelCategory>,
    selectedCategory: HighLevelCategory?,
    onCategorySelected: (HighLevelCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedIndex = categories.indexOfFirst { it == selectedCategory }
    ScrollableTabRow(
        selectedTabIndex = 0,
        divider = {}, /* Disable the built-in divider */
        edgePadding = Keyline1,
        indicator = emptyTabIndicator,
        modifier = modifier
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = index == selectedIndex,
                onClick = { onCategorySelected(category) }
            ) {
                ChoiceCategoryChip(
                    text = category.name,
                    selected = index == selectedIndex,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun ChoiceCategoryChip(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
//        color = when {
//            selected -> MaterialTheme.colors.primary.copy(alpha = 0.08f)
//            else -> MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
//        },
        contentColor = when {
            selected -> VroadwayColors.primaryVariant
            else -> VroadwayColors.onSurface
        },
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = EnglishTypography.subtitle1,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
fun PreviewChip() {
    Row {
        ChoiceCategoryChip(text = "Seventeen", selected = true)
        ChoiceCategoryChip(text = "Seventeen", selected = false)
    }
}