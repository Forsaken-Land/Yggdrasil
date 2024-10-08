/*
 * Copyright 2024 limbang and contributors.
 *
 * 此源代码的使用受 GNU AGPLv3 许可证的约束，该许可证可在"LICENSE"文件中找到。
 * Use of this source code is governed by the GNU AGPLv3 license that can be found in the "LICENSE" file.
 */

package top.limbang.minecraft.yggdrasil.model

import kotlinx.serialization.Serializable


/**
 * ### 验证请求
 * @param accessToken 令牌的 accessToken
 * @param clientToken 令牌的 clientToken（可选）
 */
@Serializable
data class Token(
    val accessToken: String,
    val clientToken: String? = null,
    val selectedProfile: Profile? = null,
)