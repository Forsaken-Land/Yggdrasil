/*
 * Copyright 2024 limbang and contributors.
 *
 * 此源代码的使用受 GNU AGPLv3 许可证的约束，该许可证可在"LICENSE"文件中找到。
 * Use of this source code is governed by the GNU AGPLv3 license that can be found in the "LICENSE" file.
 */

package top.limbang.minecraft.yggdrasil.model

import kotlinx.serialization.Serializable

/**
 * ### yggdrasil 错误信息
 */
@Serializable
data class ErrorMessage(val error: String, val errorMessage: String, val cause: String? = null)

