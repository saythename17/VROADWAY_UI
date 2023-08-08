package com.alphacircle.vroadway.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alphacircle.vroadway.data.AccordionModel

@Composable
fun AccordionList(modifier: Modifier = Modifier, list: List<AccordionModel>) {
    Column(modifier = modifier) {
        list.forEach {
            Accordion(model = it)
        }
    }
}