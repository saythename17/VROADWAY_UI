package com.alphacircle.vroadway.ui.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.alphacircle.vroadway.ui.theme.KoreanTypography

@Composable
fun AcAlertDialog (
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String?,
    dialogText: String?,
    confirmText: String?,
    dismissText: String?,
) {
    AlertDialog(
        title = {
            dialogTitle?.let { Text(text = it, style = KoreanTypography.h6) }
        },
        text = {
            dialogText?.let { Text(text = it, style = KoreanTypography.body2) }
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                when (confirmText) {
                    null -> Text("확인", style = KoreanTypography.button, color = Color.Red)
                    else -> Text(confirmText, style = KoreanTypography.button, color = Color.Red)
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                when (dismissText) {
                    null -> Text("취소", style = KoreanTypography.button)
                    else -> Text(dismissText, style = KoreanTypography.button)
                }

            }
        }
    )
}