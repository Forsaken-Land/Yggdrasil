/*
 * Copyright 2021 Forsaken-Land and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/Forsaken-Land/yggdrasil/blob/master/LICENSE
 */

package top.limbang.minecraft.yggdrasil.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * ### 玩家皮肤纹理
 * @param timestamp 时间戳
 * @param profileId 玩家 id
 * @param profileName 玩家名称
 * @param isPublic 是否公开
 * @param textures 皮肤纹理
 */
@Serializable
data class ProfileTextures(
    val timestamp: ULong,
    val profileId: String,
    val profileName: String,
    val isPublic: Boolean,
    val textures: Textures,
)

/**
 * ### 皮肤纹理
 */
@Serializable
data class Textures(
    @SerialName("SKIN")
    val skin: Skin? = null,
) {
    @Serializable
    data class Skin(
        val url: String,
        val metadata: Metadata,
    ) {
        @Serializable
        data class Metadata(val model: SkinModel = SkinModel.classic)
    }

    /**
     * ### 皮肤模型
     * - [slim] Alex模型
     * - [classic] Steve模型
     */
    enum class SkinModel { slim, classic }
}


