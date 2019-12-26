package com.rs17.marketfetch

import scalaj.http.Http
import scala.collection.mutable.LinkedHashMap

class QuoteTDA extends QuoteBase with APITDA {
  // takes symbol and access token, returns quote.  Specific gets specific result from quote
  def getQuote(symbol: String, specific: Option[String], accessToken: Option[String]): String = {
    val quoteUri = "https://api.tdameritrade.com/v1/marketdata/" + symbol + "/quotes"
    val authorization = accessToken.getOrElse("")

    val response = Http(quoteUri).header("Authorization", authorization).asString
    val json = ujson.read(response.body)

    val genresult = json.value.asInstanceOf[LinkedHashMap[String, ujson.Obj]](symbol)

    if (specific.isDefined) genresult.value.asInstanceOf[LinkedHashMap[String, AnyVal]](specific.get).toString else genresult.toString()
  }

}
