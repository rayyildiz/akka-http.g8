package $package$.modules

import javax.inject.Inject

import akka.actor.{Actor, ActorSystem, IndirectActorProducer, _}
import akka.stream.ActorMaterializer
import com.google.inject._
import com.google.inject.name.Names
import com.typesafe.config.Config
import $package$.modules.AkkaModule._
import net.codingwell.scalaguice.ScalaModule

import scala.concurrent.ExecutionContext

object AkkaModule {

  class ActorSystemProvider @Inject()(val config: Config, val injector: Injector) extends Provider[ActorSystem] {
    override def get(): ActorSystem = {
      val system = ActorSystem("actor-system", config)
      GuiceAkkaExtension(system).initialize(injector)
      system
    }
  }

  class ActorMaterializerProvider @Inject()(val config: Config, val injector: Injector, val system: ActorSystem)
      extends Provider[ActorMaterializer] {
    override def get(): ActorMaterializer = {
      val materializer = ActorMaterializer()(system)
      materializer
    }
  }

  class ExecutionContextProvider @Inject()(val config: Config, val injector: Injector, val system: ActorSystem)
      extends Provider[ExecutionContext] {
    override def get(): ExecutionContext = {
      val executingContext = scala.concurrent.ExecutionContext.global
      executingContext
    }
  }

}

class AkkaModule extends AbstractModule with ScalaModule {
  override def configure() {
    bind[ActorSystem].toProvider[ActorSystemProvider].asEagerSingleton()
    bind[ActorMaterializer].toProvider[ActorMaterializerProvider].asEagerSingleton()
    bind[ExecutionContext].toProvider[ExecutionContextProvider].asEagerSingleton()
  }
}

class GuiceActorProducer(val injector: Injector, val actorName: String) extends IndirectActorProducer {

  override def actorClass: Class[Actor] = classOf[Actor]

  override def produce(): Actor =
    injector.getBinding(Key.get(classOf[Actor], Names.named(actorName))).getProvider.get()

}

class GuiceAkkaExtensionImpl extends Extension {

  private var injector: Injector = _

  def initialize(injector: Injector) {
    this.injector = injector
  }

  def props(actorName: String): Props = Props(classOf[GuiceActorProducer], injector, actorName)
}

// Akka
object GuiceAkkaExtension extends ExtensionId[GuiceAkkaExtensionImpl] with ExtensionIdProvider {
  override def lookup(): ExtensionId[GuiceAkkaExtensionImpl] = GuiceAkkaExtension

  override def createExtension(system: ExtendedActorSystem): GuiceAkkaExtensionImpl = new GuiceAkkaExtensionImpl

  override def get(system: ActorSystem): GuiceAkkaExtensionImpl = super.get(system)
}

trait NamedActor {
  def name: String
}

trait GuiceAkkaActorRefProvider {
  def provideActorRef(system: ActorSystem, name: String): ActorRef = system.actorOf(propsFor(system, name))

  def propsFor(system: ActorSystem, name: String): Props = GuiceAkkaExtension(system).props(name)
}
