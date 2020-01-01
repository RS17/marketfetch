package com.rs17.marketfetch

// Configuration object.  I prefer to hard code when possible for easy debugging and flexibility. Fight me.
object MFProperties {
  val refreshTokenFile: String = System.getProperty("user.home") + "/tdameritrade.key"
  val appName: String = "RS17MF"
  val repeaterOut: String = System.getProperty("user.home") + "/out.txt"
  val repeaterSleepMilli: Int = 501
  val repeaterRefreshMin: Int = 1
  val commandList = Map("repeater" -> new Repeater, "option" -> new OptionChainTDA, "refreshtoken" -> new RefreshTokenTDA)
}
