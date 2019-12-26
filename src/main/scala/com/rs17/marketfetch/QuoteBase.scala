package com.rs17.marketfetch

abstract class QuoteBase extends StuffHandler {
  def getQuote(symbol: String, specific: Option[String], accessToken: Option[String]): String {}

  override def handleArgs(args: Array[String]): Array[Any] = {
    val symbol = args(0)
    val specific = if (args.length < 2) None else Some(args(1))
    val retarr = Array(symbol, specific)
    retarr.asInstanceOf[Array[Any]]
  }

  def process(args: Array[Any]): Option[Any] = {
    val symbol = args(0).toString()
    val specific = args(1).asInstanceOf[Option[String]]
    val accessToken = args(2).asInstanceOf[Option[String]]
    Option(getQuote(symbol, specific, accessToken))
  }
}
