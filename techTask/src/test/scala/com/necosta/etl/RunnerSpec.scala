package com.necosta.etl

import com.necosta.etl.config.RuntimeConfig
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RunnerSpec extends AnyFlatSpec with Matchers {

  it should "test file name parsing" in {
    val runner = new Runner(RuntimeConfig.buildMock())
    runner.getFileNameWithoutExtension("file.csv") should be("file")
    runner.getFileNameWithoutExtension("file") should be("file")
  }
}
