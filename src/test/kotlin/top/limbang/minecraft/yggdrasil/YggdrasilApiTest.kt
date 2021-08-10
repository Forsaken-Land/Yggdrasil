/*
 * Copyright 2021 Forsaken-Land and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/Forsaken-Land/yggdrasil/blob/master/LICENSE
 */

package top.limbang.minecraft.yggdrasil

import kotlinx.coroutines.runBlocking
import org.junit.Test
import top.limbang.minecraft.yggdrasil.model.AuthenticateRequest



class YggdrasilApiTest {
    private val authUrl = "https://skin.blackyin.xyz/api/yggdrasil/authserver/"
    private val sessionUrl = "https://skin.blackyin.xyz/api/yggdrasil/sessionserver/"
    private val correctAuthenticateRequest = AuthenticateRequest("x@x.com", "12345678")
    private val errorAuthenticateRequest = AuthenticateRequest("x@x.com", "12345678")

    @Test
    fun createService() {
        val service = YggdrasilApi(authUrl, sessionUrl).createService()
        runBlocking {
            runCatching { service.authenticate(correctAuthenticateRequest) }.onSuccess {
                println("登陆成功:${it.selectedProfile?.name}")
            }
            runCatching { service.authenticate(errorAuthenticateRequest) }.onFailure {
                println("登陆失败:${it.message}")
            }
        }
    }
}