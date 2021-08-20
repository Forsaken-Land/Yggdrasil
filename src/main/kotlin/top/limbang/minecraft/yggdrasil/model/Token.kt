/*
 * Copyright 2021 Forsaken-Land and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/Forsaken-Land/yggdrasil/blob/master/LICENSE
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