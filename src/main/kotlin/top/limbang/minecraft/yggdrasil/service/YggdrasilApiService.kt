/*
 * Copyright 2021 Forsaken-Land and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/Forsaken-Land/yggdrasil/blob/master/LICENSE
 */

package top.limbang.minecraft.yggdrasil.service


import retrofit2.http.*
import top.limbang.minecraft.yggdrasil.annotation.SessionServer
import top.limbang.minecraft.yggdrasil.model.*


interface YggdrasilApiService {

    /**
     * ### 登录
     * 使用密码进行身份验证，并分配一个新的令牌。
     */
    @POST("authenticate")
    suspend fun authenticate(@Body authenticateRequest: AuthenticateRequest): Token

    /**
     * ### 登出
     * 吊销用户的所有令牌。
     */
    @POST("signout")
    suspend fun signout(@Body action: Account) : Boolean

    /**
     * ### 刷新令牌
     * 吊销原令牌，并颁发一个新的令牌。
     * @param token Token 如果 [Token.selectedProfile] 不为 null ，那么这就是一个选择角色的操作,原令牌要未绑定角色不然就会报错.
     */
    @POST("refresh")
    suspend fun refresh(@Body token: Token): Token

    /**
     * ### 检验令牌
     * 检验令牌是否有效。
     */
    @POST("validate")
    suspend fun validate(@Body token: Token): Boolean

    /**
     * ### 吊销令牌
     * 吊销给定令牌。
     */
    @POST("invalidate")
    suspend fun invalidate(@Body token: Token)

    /**
     * ### 客户端进入服务器
     * 记录服务端发送给客户端的 serverId，以备服务端检查。
     */
    @SessionServer
    @POST("session/minecraft/join")
    suspend fun join(@Body joinRequest: JoinRequest): Boolean

    /**
     * ### 服务端验证客户端
     * 检查客户端会话的有效性，即数据库中是否存在该 serverId 的记录，且信息正确
     */
    @SessionServer
    @GET("session/minecraft/hasJoined")
    suspend fun hasJoined(
        @Query("username") username: String,
        @Query("serverId") serverId: String,
        @Query("ip") ip: String,
    ): Profile

    /**
     * ### 查询角色属性
     * 查询指定角色的完整信息（包含角色属性）
     */
    @SessionServer
    @GET("session/minecraft/profile/{uuid}")
    suspend fun profile(
        @Path("uuid") uuid: String,
        @Query("unsigned") unsigned: Boolean = true
    ): Profile
}