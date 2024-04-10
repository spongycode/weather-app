package com.spongycode.weatherapp.screens.home.components

import com.spongycode.weatherapp.utils.Fonts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spongycode.weatherapp.domain.model.City

@Preview
@Composable
fun CityItem(
    modifier: Modifier = Modifier,
    city: City = City(
        city = "Sydney",
        lat = -33.8678,
        lng = 151.2100,
        country = "Australia",
        admin_name = "New South Wales"
    ),
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .clickable {
                onClick()
            }
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 10.dp, bottom = 10.dp, start = 25.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = city.city,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.W600,
            color = MaterialTheme.colorScheme.primary,
            fontFamily = Fonts.poppinsFamily,
            maxLines = 1,
            fontSize = 18.sp,
            overflow = TextOverflow.Ellipsis
        )
    }
}
