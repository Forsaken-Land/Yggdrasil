/*
 * Copyright 2024 limbang and contributors.
 *
 * 此源代码的使用受 GNU AGPLv3 许可证的约束，该许可证可在"LICENSE"文件中找到。
 * Use of this source code is governed by the GNU AGPLv3 license that can be found in the "LICENSE" file.
 */

package top.limbang.minecraft.yggdrasil.model

import kotlinx.serialization.Serializable


/**
 * ### 客户端进入服务器请求
 * @param accessToken 令牌的 accessToken
 * @param selectedProfile 该令牌绑定的角色的 UUID（无符号
 * @param serverId 服务端发送给客户端的 serverId
 */
@Serializable
data class JoinRequest(val accessToken: String, val selectedProfile: String, val serverId: String)