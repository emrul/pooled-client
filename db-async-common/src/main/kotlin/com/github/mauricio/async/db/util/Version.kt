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

import scala.util._
import scala.util.Success

object Version {

  private fun tryParse( index : Int, pieces : Array[String] ) : Int = {

    Try {
      pieces(index).toInt
    } match {
      case Success(value) => value
      case Failure(e) => 0
    }

  }

  fun apply( version : String ) : Version = {
    val pieces = version.split('.')
    new Version( tryParse(0, pieces), tryParse(1, pieces), tryParse(2, pieces) )
  }

}

case class Version( major : Int, minor : Int, maintenance : Int ) :  Ordered[Version] {
  override fun compare( y: Version): Int = {

    if ( this == y ) {
      return 0
    }

    if ( this.major != y.major ) {
      return this.major.compare(y.major)
    }

    if ( this.minor != y.minor ) {
      return this.minor.compare(y.minor)
    }

    this.maintenance.compare(y.maintenance)
  }
}
