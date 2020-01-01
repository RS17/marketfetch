package com.rs17.marketfetch

import scalaj.http.Http
import scala.collection.mutable.LinkedHashMap

class OptionChainTDA extends OptionChainBase with APITDA {

  override def handleArgs(args: Array[String]): Array[Any] = super.handleArgs(args)

  def getOptionChain(symbol: String, specific: Option[String], strike: Option[String], side: Option[String], accessToken: Option[String]): String = {
    val quoteUri = "https://api.tdameritrade.com/v1/marketdata/chains"
    val authorization = accessToken.getOrElse("")
    val request = Http(quoteUri).header("Authorization", authorization)
      .param("symbol", symbol)
      .param("strategy", "SINGLE")
    val response = request.asString.body
    interpret(response, specific, strike, side)
  }

  override def interpret(jsonresponse: String, specific: Option[String], strike: Option[String], side: Option[String]): String = {
    val json = ujson.read(jsonresponse)
    val resultlhm = json.value.asInstanceOf[LinkedHashMap[String, ujson.Obj]]
    // get calls and puts
    val calls = if (side == Some("call") || side == None) Some(resultlhm("callExpDateMap")) else None
    val puts = if (side == Some("put") || side == None) Some(resultlhm("putExpDateMap")) else None
    // combine the maps to date-based something or another (hopefully).
    val alldates = (calls.map(_.value.asInstanceOf[LinkedHashMap[String, ujson.Obj]]).getOrElse(new LinkedHashMap[String, ujson.Obj]).values.toSeq ++ puts.map(_.value.asInstanceOf[LinkedHashMap[String, ujson.Obj]]).getOrElse(new LinkedHashMap[String, ujson.Obj]).values.toSeq).toList
    // all dates is a list (::) containing 1) put and 2) call.  Now get a map of all the strikes.
    val allstrikes = alldates.flatMap(_.value.asInstanceOf[LinkedHashMap[String, ujson.Arr]].values)
    // I'm being very lazy here but I don't really use this one
    if (specific == None) {
      allstrikes.flatMap(_.value.map(_.value.asInstanceOf[LinkedHashMap[String, AnyVal]])).toString()
    } else {
      allstrikes.flatMap(_.value.map(_.value.asInstanceOf[LinkedHashMap[String, AnyVal]](specific.get))).toString()
    }
  }

}
