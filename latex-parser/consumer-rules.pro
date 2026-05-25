# =============================================================================
# latex-parser 消费方保留规则（Consumer ProGuard Rules）
#
# 该文件随 AAR 一起发布，会在使用本库的 Android 应用进行 R8/ProGuard 优化时自动生效。
#
# 编写原则（参考 Android 官方《库作者优化指南》）：
#  - 仅保留运行时反射 / JNI / 序列化等无法被 R8 静态分析到的入口点。
#  - 严禁包级 -keep（如 -keep com.hrm.latex.parser.** 会导致下游 App 体积膨胀）。
#  - 严禁全局选项（如 -dontobfuscate / -allowaccessmodification / -ignorewarnings）。
# =============================================================================

# latex-parser 不使用反射、JNI、ServiceLoader 与运行时注解处理，
# 命令注册（CommandRegistry / *Handlers）通过纯 Kotlin 静态调用完成，R8 可完整追踪。
# AST 节点（LatexNode 及其 sealed 子类）以正常 Kotlin 调用方式被消费，
# 因此不需要任何 -keep 规则。
#
# 仅声明对类元数据属性的依赖，避免下游 App 自定义规则误剥离这些属性。

# Kotlin sealed class / data class / 协程 suspend 等所需的元数据
-keepattributes InnerClasses,EnclosingMethod,Signature

# 公共 API 注解（如 @get:JvmName / @JvmField 等）
-keepattributes RuntimeVisibleAnnotations,RuntimeVisibleParameterAnnotations
