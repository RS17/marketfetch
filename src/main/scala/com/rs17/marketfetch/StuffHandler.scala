package com.rs17.marketfetch

import scala.collection.mutable.LinkedHashMap

object StuffHandler {

  def getHandler(firstarg: String): StuffHandler = {
    firstarg match {
      case "option" => new OptionChainTDA
      case "optionsecret" => new OptionChainTDASecret
      case "repeater" => new Repeater
      case _ => new QuoteTDA // assume quote if no first argument
    }
  }
}

abstract class StuffHandler {
  def handleArgs(args: Array[String]): Array[Any] = {
    return new Array[Any](0)
  }

  def process(args: Array[Any]): Option[Any]
}
