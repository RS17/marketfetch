package com.rs17.marketfetch


import scalaj.http._

import scala.collection.mutable.LinkedHashMap

object MainObject {
  def main(args: Array[String]) {

    // get the handler that determines what happens
    val handler = StuffHandler.getHandler(args(0))

    // get quote or whatever
    val procArgs = handler.handleArgs(args) // get args and append token.  Token is always last
    println(handler.process(procArgs).getOrElse("no response"))
  }

}


