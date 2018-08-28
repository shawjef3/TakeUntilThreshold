package me.jeffshaw.takeuntil

import scala.collection.mutable.ArrayBuffer

class MutableArraySeqIteratorBenchmarks extends IteratorBenchmarks[scala.collection.mutable.WrappedArray.ofInt] {
  override def getIterator: Iterator[Int] = values.iterator

  override def getValues(): scala.collection.mutable.WrappedArray.ofInt = {
    Array.fill(valueCount)(random.nextInt()).toSeq.asInstanceOf[scala.collection.mutable.WrappedArray.ofInt]
  }
}
