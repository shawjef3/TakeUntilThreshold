package me.jeffshaw.takeuntil

import org.openjdk.jmh.annotations._

@State(Scope.Benchmark)
class Benchmarks {

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
    "Var",
    "Fold",
    "FoldReturn",
    "RecursiveFunction",
    "RunningTotals",
    "RunningTotalsNoInit",
    "RunningTotalsReversed",
    "RunningTotalsReversedNoInit",
    "RunningStateTotals",
  ))
  var implementationName: String = _

  var implementation: Implementation = _

  /**
    * Don't perform the string lookup for the implementation name as part of the benchmark.
    */
  @Setup
  def setImplementation(): Unit = {
    implementation = Implementation(implementationName)
  }

  @Benchmark
  @OperationsPerInvocation(100)
  def run(): Unit = {
    var i = 0
    while (i < 100) {
      implementation(values, maxTotal)
      i += 1
    }
  }

}
