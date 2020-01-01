package com.rs17.marketfetch

import java.io._

import scala.io.Source

class Repeater extends StuffHandler {
  override def handleArgs(args: Array[String]): Array[Any] = {
    val retarr = args.drop(1) // drop "repeater" argument
    retarr.asInstanceOf[Array[Any]]
  }

  override def process(args: Array[Any]): Option[Any] = {
    val source = Source.fromFile(args(0).toString())
    val handler = StuffHandler.getHandler(args(1).toString())
    // need var newargs to get access token.  Symbol is placeholder.
    var newArgs = handler.handleArgs(args.asInstanceOf[Array[String]])
    // call specified handler new args for each line in file
    val writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(MFProperties.repeaterOut)))
    var starttime = java.time.LocalTime.now

    var fullout = "";
    for (line <- source.getLines()) {
      Thread.sleep(MFProperties.repeaterSleepMilli)
      val out = handler.process(line +: newArgs.drop(1)).get.toString
      if (out != "") {
        fullout += out
      }
      println("processed " + line)
      if (java.time.LocalTime.now.minus(java.time.Duration.ofMinutes(MFProperties.repeaterRefreshMin)).compareTo(starttime) > 0) {
        // get new access token
        newArgs = handler.handleArgs(args.asInstanceOf[Array[String]])
        starttime = java.time.LocalTime.now
      }
    }
    writer.write(fullout)
    source.close()
    None
  }
}
