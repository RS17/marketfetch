package com.rs17.marketfetch

import java.io.{BufferedWriter, File, FileWriter}
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

import scala.sys.process._

class AuthorizerTDA extends AuthorizerBase {
  val apiName = "TDA"

  override def getToken: Option[String] = {
    // getting refresh token the first time: follow steps in this guide (not by me): https://www.reddit.com/r/algotrading/comments/c81vzq/td_ameritrade_api_access_2019_guide/ to get "code" (thing that you copy from URL after entering account details)
    // then use this page (https://developer.tdameritrade.com/authentication/apis/post/token-0) to get a refresh token
    // then paste the refresh token (as is - no decoding) in refresh token file
    // To summarize: I think there's 3 kinds of codes:
    //    1 - "code" from url - can use this only once to get authorization code and/or refresh token.  This is NOT an authorization code.
    //    2 - refresh token - you can use this for 90 days to get an authorization code
    //    3 - authorization code - thing you use to actually get data and do stuff
    // unfortunately I don't think there's any good way to use length, format, etc to know which is which and catch errors
    val refresh = scala.io.Source.fromFile(MFProperties.refreshTokenFile).mkString.trim()

    val client = MFProperties.appName

    val auth_response = doCurl("grant_type=refresh_token&refresh_token=" + refresh + "&access_type=&code=&client_id=" + client + "%40AMER.OAUTHAP&redirect_uri=")

    // invalid grant here probably means either 1) the refresh token is expired or 2) the refresh token has some extra characters at the end
    if (auth_response.contains("invalid_grant")) {
      throw new Exception("Invalid grant error.  Check that refresh token is correct and has not expired.  " + auth_response)
    }

    // get the token from the response
    val access_start = auth_response.indexOf("access_token") + 17
    val access_token = auth_response.substring(access_start, auth_response.indexOf('"', access_start))

    Some("Bearer " + access_token)
  }

  def getRefreshToken(code: String): Unit = {
    // gets refresh token from url-encoded code, saves in file
    val client = MFProperties.appName
    val auth_response: String = doCurl("grant_type=authorization_code&access_type=offline&code=" + code + "&client_id=" + client + "%40AMER.OAUTHAP&redirect_uri=http%3A%2F%2Flocalhost")

    val refresh_start = auth_response.indexOf("refresh_token") + 18
    val refresh_token_unencoded = auth_response.substring(refresh_start, auth_response.indexOf('"', refresh_start))

    val bw = new BufferedWriter(new FileWriter(new File(MFProperties.refreshTokenFile)))
    bw.write(URLEncoder.encode(refresh_token_unencoded, StandardCharsets.UTF_8.toString))
    bw.close()
  }

  def doCurl(cmdString: String): String = {
    val cmd = Seq("curl",
      "-X",
      "POST",
      "--header",
      "Content-Type: application/x-www-form-urlencoded",
      "-d",
      cmdString,
      "https://api.tdameritrade.com/v1/oauth2/token")

    // invalid grant here probably means either 1) the refresh token is expired or 2) the refresh token has some extra characters at the end
    cmd.!!
  }
}
