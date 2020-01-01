package com.rs17.marketfetch

class RefreshTokenTDA extends StuffHandler with APITDA {
  // gets refresh token from code and saves
  override def handleArgs(args: Array[String]): Array[Any] = {
    val code = args(1)
    val retarr = Array(code)
    retarr.asInstanceOf[Array[Any]]
  }

  def process(args: Array[Any]): Option[Any] = {
    val code: String = args(0).toString
    authorizerTDA.getRefreshToken(code)
    return Some("finished")
  }
}
