package com.necosta.etl.step

import java.nio.charset.CodingErrorAction

import org.apache.commons.compress.utils.CharsetNames
import org.apache.spark.sql.types.StructType
import org.slf4j.LoggerFactory

import scala.io.{Codec, Source}

class Importer extends Utils {

  private val logger = LoggerFactory.getLogger(getClass.getName)

  private val tableSplitConditionStart = "CREATE TABLE"
  private val tableSplitConditionEnd = "GO"

  def getSchemaDDL(tableName: String, tablesDDLScript: List[String]): StructType = {
    val allTablesCode = tablesDDLScript.find(t => t.contains(tableName))
    allTablesCode match {
      case Some(t) => parseDDLCode(t)
      case None => throw new Exception(s"DDL script not found for table $tableName")
    }
  }

  def parseDDLCode(ddlCode: String) : StructType = {
    val columnsStartIndex = ddlCode.indexOf("(")
    val columnsEndIndex = ddlCode.lastIndexOf(")")
    val ddlColumnsStr = ddlCode.substring(columnsStartIndex + 1, columnsEndIndex)
      .replace("[", "")
      .replace("]", "")
      .replace("IDENTITY(1,1)", "") // ToDo: Review this logic
    val ddlColumns = ddlColumnsStr.split(",").map(columnNameStr => {
      val columnNameIndex = columnNameStr.indexOf(" ")
      val columnName = columnNameStr.substring(0, columnNameIndex)
      s"$columnName STRING" // default all columns to string type
    }).mkString(",")
    val structType = StructType.fromDDL(ddlColumns)
    if(logger.isDebugEnabled) structType.printTreeString()
    structType
  }

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


  private def getUrlContent(url: String): String = {
    val decoder = Codec.UTF8.decoder.onMalformedInput(CodingErrorAction.IGNORE)
    val bufferedSource = Source.fromURL(url)(decoder)
    val str = new String(bufferedSource.mkString.getBytes(), CharsetNames.UTF_8)
      .replaceAll("[^ -~]", "") // ToDo: review encoding madness hack
    bufferedSource.close()
    str
  }
}
