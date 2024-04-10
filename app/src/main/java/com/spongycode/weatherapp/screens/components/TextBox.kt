package com.spongycode.weatherapp.screens.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.spongycode.weatherapp.utils.Fonts

@Composable
fun TextBox(
    text: String = "",
    size: TextUnit = 18.sp,
    color: Color = MaterialTheme.colorScheme.primary,
    fontFamily: FontFamily = Fonts.poppinsFamily,
    weight: FontWeight = FontWeight.W400,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        fontSize = size,
        fontFamily = fontFamily,
        fontWeight = weight,
    )
}