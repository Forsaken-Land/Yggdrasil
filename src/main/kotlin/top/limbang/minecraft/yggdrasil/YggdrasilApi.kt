/*
 * Copyright 2021 Forsaken-Land and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/Forsaken-Land/yggdrasil/blob/master/LICENSE
 */

package top.limbang.minecraft.yggdrasil

import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import top.limbang.minecraft.yggdrasil.converter.toConverterFactory
import top.limbang.minecraft.yggdrasil.interceptor.SessionServerInterceptor
import top.limbang.minecraft.yggdrasil.interceptor.YggdrasilInterceptor
import top.limbang.minecraft.yggdrasil.service.YggdrasilApiService
import java.util.concurrent.TimeUnit

/**
 * ### 创建 YggdrasilApi
 * @param authUrl 默认为正版地址
 * @param sessionUrl 默认为正版地址
 * @param httpLoggingInterceptor okhttp httpLogging 默认不添加
 * @param format Json 自定义的 [Json],默认是忽略输入 JSON 中遇到的未知属性
 */
class YggdrasilApi(
    authUrl: String = "https://authserver.mojang.com/",
    sessionUrl: String = "https://sessionserver.mojang.com/",
    httpLoggingInterceptor: Interceptor? = null,
    format: Json = Json { ignoreUnknownKeys = true },
) {

    /**
     * ### 创建 okhttp 客户端
     */
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .also { if (httpLoggingInterceptor != null) it.addInterceptor(httpLoggingInterceptor) }
            .addInterceptor(YggdrasilInterceptor())
            .addInterceptor(SessionServerInterceptor(authUrl, sessionUrl))
            .build()
    }

    /**
     * ### 创建 yggdrasilApi 服务
     */
    private val yggdrasilApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(authUrl)
            .addConverterFactory(format.toConverterFactory())
            .client(okHttpClient)
            .build()
        retrofit.create(YggdrasilApiService::class.java)
    }


    /**
     * ### 获取 YggdrasilApiService
     */
    fun get() : YggdrasilApiService{
        return yggdrasilApi
    }
}