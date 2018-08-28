//package me.jeffshaw.takeuntil
//
//import java.util.concurrent.TimeUnit
//import org.openjdk.jmh.annotations._
//import org.openjdk.jmh.infra.Blackhole
//import scala.collection.mutable.ArrayBuffer
//
//@State(Scope.Benchmark)
//@Fork(value = 1, jvmArgsAppend = Array("-XX:+UseG1GC", "-Xmx10g"))
//@Threads(1)
//@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
//@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
//class TakeWhileBenchmarks {
//
//  @Param(Array(
//    "0",
//    "100",
//    "200",
//    "300",
//    "400",
//    "500",
//    "600",
//    "700",
//    "800",
//    "900",
//    "1000",
//  ))
//  var valueCount: Int = _
//
//  var vector: scala.collection.immutable.Vector[Int] = _
//
//  var list: scala.collection.immutable.List[Int] = _
//
//  var array: Array[Int] = _
//
//  var mutableArraySeq: scala.collection.mutable.WrappedArray.ofInt = _
//
//  var arrayBuffer: scala.collection.mutable.ArrayBuffer[Int] = _
//
//  @Setup
//  @OperationsPerInvocation(100)
//  def setBenchmarkValues(): Unit = {
//    val values: Array[Int] = allValues.take(valueCount)
//
//    vector = values.toVector
//    list = values.toList
//    array = values
//    mutableArraySeq = values.toSeq.asInstanceOf[scala.collection.mutable.WrappedArray.ofInt]
//    arrayBuffer = ArrayBuffer(values: _*)
//  }
//
//  @Benchmark
//  @OperationsPerInvocation(100)
//  def array(blackhole: Blackhole): Unit = repeatedly(100, blackhole) {
//    array.takeWhile(_ => true)
//  }
//
//  @Benchmark
//  @OperationsPerInvocation(100)
//  def vector(blackhole: Blackhole): Unit = repeatedly(100, blackhole) {
//    vector.takeWhile(_ => true)
//  }
//
//  @Benchmark
//  @OperationsPerInvocation(100)
//  def list(blackhole: Blackhole): Unit = repeatedly(100, blackhole) {
//    list.takeWhile(_ => true)
//  }
//
//  @Benchmark
//  @OperationsPerInvocation(100)
//  def mutableArraySeq(blackhole: Blackhole): Unit = repeatedly(100, blackhole) {
//    mutableArraySeq.takeWhile(_ => true)
//  }
//
//  @Benchmark
//  @OperationsPerInvocation(100)
//  def arrayBuffer(blackhole: Blackhole): Unit = repeatedly(100, blackhole) {
//    arrayBuffer.takeWhile(_ => true)
//  }
//
//}
