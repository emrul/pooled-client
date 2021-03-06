/*
 * Copyright 2013 Maurício Linhares
 *
 * Maurício Linhares licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.github.mauricio.async.db.util

data class Version(val major: Int, val minor: Int, val maintenance: Int) : Comparable<Version> {
    companion object {
        private fun tryParse(index: Int, pieces: Array<String>): Int =
                try {
                    pieces[index].toInt()
                } catch(e: Throwable) {
                    0
                }
    }

    constructor(pieces: Array<String>) :
            this(tryParse(0, pieces), tryParse(1, pieces), tryParse(2, pieces))

    constructor(version: String) : this(version.split('.').toTypedArray())

    override fun compareTo(other: Version): Int {
        if (this.major != other.major) {
            return this.major - other.major
        }

        if (this.minor != other.minor) {
            return this.minor - other.minor
        }

        return this.maintenance - other.maintenance
    }
}
