package com.rs17.marketfetch

object StuffHandler {

  def getHandler(firstarg: String): StuffHandler = {
    // edit MFProperties.classList to add new commands/classes
    return MFProperties.commandList.getOrElse(firstarg, new QuoteTDA)
  }
}

abstract class StuffHandler {
  def handleArgs(args: Array[String]): Array[Any] = {
    return new Array[Any](0)
  }

  def process(args: Array[Any]): Option[Any]
}