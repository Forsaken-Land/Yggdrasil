/*
 * Copyright 2024 limbang and contributors.
 *
 * 此源代码的使用受 GNU AGPLv3 许可证的约束，该许可证可在"LICENSE"文件中找到。
 * Use of this source code is governed by the GNU AGPLv3 license that can be found in the "LICENSE" file.
 */

package top.limbang.minecraft.yggdrasil.converter

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

fun Json.toConverterFactory() = JsonConverterFactory(this)

/**
 * ### Json Converter Factory
 * 使用 Kotlin serialization 转换 Json
 * @param format 自定义配置的 Json
 */
class JsonConverterFactory(private val format:Json) : Converter.Factory() {
    private val mediaType = "application/json; charset=utf-8".toMediaType()

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): Converter<ResponseBody, *> {
        val serializer = format.serializersModule.serializer(type)
        return JsonResponseBodyConverter(serializer, format)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): Converter<*, RequestBody> {
        val serializer = format.serializersModule.serializer(type)
        return JsonRequestBodyConverter(mediaType, serializer, format)
    }
}

/**
 * ### Json 请求内容 Converter
 */
class JsonRequestBodyConverter<T : Any>(
    private val contentType: MediaType,
    private val saver: SerializationStrategy<T>,
    private val format: Json,
) : Converter<T, RequestBody> {
    override fun convert(value: T): RequestBody {
        return format.encodeToString(saver, value).toRequestBody(contentType)
    }
}

/**
 * ### Json 响应内容 Converter
 */
class JsonResponseBodyConverter<T>(
    private val loader: DeserializationStrategy<T>,
    private val format: Json,
) : Converter<ResponseBody, T> {
    override fun convert(value: ResponseBody): T {
        return format.decodeFromString(loader, value.string())
    }
}