/*
 * Copyright (c) 2024 damahecode.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.code.damahe.res.icon

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

/**
 * Damahe Code icons. Material icons are [ImageVector]s, custom icons are drawable resource IDs.
 */
object MyIcons {
    val Settings = Icons.Rounded.Settings
}

/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */
sealed class DCodeResource {
    data class VectorResource(val imageVector: ImageVector) : DCodeResource()
    data class DrawableResource(@DrawableRes val id: Int) : DCodeResource()
}

/**
 * A Material Design icon component that draws [DCodeResource] using [tint], with a default value of [LocalContentColor].
 */
@Composable
fun DrawIcon(
    icon: DCodeResource,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current
) {
    when (icon) {
        is DCodeResource.VectorResource -> Icon(
            imageVector = icon.imageVector,
            contentDescription = contentDescription,
            modifier = modifier,
            tint = tint
        )

        is DCodeResource.DrawableResource -> Icon(
            painter = painterResource(id = icon.id),
            contentDescription = contentDescription,
            modifier = modifier,
            tint = tint
        )
    }
}

@Composable
fun LottieLoadingView(
    file: String,
    modifier: Modifier = Modifier,
    iterations: Int = 10
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset(file))
    LottieAnimation(
        composition,
        modifier = modifier.defaultMinSize(300.dp),
        iterations = iterations
    )
}