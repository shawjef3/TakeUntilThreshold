package me.jeffshaw.takeuntil

import java.util.concurrent.TimeUnit
import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Benchmark)
@Fork(value = 1, jvmArgsAppend = Array("-XX:+UseG1GC", "-Xmx10g"))
@Threads(1)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
class VarBenchmarks {

  val values: Seq[Int] = {
    val deterministicRandom = new util.Random(0)
    Array.fill(1000000)(deterministicRandom.nextInt(10))
  }

  @Param(Array(
    "0",
    "10000",
    "20000",
    "30000",
    "40000",
    "50000",
    "60000",
    "70000",
    "80000",
    "90000",
    "100000",
  ))
  var maxTotal: Int = _

  @Param(Array(
    "Array",
    "List",
    "Vector"
  ))
  var collectionName: String = _

  var collection: Seq[Int] = _

  @Setup
  def setCollection(): Unit = {
    collectionName match {
      case "Array" =>
        collection = values
      case "List" =>
        collection = values.toList
      case "Vector" =>
        collection = values.toVector
    }
  }

  @Benchmark
  @OperationsPerInvocation(100)
  def run(blackhole: Blackhole): Unit = repeatedly(100, blackhole) {
    Var(collection, maxTotal)
  }

}
