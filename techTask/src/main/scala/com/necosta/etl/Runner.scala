package com.necosta.etl

import com.necosta.etl.config.{AwsConfig, RuntimeConfig}
import com.necosta.etl.step.{Parser, Reader, Transformer, Writer}
import org.slf4j.LoggerFactory

class Runner(runtimeConf: RuntimeConfig) {

  private val logger = LoggerFactory.getLogger(getClass.getName)

  private val tablesToImport = Seq(
    "DimAccount.csv",
    "DimCurrency.csv",
    "DimCustomer.csv",
    "DimDate.csv",
    "DimDepartmentGroup.csv",
    "DimEmployee.csv",
    "DimGeography.csv",
    "DimOrganization.csv",
    "DimProduct.csv",
    "DimProductCategory.csv",
    "DimProductSubcategory.csv",
    "DimPromotion.csv",
    "DimReseller.csv",
    "DimSalesReason.csv",
    "DimSalesTerritory.csv",
    "DimScenario.csv",
    "FactAdditionalInternationalProductDescription.csv",
    "FactCallCenter.csv",
    "FactCurrencyRate.csv",
    "FactFinance.csv",
    "FactInternetSales.csv",
    "FactInternetSalesReason.csv",
    "FactProductInventory.csv",
    "FactResellerSales.csv",
    "FactSalesQuota.csv",
    "FactSurveyResponse.csv"
  )

  def runPipeline(): Unit = {

    logger.info("Started running workflow...")

    new AwsConfig(runtimeConf).setAwsS3Creds

    val parser = new Parser()
    val reader = new Reader(runtimeConf)
    val transformer = new Transformer()
    val writer = new Writer(runtimeConf)

    // For each dimension/fact file...
    tablesToImport.foreach(fileName => {
      logger.info(s"Started importing $fileName")
      val fileNameWithoutExtension = getFileNameWithoutExtension(fileName)

      logger.info(s"Convert $fileName into dataframe")
      val df = reader.readFromCsv(fileName)

      logger.info(s"Dataframe $fileName has ${df.count()} rows.")

      val tablesDDLScript = parser.getTablesDDLScript
      val schemaDDL = parser.getSchemaDDL(fileNameWithoutExtension, tablesDDLScript)
      val transformedDf = transformer.transform(df, schemaDDL)
      writer.write(transformedDf, fileNameWithoutExtension)

      logger.info(s"Finished importing $fileName")

      // Visually check write was successful
      if(logger.isDebugEnabled()) {
        val df = reader.readFromRedshift(s"SELECT * FROM $fileName")
        df.show(10, false)
      }
    })

    logger.info("Finished running workflow")
  }

  def getFileNameWithoutExtension(fileName: String): String = {
    val indexOfDot = fileName.indexOf(".")
    if(indexOfDot > 0) fileName.substring(0, indexOfDot)
    else fileName
  }
}
