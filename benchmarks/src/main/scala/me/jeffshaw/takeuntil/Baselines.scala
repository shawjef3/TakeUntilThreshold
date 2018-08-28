package me.jeffshaw.takeuntil

import java.util.concurrent.TimeUnit
import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Benchmark)
@Fork(value = 1, jvmArgsAppend = Array("-XX:+UseG1GC"))
@Threads(1)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
class Baselines {

  @Benchmark
  @OperationsPerInvocation(100)
  def repeatedlyBaseline(blackhole: Blackhole): Unit = repeatedly(100, blackhole)(())

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

  @Benchmark
  @OperationsPerInvocation(100)
  def forceVectorBaseline(blackhole: Blackhole): Unit = repeatedly(100, blackhole) {
    force(Vector.empty[Int], blackhole)
  }

  @Benchmark
  @OperationsPerInvocation(100)
  def forceStreamBaseline(blackhole: Blackhole): Unit = repeatedly(100, blackhole) {
    force(Stream.empty[Int], blackhole)
  }

}
