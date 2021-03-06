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

package com.github.mauricio.async.db.postgresql.encoders

import com.github.mauricio.async.db.postgresql.messages.backend.ServerMessage
import com.github.mauricio.async.db.postgresql.messages.backend.AuthenticationResponseType
import com.github.mauricio.async.db.postgresql.messages.frontend.CredentialMessage
import com.github.mauricio.async.db.postgresql.messages.frontend.ClientMessage
import com.github.mauricio.async.db.postgresql.util.PasswordHelper
import com.github.mauricio.async.db.util.ByteBufferUtils
import java.nio.charset.Charset
import io.netty.buffer.Unpooled
import io.netty.buffer.ByteBuf

class CredentialEncoder(val charset: Charset) : Encoder {

    override fun encode(message: ClientMessage): ByteBuf {

        val credentialMessage = message as CredentialMessage

        val password = when (credentialMessage.authenticationType) {
            AuthenticationResponseType.Cleartext -> {
                credentialMessage.password.toByteArray(charset)
            }
            AuthenticationResponseType.MD5 -> {
                PasswordHelper.encode(
                        credentialMessage.username,
                        credentialMessage.password,
                        credentialMessage.salt!!,
                        charset)
            }
            else -> ByteArray(0)
        }

        val buffer = Unpooled.buffer(1 + 4 + password.size + 1)
        buffer.writeByte(ServerMessage.PasswordMessage)
        buffer.writeInt(0)
        buffer.writeBytes(password)
        buffer.writeByte(0)

        ByteBufferUtils.writeLength(buffer)

        return buffer
    }

}
