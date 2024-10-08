/*
 * Copyright 2024 limbang and contributors.
 *
 * 此源代码的使用受 GNU AGPLv3 许可证的约束，该许可证可在"LICENSE"文件中找到。
 * Use of this source code is governed by the GNU AGPLv3 license that can be found in the "LICENSE" file.
 */

package top.limbang.minecraft.yggdrasil.interceptor

import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import top.limbang.minecraft.yggdrasil.annotation.SessionServer


/**
 * ### SessionServer 拦截器
 * 拦截使用 [SessionServer] 注解的请求,并将 [authUrl] 替换成 [sessionUrl]
 * @param authUrl 验证服务器 url
 * @param sessionUrl 会话服务器 url
 */
class SessionServerInterceptor(private val authUrl: String, private val sessionUrl: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        // 获取 retrofit 中定义 method
        val retrofitMethod = request.tag(Invocation::class.java)?.method() ?: return chain.proceed(request)
        // 判断是否有SessionServer注解
        retrofitMethod.annotations.forEach {
            if (it is SessionServer) {
                val httpUrl = request.url.toString().replace(authUrl, sessionUrl).toHttpUrl()
                return chain.proceed(
                    request.newBuilder()
                        .url(httpUrl)
                        .build()
                )
            }
        }
        return chain.proceed(request)
    }
}