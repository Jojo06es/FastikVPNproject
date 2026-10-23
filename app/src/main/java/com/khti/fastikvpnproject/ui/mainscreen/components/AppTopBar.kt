package com.khti.fastikvpnproject.ui.mainscreen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.khti.fastikvpnproject.R
import com.khti.fastikvpnproject.core.constants.Dimens
import com.khti.fastikvpnproject.core.theme.FastikTheme

// Верхняя шапка приложения: слева название FastikVPN.
@Composable
fun AppTopBar(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.ScreenPadding, vertical = Dimens.SpacingM),
        // Прижимаем содержимое к левому краю по центру высоты
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = stringResource(R.string.app_name),
            // Стиль (размер и жирность) берём из темы, цвет - из наших дополнительных цветов
            style = MaterialTheme.typography.headlineMedium,
            color = FastikTheme.colors.title
        )
    }
}