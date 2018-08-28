package me.jeffshaw.takeuntil

import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Benchmark)
class Benchmarks {

  def force(values: Seq[Int], blackhole: Blackhole): Unit = {
    blackhole.consume(
      values match {
        case stream: Stream[_] =>
          stream.force
        case otherwise =>
          otherwise
      }
    )
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
    "VectorRecursiveFunction",
    "StreamRecursiveFunction",
    "RunningTotals",
    "RunningTotalsNoInit",
    "RunningTotalsReversed",
    "RunningTotalsReversedNoInit",
    "RunningStateTotals",
  ))
  var implementationName: String = _

  var implementation: Implementation = _

  /**
    * Perform the string lookup for the implementation outside of the benchmark.
    */
  @Setup
  def setImplementation(): Unit = {
    implementation = Implementation(implementationName)
  }

  @Benchmark
  @OperationsPerInvocation(100)
  def run(blackhole: Blackhole): Unit = repeatedly(100, blackhole) {
//    val result = implementation(allValues, maxTotal)
    /*
    Force evaluation in case `result` is lazy. This is OK because
    none of the benchmarks give infinite collections due to the
    length of `values`.
     */
//    blackhole.consume(
//      result match {
//        case stream: Stream[_] =>
//          stream.force
//        case otherwise =>
//          otherwise
//      }
//    )
  }

}
