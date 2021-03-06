/*
 * Copyright 2015 Webtrends (http://www.webtrends.com)
 *
 * See the LICENCE.txt file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.webtrends.harness.component.spray.websocket

import akka.actor.Props
import org.slf4j.LoggerFactory

import scala.collection.mutable

/**
 * @author Michael Wallin on 4/16/15.
 */
object WebSocketManager {
  val externalLogger = LoggerFactory.getLogger(this.getClass)

  private val workers = mutable.Map[String, Props]()

  def addWorker[T](path: String, actorClass:Class[T]): Unit = { addWorker[T](path, Props(actorClass)) }

  def addWorker[T](path: String, props:Props): Unit = {
    externalLogger.debug(s"new websocket worker [$path] registered with WebSocketManager")
    workers += path -> props
  }

  def removeCommand(path:String) : Boolean = {
    workers.get(path) match {
      case Some(n) =>
        externalLogger.debug(s"Websocket worker [$path] removed from WebSocketManager")
        workers -= path
        true
      case None => false
    }
  }

  def getWorker(path: String): Option[Props] = {
    workers.get(path)
  }
}
