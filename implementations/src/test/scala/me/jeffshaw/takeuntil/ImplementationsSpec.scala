package me.jeffshaw.takeuntil

import org.scalatest.FunSuite

class ImplementationsSpec extends FunSuite {

  val implementations: Seq[(Seq[Int], Int) => Seq[Int]] =
    Seq(
      Var,
      Fold,
      FoldReturn,
      StreamRecursiveFunction,
      VectorRecursiveFunction,
      RunningTotals,
      RunningTotalsNoInit,
      RunningTotalsReversed,
      RunningTotalsReversedNoInit,
      RunningStateTotals,
    )

  case class TestParameters(
    values: Seq[Int],
    threshold: Int,
    expected: Seq[Int],
    maybeOverrideName: Option[String] = None,
    implementations: Seq[(Seq[Int], Int) => Seq[Int]] = implementations
  ) {
    def register(): Unit = {
      for (implementation <- implementations) {
        val testName = {
          for (overrideName <- maybeOverrideName) yield {
            s"$implementation $overrideName"
          }
        }.getOrElse(s"assertResult($expected)($implementation($values, $threshold))")
        test(testName) {
          assertResult(expected)(implementation(values, threshold))
        }
      }
    }
  }

  val seqTests: Seq[TestParameters] =
    Seq(
      TestParameters(List(), 0, List()),
      TestParameters(List(), 1, List()),
      TestParameters(List(1,1,1), 2, List(1,1)),
      TestParameters(List(1,0,1), 2, List(1,0,1)),
      TestParameters(List(1,1,0), 2, List(1,1,0)),
      TestParameters(List(1,2,3), 0, List()),
      TestParameters(List(0,1,2,3), 0, List(0)),
      TestParameters(List.fill(100)(0), 0, List.fill(100)(0)),
      TestParameters(List(3,2,1), 3, List(3)),
      TestParameters(List(1,3), 1, List(1)),
    )

  val indexedSeqTests: Seq[TestParameters] =
    Seq(
      TestParameters(Vector(), 0, Vector()),
      TestParameters(Vector(), 1, Vector()),
      TestParameters(Vector(1,1,1), 2, Vector(1,1)),
      TestParameters(Vector(1,0,1), 2, Vector(1,0,1)),
      TestParameters(Vector(1,1,0), 2, Vector(1,1,0)),
      TestParameters(Vector(1,2,3), 0, Vector()),
      TestParameters(Vector(0,1,2,3), 0, Vector(0)),
      TestParameters(0 to 100, 100, 0 to 13),
      TestParameters((0 to 100).reverse, 100, Vector(100)),
      TestParameters(Vector.fill(100)(0), 0, Vector.fill(100)(0)),
      TestParameters(Vector(3,2,1), 3, Vector(3)),
      TestParameters(Vector(1,3), 1, Vector(1)),
    )

  val streamTests: Seq[TestParameters] =
    Seq(
      TestParameters(Stream(), 0, Stream()),
      TestParameters(Stream(), 1, Stream()),
      TestParameters(Stream(1,1,1), 2, Stream(1,1)),
      TestParameters(Stream(1,0,1), 2, Stream(1,0,1)),
      TestParameters(Stream(1,1,0), 2, Stream(1,1,0)),
      TestParameters(Stream(1,2,3), 0, Stream()),
      TestParameters(Stream(0,1,2,3), 0, Stream(0)),
      TestParameters(0 to 100, 100, 0 to 13),
      TestParameters((0 to 100).reverse, 100, Stream(100)),
      TestParameters(Stream.fill(100)(0), 0, Stream.fill(100)(0)),
      TestParameters(Stream(3,2,1), 3, Stream(3)),
      TestParameters(Stream(1,3), 1, Stream(1)),
      /*
      `Stream.continually(1).fold` will never terminate, so don't test that implementation.
       */
      TestParameters(
        values = Stream.continually(1),
        threshold = 1000,
        expected = Stream.fill(1000)(1),
        implementations = implementations.filter(_ != Fold))
    ).zipWithIndex.map {
      case (test, ix) =>
        /*
        Stream test names will collide because of laziness, and I
        don't want to force the Stream until the test runs, and I'm
        lazy (am I a stream?), so the names aren't very descriptive.
         */
        test.copy(maybeOverrideName = Some(s"Stream test $ix"))
    }

  (seqTests ++ indexedSeqTests ++ streamTests).foreach(_.register())

}
