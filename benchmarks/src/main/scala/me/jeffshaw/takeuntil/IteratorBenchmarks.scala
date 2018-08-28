package me.jeffshaw.takeuntil

import java.util.concurrent.TimeUnit
import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole
import scala.collection.mutable.ArrayBuffer

// GC suffers if you put all the collections into one benchmark class
@State(Scope.Benchmark)
@Fork(value = 1, jvmArgsAppend = Array("-XX:+UseG1GC", "-Xmx12g"))
@Threads(1)
@Warmup(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
abstract class IteratorBenchmarks[M] {

  @Param(Array(
    "0",
    "100",
    "200",
    "300",
    "400",
    "500",
    "600",
    "700",
    "800",
    "900",
    "1000",
    "10000",
    "100000",
    "1000000",
    "10000000",
    "100000000",
  ))
  var valueCount: Int = _

  var values: M = _

  val random: util.Random = new util.Random(0)

  def getValues: M

  @Setup
  def setValues(): Unit = {
    values = getValues
  }

  // abstract because Arrays aren't Iterables
  def getIterator: Iterator[Int]

  @Benchmark
  def runWithoutBlackhole(): Unit = {
    val i = getIterator
    while (i.hasNext) {
      i.next()
    }
  }

  @Benchmark
  def run(blackhole: Blackhole): Unit = {
    val i = getIterator
    while (i.hasNext) {
      blackhole.consume(i.next())
    }
  }

}

class VectorIteratorBenchmarks extends IteratorBenchmarks[Vector[Int]] {
  override def getIterator: Iterator[Int] = values.iterator

  override def getValues(): Vector[Int] = {
    Vector.fill(valueCount)(random.nextInt())
  }
}

class ListIteratorBenchmarks extends IteratorBenchmarks[List[Int]] {
  override def getIterator: Iterator[Int] = values.iterator

  override def getValues(): List[Int] = {
    List.fill(valueCount)(random.nextInt())
  }
}

class ArrayBufferIteratorBenchmarks extends IteratorBenchmarks[ArrayBuffer[Int]] {
  override def getValues: ArrayBuffer[Int] = {
    ArrayBuffer.fill(valueCount)(random.nextInt())
  }

  override def getIterator: Iterator[Int] = values.iterator
}

class ArrayIteratorBenchmarks extends IteratorBenchmarks[Array[Int]] {
  override def getIterator: Iterator[Int] = values.iterator

  override def getValues(): Array[Int] = {
    Array.fill(valueCount)(random.nextInt())
  }
}

