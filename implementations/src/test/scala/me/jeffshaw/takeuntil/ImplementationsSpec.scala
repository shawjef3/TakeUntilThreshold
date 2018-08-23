package me.jeffshaw.takeuntil

import org.scalatest.FunSuite

class ImplementationsSpec extends FunSuite {

  val implementations: Seq[(Seq[Int], Int) => Seq[Int]] =
    Seq(
      Var,
      Fold,
      FoldReturn,
      RunningTotals,
      RunningStateTotals
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

    override def toString: String = {
      s"Test(values = $values, threshold = $threshold, expected = $expected"
    }
  }

  val tests: Seq[TestParameters] =
    Seq(
      TestParameters(Seq(1,2,3), 0, Seq()),
      TestParameters(Seq(0,1,2,3), 0, Seq(0)),
      TestParameters(0 to 100, 100, 0 to 13),
      TestParameters((0 to 100).reverse, 100, Seq(100)),
      TestParameters(List.fill(100)(0), 0, List.fill(100)(0)),
      TestParameters(List(3,2,1), 3, Seq(3))
    )

  tests.foreach(_.register())

}
