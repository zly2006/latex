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
import com.hrm.latex.parser.visitor.MathMLVisitor
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class AtopTest {
    private val parser = LatexParser()

    @Test
    fun should_parse_atop_as_ruleless_fraction() {
        val group = parser.parse("{g_1+g_2 \\atop i+j=n-2}").children.single()
        assertIs<LatexNode.Group>(group)
        val fraction = group.children.single()
        assertIs<LatexNode.Fraction>(fraction)
        assertEquals(LatexNode.Fraction.FractionStyle.RULELESS, fraction.style)
    }

    @Test
    fun should_parse_atop_in_document_math_list() {
        val fraction = parser.parse("a \\atop b").children.single()

        assertIs<LatexNode.Fraction>(fraction)
        assertEquals(LatexNode.Fraction.FractionStyle.RULELESS, fraction.style)
        assertEquals(0, fraction.sourceRange?.start)
        assertEquals(9, fraction.sourceRange?.end)
        assertEquals(0, fraction.numerator.sourceRange?.start)
        assertEquals(2, fraction.numerator.sourceRange?.end)
        assertEquals(7, fraction.denominator.sourceRange?.start)
        assertEquals(9, fraction.denominator.sourceRange?.end)
    }

    @Test
    fun should_parse_atop_in_inline_and_display_math_lists() {
        val inline = parser.parse("\$a \\atop b\$").children.single()
        val inlineMath = assertIs<LatexNode.InlineMath>(inline)
        assertIs<LatexNode.Fraction>(inlineMath.children.single())

        val display = parser.parse("\$\$a \\atop b\$\$").children.single()
        val displayMath = assertIs<LatexNode.DisplayMath>(display)
        assertIs<LatexNode.Fraction>(displayMath.children.single())
    }

    @Test
    fun should_emit_zero_line_thickness_mathml() {
        val document = parser.parse("{a \\atop b}")
        val mathMl = MathMLVisitor.convert(document)
        assertTrue(mathMl.contains("""linethickness="0""""))
    }

    @Test
    fun should_keep_regular_fraction_rule() {
        val fraction = parser.parse("\\frac{a}{b}").children.single()
        assertIs<LatexNode.Fraction>(fraction)
        assertEquals(LatexNode.Fraction.FractionStyle.NORMAL, fraction.style)
    }
}
