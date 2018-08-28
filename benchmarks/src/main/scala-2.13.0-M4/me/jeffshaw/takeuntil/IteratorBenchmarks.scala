package me.jeffshaw.takeuntil

import java.util.concurrent.TimeUnit
import org.openjdk.jmh.annotations._
import scala.collection.mutable.ArrayBuffer

// GC suffers if you put all the collections into one benchmark class
@State(Scope.Benchmark)
@Fork(value = 1, jvmArgsAppend = Array("-XX:+UseG1GC", "-Xmx12g"))
@Threads(1)
@Warmup(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
abstract class AbstractIteratorBenchmarks[M] {

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
  def run(): Unit = {
    val i = getIterator
    while (i.hasNext) {
      i.next()
    }
  }

}

class ImmutableArraySeqIteratorBenchmarks extends AbstractIteratorBenchmarks[scala.collection.immutable.ArraySeq.ofInt] {
  override def getValues: scala.collection.immutable.ArraySeq.ofInt = {
    scala.collection.immutable.ArraySeq.fill(valueCount)(random.nextInt()).
      asInstanceOf[scala.collection.immutable.ArraySeq.ofInt]
  }

  override def getIterator: Iterator[Int] = values.iterator
}

class VectorIteratorBenchmarks extends AbstractIteratorBenchmarks[Vector[Int]] {
  override def getIterator: Iterator[Int] = values.iterator

  override def getValues(): Vector[Int] = {
    Vector.fill(valueCount)(random.nextInt())
  }
}

class ListIteratorBenchmarks extends AbstractIteratorBenchmarks[List[Int]] {
  override def getIterator: Iterator[Int] = values.iterator

  override def getValues(): List[Int] = {
    List.fill(valueCount)(random.nextInt())
  }
}

class MutableArraySeqIteratorBenchmarks extends AbstractIteratorBenchmarks[scala.collection.mutable.ArraySeq.ofInt] {
  override def getIterator: Iterator[Int] = values.iterator

  override def getValues(): scala.collection.mutable.ArraySeq.ofInt = {
    scala.collection.mutable.ArraySeq.fill(valueCount)(random.nextInt()).
      asInstanceOf[scala.collection.mutable.ArraySeq.ofInt]
  }
}

class ArrayBufferIteratorBenchmarks extends AbstractIteratorBenchmarks[ArrayBuffer[Int]] {
  override def getValues: ArrayBuffer[Int] = {
    ArrayBuffer.fill(valueCount)(random.nextInt())
  }

  override def getIterator: Iterator[Int] = values.iterator
}

class ArrayIteratorBenchmarks extends AbstractIteratorBenchmarks[Array[Int]] {
  override def getIterator: Iterator[Int] = values.iterator

  override def getValues(): Array[Int] = {
    Array.fill(valueCount)(random.nextInt())
  }
}
