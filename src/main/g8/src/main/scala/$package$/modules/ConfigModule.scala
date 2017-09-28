package $package$.modules

import com.google.inject.{AbstractModule, Provider}
import com.rayyildiz.sentiment_analyzer.modules.ConfigModule.ConfigProvider
import com.typesafe.config.{Config, ConfigFactory}
import net.codingwell.scalaguice.ScalaModule

object ConfigModule {

  class ConfigProvider extends Provider[Config] {
    override def get(): Config = ConfigFactory.load()
  }

}

class ConfigModule extends AbstractModule with ScalaModule {

  override def configure() {
    bind[Config].toProvider[ConfigProvider].asEagerSingleton()
  }

}
