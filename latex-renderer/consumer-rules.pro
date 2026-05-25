# =============================================================================
# latex-renderer 消费方保留规则（Consumer ProGuard Rules）
#
# 该文件随 AAR 一起发布，会在使用本库的 Android 应用进行 R8/ProGuard 优化时自动生效。
#
# 编写原则（参考 Android 官方《库作者优化指南》）：
#  - 仅保留运行时反射 / JNI / 序列化等无法被 R8 静态分析到的入口点。
#  - 严禁包级 -keep（如 -keep com.hrm.latex.renderer.** 会导致下游 App 体积膨胀）。
#  - 严禁全局选项（如 -dontobfuscate / -allowaccessmodification / -ignorewarnings）。
#  - Compose 运行时（compose.runtime / compose.foundation 等）已自带 consumer-rules，
#    本文件不再重复声明。
# =============================================================================

# latex-renderer 不直接使用反射 / JNI / ServiceLoader，
# Measurer 注册表通过 KClass 作为 Map 键，在 R8 优化下行为正确（不依赖类名）。
# Compose Resources（katex / stix 字体）通过生成的 Res.font.* 静态调用访问，
# Compose Multiplatform 自带 consumer-rules 已覆盖资源加载链路。

# 字体度量在解析 OTF/CFF 表时依赖类元数据（Signature / InnerClasses 等）以保证
# Kotlin 反射读取 sealed/data 类层级时正常工作。
-keepattributes InnerClasses,EnclosingMethod,Signature

# 公共 API 注解（@OptIn / @Stable / @Immutable 等运行时注解）
-keepattributes RuntimeVisibleAnnotations,RuntimeVisibleParameterAnnotations
