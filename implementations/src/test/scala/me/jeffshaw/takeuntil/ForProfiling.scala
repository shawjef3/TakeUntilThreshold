package me.jeffshaw.takeuntil

import org.scalatest.FunSuite

class ForProfiling extends FunSuite {

  test("vector") {
    val collection = Vector.fill(100000)(1)
    assertResult(collection)(VectorRecursiveFunction(collection, 100000))
  }

  test("stream") {
    val collection = Stream.continually(1)
    val result: Stream[_] = StreamRecursiveFunction(collection, 100000).asInstanceOf[Stream[_]].force
    assertResult(collection.take(100000))(result)
  }

}
