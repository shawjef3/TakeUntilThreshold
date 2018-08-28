package me.jeffshaw.takeuntil

import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole
import scala.collection.mutable

@State(Scope.Benchmark)
class VectorBuilderBenchmarks {

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
  var length: Int = _

  var builder: mutable.Builder[Int, Vector[Int]] = _

  @Setup
  def setBuilder(blackhole: Blackhole): Unit = {
    builder = createBuilder(blackhole)
  }

  def createBuilder(blackhole: Blackhole): mutable.Builder[Int, Vector[Int]] = {
    val builder = Vector.newBuilder[Int]
    var i = 0
    while (i < length) {
      builder += 0
      i += 1
      // Calling this because [[baseline]] does also.
      blackhole.consume(i)
    }
    builder
  }

  @Benchmark
  @OperationsPerInvocation(100)
  def vector(blackhole: Blackhole): Unit = repeatedly(100, blackhole) {
    val builder = createBuilder(blackhole)
    builder.result()
  }

  @Benchmark
  @OperationsPerInvocation(100)
  def result(blackhole: Blackhole): Unit = repeatedly(100, blackhole) {
    builder.result()
  }

}
