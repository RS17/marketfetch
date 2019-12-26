package com.rs17.marketfetch


abstract class AuthorizerBase {
  def getToken: Option[String] = {
    None
  }
}
