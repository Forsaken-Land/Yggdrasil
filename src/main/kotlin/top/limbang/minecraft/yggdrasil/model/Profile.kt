/*
 * Copyright 2024 limbang and contributors.
 *
 * 此源代码的使用受 GNU AGPLv3 许可证的约束，该许可证可在"LICENSE"文件中找到。
 * Use of this source code is governed by the GNU AGPLv3 license that can be found in the "LICENSE" file.
 */

package top.limbang.minecraft.yggdrasil.model

import kotlinx.serialization.Serializable


/**
 * ### 角色
 * @param id 角色 UUID（无符号）
 * @param name 角色名称
 * @param properties 角色的属性（数组，每一元素为一个属性）（仅在特定情况下需要包含）
 */
@Serializable
data class Profile(val id: String, val name: String, val properties: List<Properties>? = null) {

    /**
     * ### 角色的属性
     * @param name 属性的名称
     * @param value 属性的值
     * @param signature 属性值的数字签名（仅在特定情况下需要包含）
     */
    @Serializable
    data class Properties(val name: String, val value: String, val signature: String? = null)
}