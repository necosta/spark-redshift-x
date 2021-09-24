package com.necosta.etl.step

import com.necosta.etl.WithSpark
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._

class Transformer extends Utils with WithSpark {

  def transform(df: DataFrame, schemaDDL: StructType): DataFrame = {
    val dfWithSchema = spark.createDataFrame(df.rdd, schemaDDL)

    val firstColumn = schemaDDL.head

    // Hacky way to remove BOM (Byte-Order-Mark from first row, first column
    dfWithSchema.withColumn(firstColumn.name,
      regexp_replace(col(firstColumn.name), "[^A-Z0-9_]", "")
        .cast(firstColumn.dataType))
  }
}
