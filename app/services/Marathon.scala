package services

import javax.inject.Inject

import com.google.inject.{AbstractModule, Guice, Injector}

trait Marathon {
  def value: String
}

class TokyoMarathon() extends Marathon {
  override val value: String = "Tokyo"
}

class TokyoMarathonModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[Marathon]).to(classOf[TokyoMarathon])
  }
}

class HonoluluMarathon() extends Marathon {
  override val value: String = "Hawai"
}

class HonoluluMarathonModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[Marathon]).to(classOf[HonoluluMarathon])
  }
}

trait MarathonRace {
  def venue(): Unit
}

// MarathonRaceImplは、Marathonに依存することをJSR330のアノテーションで表現
class MarathonRaceImpl @Inject()(marathon: Marathon) extends MarathonRace {
  override def venue(): Unit = println(marathon.value)
}

class MarathonRaceModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[MarathonRace]).to(classOf[MarathonRaceImpl])
  }
}

object Main {
  def main(args: Array[String]): Unit = {

    val injector: Injector = Guice.createInjector(new TokyoMarathonModule, new MarathonRaceModule)
    val marathonRace: MarathonRace = injector.getInstance(classOf[MarathonRace])
    marathonRace.venue()

    val injector2: Injector = Guice.createInjector(new HonoluluMarathonModule, new MarathonRaceModule)
    val marathonRace2: MarathonRace = injector2.getInstance(classOf[MarathonRace])
    marathonRace2.venue()
  }
}