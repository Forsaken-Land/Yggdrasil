/*
 * Copyright 2021 Forsaken-Land and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/Forsaken-Land/yggdrasil/blob/master/LICENSE
 */

package top.limbang.minecraft.yggdrasil

import org.slf4j.LoggerFactory
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import top.limbang.minecraft.yggdrasil.converter.toConverterFactory
import top.limbang.minecraft.yggdrasil.interceptor.SessionServerInterceptor
import top.limbang.minecraft.yggdrasil.interceptor.YggdrasilInterceptor
import top.limbang.minecraft.yggdrasil.service.YggdrasilApiService

/**
 * ### 创建 YggdrasilApi
 * @param authUrl 默认为正版地址
 * @param sessionUrl 默认为正版地址
 */
class YggdrasilApi(
    authUrl: String = "https://authserver.mojang.com/",
    sessionUrl: String = "https://sessionserver.mojang.com/"
) {

    private val retrofit: Retrofit
    private var service: YggdrasilApiService? = null

    init {
        val format = Json { ignoreUnknownKeys = true }
        val logger = LoggerFactory.getLogger(this.javaClass)

        val httpLoggingInterceptor = HttpLoggingInterceptor {
            logger.debug(it)
        }.apply { level = HttpLoggingInterceptor.Level.BASIC }

        val okhttp = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(YggdrasilInterceptor())
            .addInterceptor(SessionServerInterceptor(authUrl, sessionUrl))
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(authUrl)
            .addConverterFactory(format.toConverterFactory())
            .client(okhttp)
            .build()
    }

    /**
     * ### 创建 YggdrasilApiService
     */
    fun createService(): YggdrasilApiService {
        if (service == null) service = retrofit.create(YggdrasilApiService::class.java)
        return service!!
    }

}