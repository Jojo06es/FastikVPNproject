package com.khti.fastikvpnproject.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.khti.fastikvpnproject.core.constants.Dimens
import com.khti.fastikvpnproject.core.model.ConnectionState
import com.khti.fastikvpnproject.ui.screens.home.components.ConnectionStatusText
import com.khti.fastikvpnproject.ui.screens.home.components.HomeUiState
import com.khti.fastikvpnproject.ui.screens.home.components.IpInfoRow
import com.khti.fastikvpnproject.ui.screens.home.components.VpnConnectButton
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    HomeContent(
        state = state,
        onConnectClick = viewModel::onConnectClick,
        onIpCardClick = viewModel::onIpCardClick,
        onPingRefreshClick = viewModel::onPingRefreshClick,
        modifier = modifier
    )
}
@Composable
private fun HomeContent(
    state: HomeUiState,
    onConnectClick: () -> Unit,
    onIpCardClick: () -> Unit,
    onPingRefreshClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            VpnConnectButton(
                connectionState = state.connectionState,
                onClick = onConnectClick
            )

            Spacer(modifier = Modifier.height(Dimens.SpacingL))

            ConnectionStatusText(connectionState = state.connectionState)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (state.connectionState == ConnectionState.CONNECTED) {
                    formatTime(state.elapsedSeconds)
                } else {
                    "00:00"
                },
                style = MaterialTheme.typography.titleMedium,
                color = if (state.connectionState == ConnectionState.CONNECTED) {
                    MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                } else {
                    Color.Transparent
                }
            )
        }

        IpInfoRow(
            ipAddress = state.ipAddress,
            isIpLoading = state.isIpLoading,
            pingMs = state.pingMs,
            isPingLoading = state.isPingLoading,
            isPingVisible = state.isPingVisible,
            canRefreshPing = state.connectionState == ConnectionState.CONNECTED,
            onCardClick = onIpCardClick,
            onRefreshPingClick = onPingRefreshClick,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = Dimens.SpacingM)
        )
    }
}
private fun formatTime(totalSeconds: Int): String {
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d".format(minutes, seconds)
}