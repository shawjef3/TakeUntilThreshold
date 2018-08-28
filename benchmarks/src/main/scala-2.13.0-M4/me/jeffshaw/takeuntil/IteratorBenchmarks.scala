package me.jeffshaw.takeuntil

class ImmutableArraySeqIteratorBenchmarks extends IteratorBenchmarks[scala.collection.immutable.ArraySeq.ofInt] {
  override def getValues: scala.collection.immutable.ArraySeq.ofInt = {
    scala.collection.immutable.ArraySeq.fill(valueCount)(random.nextInt()).
      asInstanceOf[scala.collection.immutable.ArraySeq.ofInt]
  }

  override def getIterator: Iterator[Int] = values.iterator
}

class MutableArraySeqIteratorBenchmarks extends IteratorBenchmarks[scala.collection.mutable.ArraySeq.ofInt] {
  override def getIterator: Iterator[Int] = values.iterator

  override def getValues(): scala.collection.mutable.ArraySeq.ofInt = {
    scala.collection.mutable.ArraySeq.fill(valueCount)(random.nextInt()).
      asInstanceOf[scala.collection.mutable.ArraySeq.ofInt]
  }
}
