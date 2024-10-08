/*
 * Copyright 2021 Forsaken-Land and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/Forsaken-Land/yggdrasil/blob/master/LICENSE
 */

const val kotlinVersion = "2.0.20"
const val coroutinesVersion = "1.9.0"
const val retrofitVersion = "2.9.0"
const val okhttpVersion = "5.0.0-alpha.11"
const val serializationJsonVersion = "1.7.3"

fun kotlinx(id: String, version: String) = "org.jetbrains.kotlinx:kotlinx-$id:$version"