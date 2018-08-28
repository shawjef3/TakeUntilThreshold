//package me.jeffshaw.takeuntil
//
//import java.util.concurrent.TimeUnit
//import org.openjdk.jmh.annotations._
//import org.openjdk.jmh.infra.Blackhole
//
//@State(Scope.Benchmark)
//@Fork(value = 1, jvmArgsAppend = Array("-XX:+UseG1GC", "-Xmx10g"))
//@Threads(1)
//@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
//@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
//class ImmutableTakeBenchmarks {
//
//  @Param(Array(
//    "0",
//    "10000",
//    "20000",
//    "30000",
//    "40000",
//    "50000",
//    "60000",
//    "70000",
//    "80000",
//    "90000",
//    "100000",
//  ))
//  var valueCount: Int = _
//
//  @Param(Array("wrapped array", "list", "vector"))
//  var collectionName: String = _
//
//  var benchmarkValues: Seq[Int] = _
//
//  @Setup
//  def setBenchmarkValues(): Unit = {
//    benchmarkValues =
//      collectionName match {
//        case "wrapped array" => takeAsWrappedArray
//        case "list" => takeAsList
//        case "vector" => takeAsVector
//      }
//  }
//
//  @Benchmark
//  @OperationsPerInvocation(100)
//  def take(blackhole: Blackhole): Unit = repeatedly(100, blackhole) {
//    benchmarkValues.take(valueCount)
//  }
//
//}
//
