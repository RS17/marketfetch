package com.rs17.marketfetch

import scala.sys.process._

class AuthorizerTDA extends AuthorizerBase {
  val apiName = "TDA"

  override def getToken: Option[String] = {
    val refresh = scala.io.Source.fromFile("/home/ravi/tdameritrade.key").mkString.trim()
    val client = scala.io.Source.fromFile("/home/ravi/client_id.txt").mkString.trim()

    val cmd = Seq("curl",
      "-X",
      "POST",
      "--header",
      "Content-Type: application/x-www-form-urlencoded",
      "-d",
      "grant_type=refresh_token&refresh_token=" + refresh + "&access_type=&code=&client_id=" + client + "%40AMER.OAUTHAP&redirect_uri=http%3A%2F%2Flocalhost",
      "https://api.tdameritrade.com/v1/oauth2/token")

    // invalid grant here probably means either 1) the refresh token is expired or 2) the refresh token has some extra characters at the end
    val auth_response = cmd.!!

    // get the token from the response
    val token_start = auth_response.indexOf("access_token") + 17
    Some("Bearer " + auth_response.substring(token_start, auth_response.indexOf('"', token_start)))
  }
}
