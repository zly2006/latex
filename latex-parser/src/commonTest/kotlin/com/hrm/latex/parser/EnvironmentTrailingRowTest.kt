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

class EnvironmentTrailingRowTest {
    @Test
    fun should_ignore_whitespace_after_trailing_matrix_separator() {
        val matrix = LatexParser().parse(
            "\\begin{matrix}a & b \\\\ c & d \\\\ \\end{matrix}"
        ).children.single()

        assertEquals(2, assertIs<LatexNode.Matrix>(matrix).rows.size)
    }

    @Test
    fun should_ignore_whitespace_after_trailing_cases_separator() {
        val cases = LatexParser().parse(
            "\\begin{cases}a & x > 0 \\\\ b & x < 0 \\\\ \\end{cases}"
        ).children.single()

        assertEquals(2, assertIs<LatexNode.Cases>(cases).cases.size)
    }
}
