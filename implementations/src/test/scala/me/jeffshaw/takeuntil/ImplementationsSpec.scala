package me.jeffshaw.takeuntil

import org.scalatest.FunSuite

class ImplementationsSpec extends FunSuite {

  val implementations: Seq[(Seq[Int], Int) => Seq[Int]] =
    Seq(
      Var,
      Fold,
      FoldReturn,
      RecursiveFunction,
      RunningTotals,
      RunningTotalsNoInit,
      RunningTotalsReversed,
      RunningTotalsReversedNoInit,
      RunningStateTotals,
    )

  case class TestParameters(
    values: Seq[Int],
    threshold: Int,
    expected: Seq[Int]
  ) {
    def register(): Unit = {
      for (implementation <- implementations) {
        test(s"assertResult($expected)($implementation($values, $threshold))") {
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

  (seqTests ++ indexedSeqTests).foreach(_.register())

}
