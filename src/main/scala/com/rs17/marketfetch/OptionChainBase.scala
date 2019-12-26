package com.rs17.marketfetch

abstract class OptionChainBase extends StuffHandler {
  def getOptionChain(symbol: String, specific: Option[String], strike: Option[String], side: Option[String], accessToken: Option[String]): String {}

  override def handleArgs(args: Array[String]): Array[Any] = {
    val symbol = args(1)
    val specific = if (args.length < 3) None else Some(args(2))
    val strike = if (args.length < 4) None else Some(args(3))
    val side = if (args.length < 5) None else Some(args(4))
    val retarr = Array(symbol, specific, strike, side)
    retarr.asInstanceOf[Array[Any]]
  }

  def process(args: Array[Any]): Option[Any] = {
    val symbol = args(0).toString()
    val specific = args(1).asInstanceOf[Option[String]]
    val strike = args(2).asInstanceOf[Option[String]]
    val side = args(3).asInstanceOf[Option[String]]
    val accessToken = args(4).asInstanceOf[Option[String]]
    Option(getOptionChain(symbol, specific, strike, side, accessToken))
  }

  // gets specific values from json
  def interpret(jsonresponse: String, specific: Option[String], strike: Option[String], side: Option[String]): String = {
    return "interpreter not defined";
  }

}
