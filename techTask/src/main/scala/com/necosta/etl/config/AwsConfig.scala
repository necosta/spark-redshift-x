package com.necosta.etl.config

import com.necosta.etl.WithSpark

class AwsConfig(runtimeConf: RuntimeConfig) extends WithSpark {

  def setAwsS3Creds: Unit = {
    sc.hadoopConfiguration.set("fs.s3a.access.key", runtimeConf.s3AccessKey())
    sc.hadoopConfiguration.set("fs.s3a.secret.key", runtimeConf.s3SecretKey())
  }
}
