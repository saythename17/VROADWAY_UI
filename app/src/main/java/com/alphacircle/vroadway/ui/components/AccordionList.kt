package com.alphacircle.vroadway.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alphacircle.vroadway.data.AccordionModel
import com.alphacircle.vroadway.data.Board

@Composable
fun AccordionList(modifier: Modifier = Modifier, list: List<Board>, boardType: String) {
    Column(modifier = modifier) {
        list.forEach {
            Accordion(board = it, boardType = boardType)
        }
    }
}