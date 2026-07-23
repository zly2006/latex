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

package com.hrm.latex.parser

import com.hrm.latex.parser.model.LatexNode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class EnvironmentRowSpacingTest {
    @Test
    fun should_preserve_explicit_empty_rows() {
        val aligned = LatexParser().parse(
            "\\begin{aligned}\\\\a&=b\\\\\\\\c&=d\\end{aligned}"
        ).children.single()

        assertEquals(4, assertIs<LatexNode.Aligned>(aligned).rows.size)
    }

    @Test
    fun should_not_treat_optional_row_spacing_as_a_row() {
        val array = LatexParser().parse(
            "\\begin{array}{cc}a&b\\\\[.2cm]\\hline c&d\\end{array}"
        ).children.single()
        val parsed = assertIs<LatexNode.Array>(array)

        assertEquals(3, parsed.rows.size)
        assertEquals(LatexNode.RowGap(0.2, "cm"), parsed.rowGaps.single())
    }

    @Test
    fun should_not_consume_environment_after_unclosed_row_spacing() {
        val array = LatexParser().parse(
            "\\begin{array}{cc}a&b\\\\[.2cm c&d\\end{array}"
        ).children.single()

        assertEquals(2, assertIs<LatexNode.Array>(array).rows.size)
    }
}
