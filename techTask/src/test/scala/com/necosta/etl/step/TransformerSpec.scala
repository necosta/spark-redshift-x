package com.necosta.etl.step

import com.necosta.etl.WithSparkSession
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TransformerSpec extends AnyFlatSpec with Matchers with WithSparkSession {

  it should "test dataframe transformations" in withSpark { spark =>
    import spark.implicits._

    val transformer = new Transformer()

    val schema = StructType(Array(
      StructField("id", IntegerType),
      StructField("name", StringType)
    ))
    val df = spark.sparkContext.parallelize(Seq((1, "word1"), (2, "word2")))
      .toDF("number", "word")

    val dfWithSchema = transformer.transform(df, schema)

    dfWithSchema.schema.head.name should be("id")
    dfWithSchema.schema.head.dataType should be(IntegerType)
    dfWithSchema.schema.drop(1).head.dataType should be(StringType)
  }
}


