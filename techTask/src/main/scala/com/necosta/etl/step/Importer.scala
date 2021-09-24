package com.necosta.etl.step

import java.nio.charset.CodingErrorAction

import org.apache.commons.compress.utils.CharsetNames

import scala.io.{Codec, Source}

class Importer extends Utils {

  private val tableSplitConditionStart = "CREATE TABLE"
  private val tableSplitConditionEnd = "GO"

  def getTablesDDLScript: List[String] = {
    val str = getUrlContent(s"$ddlFilePath/$ddlFileName")
    val tables = str.split(tableSplitConditionStart)
      .drop(1) // Drop text before first table
      .dropRight(1) // Drop last table
    tables.map(table => {
      val lastIndex = table.indexOf(tableSplitConditionEnd)
      tableSplitConditionStart + table.substring(0, lastIndex)
    }).toList
  }


  def getUrlContent(url: String): String = {
    val decoder = Codec.UTF8.decoder.onMalformedInput(CodingErrorAction.IGNORE)
    val bufferedSource = Source.fromURL(url)(decoder)
    val str = new String(bufferedSource.mkString.getBytes(), CharsetNames.UTF_8)
      .replaceAll("[^ -~]", "") // ToDo: review encoding madness hack
    bufferedSource.close()
    str
  }
}
