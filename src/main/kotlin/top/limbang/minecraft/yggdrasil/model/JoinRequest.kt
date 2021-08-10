/*
 * Copyright 2021 Forsaken-Land and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/Forsaken-Land/yggdrasil/blob/master/LICENSE
 */

package top.limbang.minecraft.yggdrasil.model


/**
 * ### 客户端进入服务器请求
 * @param accessToken 令牌的 accessToken
 * @param selectedProfile 该令牌绑定的角色的 UUID（无符号
 * @param serverId 服务端发送给客户端的 serverId
 */
data class JoinRequest(val accessToken: String, val selectedProfile: String, val serverId: String)