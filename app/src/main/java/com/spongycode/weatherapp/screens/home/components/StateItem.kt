package com.spongycode.weatherapp.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spongycode.weatherapp.R
import com.spongycode.weatherapp.utils.Constants
import com.spongycode.weatherapp.utils.Fonts

@Preview
@Composable
fun StateItem(
    modifier: Modifier = Modifier,
    state: String = "",
    onClick: () -> Unit = {},
    isExpanded: Boolean = false
) {
    Row(
        modifier = modifier
            .clickable {
                onClick()
            }
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = state,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.W600,
            color = MaterialTheme.colorScheme.primary,
            fontFamily = Fonts.poppinsFamily,
            maxLines = 1,
            fontSize = 20.sp,
            overflow = TextOverflow.Ellipsis
        )
        Icon(
            modifier = Modifier
                .size(Constants.MEDIUM_HEIGHT * 3 / 4)
                .clickable { onClick() },
            painter = painterResource(
                if (isExpanded) R.drawable.baseline_arrow_drop_up_24
                else R.drawable.baseline_arrow_drop_down_24
            ),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = null
        )
    }
}
