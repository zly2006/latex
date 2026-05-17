package com.hrm.latex.renderer.font

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontFamily
import com.hrm.latex.base.log.HLog
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.FontResource
import org.jetbrains.compose.resources.getFontResourceBytes
import org.jetbrains.compose.resources.getSystemResourceEnvironment

private const val TAG = "Latex-font"

/**
 * 解析 [MathFont] 配置，处理 OTF FontResource 的异步加载。
 *
 * - [MathFont.Default]：字体已从 Compose Resources 移除，降级使用系统默认字体。
 *   外部应通过 [MathFont.OTF] 或 [MathFont.TTF] 传入 CDN 下载的字体。
 * - [MathFont.OTF]（FontResource 方式）：per-resource 缓存，异步加载自定义 OTF 字体。
 * - [MathFont.KaTeXTTF]、[MathFont.TTF]、[MathFont.OTF]（预加载方式）：直接透传。
 */
@Composable
internal fun rememberResolvedMathFont(mathFont: MathFont): MathFont {
    return when (mathFont) {
        is MathFont.Default -> {
            // 字体已从 Compose Resources 移除，使用系统降级
            MathFont.KaTeXTTF
        }

        is MathFont.OTF -> {
            if (mathFont.fontResource != null) {
                rememberCustomOtfAsync(mathFont.fontResource)
            } else {
                mathFont
            }
        }

        else -> mathFont
    }
}

// ========== 自定义 OTF per-resource 缓存 ==========

/**
 * 自定义 OTF FontResource 异步加载 — per-resource 缓存。
 *
 * 每个不同的 [fontResource] 独立缓存。
 * 加载前返回 [MathFont.KaTeXTTF]（TTF 降级渲染），
 * 加载完成后返回 [MathFont.OTF]（bytes + fontFamily），触发重组升级。
 */
@Composable
private fun rememberCustomOtfAsync(fontResource: FontResource): MathFont {
    val fontFamily = FontFamily(Font(fontResource))

    var resolved by remember(fontResource) { mutableStateOf<MathFont>(MathFont.KaTeXTTF) }

    LaunchedEffect(fontResource) {
        try {
            val environment = getSystemResourceEnvironment()
            val bytes = getFontResourceBytes(environment, fontResource)
            if (bytes.isNotEmpty()) {
                resolved = MathFont.OTF(bytes, fontFamily)
                HLog.i(TAG, "Custom OTF font loaded: ${bytes.size} bytes")
            } else {
                HLog.e(TAG, "Custom OTF font bytes empty, staying with TTF fallback")
            }
        } catch (e: Exception) {
            HLog.e(TAG, "Custom OTF font load failed, staying with TTF fallback", e)
        }
    }

    return resolved
}
