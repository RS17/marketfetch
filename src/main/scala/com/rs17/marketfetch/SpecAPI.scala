package com.rs17.marketfetch

// this doesn't do much yet, it's really here as a reminder that I can use this to keep common vars for a specific API
trait APIBase extends StuffHandler {
  val authorizer: Option[AuthorizerBase] = None
}

trait APITDA extends APIBase {
  override val authorizer = Some(new AuthorizerTDA)
  val authorizerTDA: AuthorizerTDA = authorizer.get

  // calls parent (usually a [Command]Base class) and adds authorization to the end
  override def handleArgs(args: Array[String]): Array[Any] = super.handleArgs(args) :+ authorizer.get.getToken
}