/*
 * Copyright (c) 2026 huarangmeng
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.hrm.latex.renderer.model

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import kotlin.test.Test
import kotlin.test.assertEquals

class LatexThemeTest {
    private val defaultLightColors = LatexThemeColors(
        color = Color.Black,
        backgroundColor = Color.Transparent
    )

    private val defaultDarkColors = LatexThemeColors(
        color = Color.White,
        backgroundColor = Color.Transparent
    )

    @Test
    fun autoThemeFollowsSystemTheme() {
        val theme = LatexTheme.auto()

        assertEquals(defaultLightColors, theme.resolve(systemInDarkTheme = false))
        assertEquals(defaultDarkColors, theme.resolve(systemInDarkTheme = true))
    }

    @Test
    fun fixedThemeIgnoresSystemTheme() {
        assertEquals(defaultLightColors, LatexTheme.light().resolve(systemInDarkTheme = true))
        assertEquals(defaultDarkColors, LatexTheme.dark().resolve(systemInDarkTheme = false))
    }

    @Test
    fun material3MapsOnSurfaceAndSurfaceForLightScheme() {
        val scheme = lightColorScheme(
            surface = Color(0xFFF7F7F7),
            onSurface = Color(0xFF111111)
        )

        val theme = LatexTheme.material3(scheme)

        assertEquals(
            LatexThemeColors(
                color = Color(0xFF111111),
                backgroundColor = Color(0xFFF7F7F7)
            ),
            theme.resolve(systemInDarkTheme = false)
        )
    }

    @Test
    fun material3MapsOnSurfaceAndSurfaceForDarkScheme() {
        val scheme = darkColorScheme(
            surface = Color(0xFF121212),
            onSurface = Color(0xFFF0F0F0)
        )

        val theme = LatexTheme.material3(scheme)

        assertEquals(
            LatexThemeColors(
                color = Color(0xFFF0F0F0),
                backgroundColor = Color(0xFF121212)
            ),
            theme.resolve(systemInDarkTheme = false)
        )
    }
}
