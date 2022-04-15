/*
 * Copyright 2021 Forsaken-Land and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/Forsaken-Land/yggdrasil/blob/master/LICENSE
 */

package top.limbang.minecraft.yggdrasil.interceptor

import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Invocation
import top.limbang.minecraft.yggdrasil.exception.YggdrasilException
import top.limbang.minecraft.yggdrasil.model.ErrorMessage
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets


/**
 * ### Yggdrasil 拦截器
 */
class YggdrasilInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        // HTTP 不在 200-300 的错误状态码处理
        if (response.isSuccessful.not()) {
            val errorMessage = try {
                Json.decodeFromString(getResponseBody(response.body!!))
            } catch (e: SerializationException) {
                ErrorMessage(e.localizedMessage,"序列化错误!!!")
            }
            throw YggdrasilException(errorMessage)
        }
        // HTTP 204 处理
        if (response.code == 204) {
            // 获取方法名称
            when (request.tag(Invocation::class.java)?.method()?.name) {
                "profile" -> throw YggdrasilException(ErrorMessage("ForbiddenOperationException", "角色不存在!!!"))
            }
            return response.newBuilder()
                .message("OK")
                .code(200)
                .body("true".toResponseBody("application/json".toMediaType()))
                .build()
        }
        return response
    }

    private fun getResponseBody(responseBody: ResponseBody): String {
        val contentLength = responseBody.contentLength()
        val source = responseBody.source()
        source.request(Long.MAX_VALUE) // Buffer the entire body.
        val buffer = source.buffer
        val contentType = responseBody.contentType()
        val charset: Charset = contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8
        return if (contentLength != 0L) buffer.clone().readString(charset) else ""
    }

}

