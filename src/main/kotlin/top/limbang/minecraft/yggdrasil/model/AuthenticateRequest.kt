/*
 * Copyright 2021 Forsaken-Land and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/Forsaken-Land/yggdrasil/blob/master/LICENSE
 */

package top.limbang.minecraft.yggdrasil.model

import kotlinx.serialization.Required
import kotlinx.serialization.Serializable


/**
 * ### 验证请求
 * @param username 用户名
 * @param password 密码
 * @param clientToken 由客户端指定的令牌的 clientToken（可选）
 * @param requestUser 是否在响应中包含用户信息，默认 false
 * @param agent 正版登陆验证需要,默认已经生成
 */
@Serializable
data class AuthenticateRequest(
    val username: String,
    val password: String,
    val clientToken: String? = null,
    val requestUser: Boolean = false,
    @Required val agent: Agent = Agent(),
) {
    @Serializable
    data class Agent(@Required val name: String = "Minecraft", @Required val version: Int = 1)
}