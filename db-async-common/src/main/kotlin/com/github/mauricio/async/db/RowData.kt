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

package com.github.mauricio.async.db

/**
 *
 * Represents a row from a database, allows clients to access rows by column number or column name.
 *
 */

interface RowData {

  /**
   *
   * Returns a column value by it's position in the originating query.
   *
   * @param columnNumber
   * @return
   */

  operator fun get( columnNumber : Int ) : Any?

  /**
   *
   * Returns a column value by it's name in the originating query.
   *
   * @param columnName
   * @return
   */

  operator fun get( columnNumber : String ) : Any?

  /**
   *
   * Number of this row in the query results. Counts start at 0.
   *
   * @return
   */

  val rowNumber : Int

  operator fun iterator(): Iterator<Any?>

}