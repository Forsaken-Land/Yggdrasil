/*
 * Copyright 2024 limbang and contributors.
 *
 * 此源代码的使用受 GNU AGPLv3 许可证的约束，该许可证可在"LICENSE"文件中找到。
 * Use of this source code is governed by the GNU AGPLv3 license that can be found in the "LICENSE" file.
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