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

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.hrm.latex.parser.model.LatexNode
import kotlin.test.Test
import kotlin.test.assertEquals

class RenderStyleFontSizeTest {
    @Test
    fun fontSizeDeclarationUsesBaseFontSizeInsteadOfCurrentFontSize() {
        val context = RenderContext(
            fontSize = 20.sp,
            baseFontSize = 20.sp,
            color = Color.Black
        )

        val small = context.applyFontSize(LatexNode.FontSize.SizeType.SMALL)
        val hugeInsideSmall = small.applyFontSize(LatexNode.FontSize.SizeType.HUGE_2)

        assertEquals(18f, small.fontSize.value, 0.001f)
        assertEquals(49.76f, hugeInsideSmall.fontSize.value, 0.001f)
    }
}
