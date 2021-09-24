package com.necosta.etl.step

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ImporterSpec extends AnyFlatSpec with Matchers {

  it should "test ddl code parsing" in {
    val importer = new Importer()
    val tablesDDLScript = List(
      "CREATE TABLE [dbo].[DimAccount]([AccountKey] [int] IDENTITY(1,1) NOT NULL," +
        "[ParentAccountKey] [int] NULL) ON [PRIMARY];GO",
      "CREATE TABLE [dbo].[DimCurrency]([CurrencyKey] [int] IDENTITY(1,1) NOT NULL," +
        "[CurrencyAlternateKey] [nchar](3) NOT NULL,[CurrencyName] [nvarchar](50) NOT NULL) ON [PRIMARY];GO")
    val structType1 = importer.getSchemaDDL("DimAccount", tablesDDLScript)
    structType1.head.name should be("AccountKey")
    structType1.drop(1).head.name should be("ParentAccountKey")

    val structType2 = importer.getSchemaDDL("DimCurrency", tablesDDLScript)
    structType2.head.name should be("CurrencyKey")
    structType2.drop(1).head.name should be("CurrencyAlternateKey")
  }
}
