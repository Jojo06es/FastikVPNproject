package com.khti.fastikvpnproject.ui.screens.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.khti.fastikvpnproject.R
import com.khti.fastikvpnproject.core.constants.AnimationConstants
import com.khti.fastikvpnproject.core.constants.Dimens
import com.khti.fastikvpnproject.core.model.ConnectionState
import com.khti.fastikvpnproject.core.theme.FastikTheme
import kotlin.math.ceil

@Composable
fun VpnConnectButton(
    connectionState: ConnectionState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val glowTarget = when (connectionState) {
        ConnectionState.DISCONNECTED -> 0f
        ConnectionState.CONNECTING -> 0.5f
        ConnectionState.CONNECTED -> 1f
    }

    val glow by animateFloatAsState(
        targetValue = glowTarget,
        animationSpec = tween(durationMillis = AnimationConstants.CONNECT_BUTTON_MS),
        label = "glow"
    )

    val isConnecting = connectionState == ConnectionState.CONNECTING

    val rotation = remember { Animatable(0f) }

    LaunchedEffect(isConnecting) {
        if (isConnecting) {
            //вращения кнопки
            rotation.animateTo(
                targetValue = rotation.value + 360f * 1000,
                animationSpec = tween(durationMillis = 1200 * 1000, easing = LinearEasing)
            )
        } else {
            val target = ceil(rotation.value / 360f) * 360f //остановка вращения
            rotation.animateTo(
                targetValue = target,
                animationSpec = tween(durationMillis = 400, easing = LinearEasing)
            )
        }
    }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(durationMillis = 120),
        label = "pressScale"
    )

    val description = stringResource(R.string.cd_connect_button)
    val canClick = enabled && connectionState != ConnectionState.CONNECTING

    Box(
        modifier = modifier.size(Dimens.ThirdCircle),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = pressScale
                    scaleY = pressScale
                },
            contentAlignment = Alignment.Center
        ) {
            // Третье кольцо
            Box(
                modifier = Modifier
                    .size(Dimens.ThirdCircle)
                    .graphicsLayer { alpha = (0.06f + 0.14f * glow) }
                    .background(FastikTheme.colors.connectRingThird, CircleShape)
            )

            // Второе кольцо
            Box(
                modifier = Modifier
                    .size(Dimens.SecondCircle)
                    .graphicsLayer { alpha = (0.12f + 0.18f * glow) }
                    .background(FastikTheme.colors.connectRingSecond, CircleShape)
            )

            // Сама кнопка
            Box(
                modifier = Modifier
                    .size(Dimens.FirstCircle)
                    .graphicsLayer { alpha = 0.55f + 0.45f * glow }
                    .clip(CircleShape)
                    .background(
                        if (enabled) {
                            Brush.linearGradient(
                                listOf(
                                    FastikTheme.colors.connectGradientStart,
                                    FastikTheme.colors.connectGradientEnd
                                )
                            )
                        } else {
                            SolidColor(FastikTheme.colors.disabledContainer)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Wifi,
                    contentDescription = null,
                    tint = if (enabled) MaterialTheme.colorScheme.onPrimary else FastikTheme.colors.disabledContent,
                    modifier = Modifier
                        .size(Dimens.WifiIconWidth)
                        .graphicsLayer {
                            rotationZ = rotation.value
                        }
                )
            }
        }

        // Эффект свечения
        if (glow > 0f) {
            Box(
                modifier = Modifier
                    .size(Dimens.FirstCircle)
                    .graphicsLayer {
                        alpha = glow
                        scaleX = 1.6f
                        scaleY = 1.6f
                    }
                    .clip(CircleShape)
                    .blur(100.dp)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                FastikTheme.colors.connectGradientStart,
                                FastikTheme.colors.connectGradientEnd.copy(alpha = 0.7f),
                                Color.Transparent
                            )
                        )
                    )
            )
        }

        // Невидимый слой нажатия
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .semantics { contentDescription = description }
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = canClick,
                    role = Role.Button,
                    onClick = onClick
                )
        )
    }
}