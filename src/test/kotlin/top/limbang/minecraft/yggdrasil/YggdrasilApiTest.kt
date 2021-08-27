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
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test
import top.limbang.minecraft.yggdrasil.model.AuthenticateRequest
import top.limbang.minecraft.yggdrasil.model.ProfileTextures
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO
import kotlin.math.roundToInt


class YggdrasilApiTest {
    private val authUrl = "https://skin.blackyin.xyz/api/yggdrasil/authserver/"
    private val sessionUrl = "https://skin.blackyin.xyz/api/yggdrasil/sessionserver/"
    private val correctAuthenticateRequest = AuthenticateRequest("123456@qq.com", "12345678")
    private val errorAuthenticateRequest = AuthenticateRequest("123456@qq.com", "123456789")

    @Test
    fun createService() {
        val service = YggdrasilApi(authUrl, sessionUrl).get()
        runBlocking {
            runCatching { service.authenticate(correctAuthenticateRequest) }.onSuccess {
                println("登陆成功:${it.selectedProfile?.name}")
            }
            runCatching { service.authenticate(errorAuthenticateRequest) }.onFailure {
                println("登陆失败:${it.message}")
            }
        }
    }

    @Test
    fun downloadSkin() {
        val service = YggdrasilApi(authUrl, sessionUrl).get()
        runBlocking {
            val token = service.authenticate(correctAuthenticateRequest)
            val profile = service.profile(token.selectedProfile!!.id)
            val texturesEncoder = profile.properties?.first { it.name == "textures" }?.value
            val decode = String(Base64.getDecoder().decode(texturesEncoder))
            val skin = Json.decodeFromString<ProfileTextures>(decode).textures.skin ?: return@runBlocking
            val inputStream = service.downloadSkin(skin.url).byteStream()
            val bufferedImage = ImageIO.read(inputStream)
            inputStream.close()
            ImageIO.write(bufferedImage,"png", File("1.png"))
            val avatar = toAvatar(bufferedImage,32)
            ImageIO.write(avatar,"png", File("2.png"))
        }
    }

    private fun toAvatar(skin: BufferedImage, size: Int): BufferedImage? {
        val avatar = BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB)
        val g = avatar.createGraphics()
        val scale = skin.width / 64
        val faceOffset = (size / 18.0).roundToInt()
        g.drawImage(skin,
            faceOffset, faceOffset, size - faceOffset, size - faceOffset,
            8 * scale, 8 * scale, 16 * scale, 16 * scale,
            null)
        g.drawImage(skin,
            0, 0, size, size,
            40 * scale, 8 * scale, 48 * scale, 16 * scale, null)
        g.dispose()
        return avatar
    }
}