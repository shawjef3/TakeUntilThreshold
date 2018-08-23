package me.jeffshaw.takeuntil

import scala.annotation.tailrec
import scala.collection.mutable

trait Implementation extends ((Seq[Int], Int) => Seq[Int]) {
  val Name: String

  override def toString(): String = {
    Name
  }
}

object Implementation extends (String => Implementation) {
  override def apply(v1: String): Implementation = {
    v1 match {
      case Var.Name => Var
      case Fold.Name => Fold
      case FoldReturn.Name => FoldReturn
      case RecursiveFunction.Name => RecursiveFunction
      case RunningTotals.Name => RunningTotals
      case RunningTotalsNoInit.Name => RunningTotalsNoInit
      case RunningTotalsReversed.Name => RunningTotalsReversed
      case RunningTotalsReversedNoInit.Name => RunningTotalsReversedNoInit
      case RunningStateTotals.Name => RunningStateTotals
    }
  }
}

object Var extends Implementation {
  override def apply(values: Seq[Int], threshold: Int): Seq[Int] = {
    var total = 0
    values.takeWhile { value =>
      total += value
      total <= threshold
    }
  }

  override val Name: String = "Var"
}

object Fold extends Implementation {
  def apply(values: Seq[Int], threshold: Int): Seq[Int] = {
    val (total, result) =
    //Vector for efficient append
      values.foldLeft((0, Vector.empty[Int])) {
        case ((total, result), value) =>
          val newTotal = total + value
          if (newTotal <= threshold) {
            (newTotal, result :+ value)
          } else (newTotal, result)
      }
    result
  }

  override val Name: String = "Fold"
}

object FoldReturn extends Implementation {
  override def apply(values: Seq[Int], threshold: Int): Seq[Int] = {
    val (total, result) =
    //Vector for efficient append.
      values.foldLeft((0, Vector.empty[Int])) {
        case ((total, result), value) =>
          val newTotal = total + value
          if (newTotal <= threshold) {
            (newTotal, result :+ value)
          } else return result
      }
    result
  }

  override val Name: String = "FoldReturn"
}

object RecursiveFunction extends Implementation {
  override val Name: String = "RecursiveFunction"

  override def apply(values: Seq[Int], threshold: Int): Seq[Int] = {

    // Use for when indexed lookup is O(1)
    @tailrec
    def indexed(accum: mutable.Buffer[Int], sum: Int, ix: Int): Seq[Int] = {
      if (ix < values.length) {
        val nextSum = sum + values(ix)
        if (nextSum <= threshold) {
          indexed(accum :+ values(ix), nextSum, ix + 1)
        } else accum
      } else accum
    }

    // Use for when indexed lookup is O(n)
    @tailrec
    def otherwise(accum: mutable.Buffer[Int], sum: Int, rest: Seq[Int]): Seq[Int] = {
      if (rest.nonEmpty) {
        val nextSum = sum + rest.head
        if (nextSum <= threshold) {
          otherwise(accum :+ rest.head, nextSum, rest.tail)
        } else accum
      } else accum
    }

    values match {
      case ixed: IndexedSeq[Int] =>
        indexed(mutable.Buffer.empty[Int], 0, 0)
      case _ =>
        otherwise(mutable.Buffer.empty[Int], 0, values)
    }
  }
}

object RunningTotals extends Implementation {
  def apply(values: Seq[Int], threshold: Int): Seq[Int] = {
    RunningTotal.ofSeq(values).
      takeWhile(_.total <= threshold).
      map(_.value)
  }

  override val Name: String = "RunningTotals"
}

object RunningTotalsReversed extends Implementation {
  def apply(values: Seq[Int], threshold: Int): Seq[Int] = {
    RunningTotalReversed.ofSeq(values).
      takeWhile(_.total <= threshold).
      map(_.value)
  }

  override val Name: String = "RunningTotalsReversed"
}

object RunningTotalsNoInit extends Implementation {
  override def apply(values: Seq[Int], threshold: Int): Seq[Int] = {
    RunningTotalNoInit.ofSeq(values).
      takeWhile(_.total <= threshold).
      map(_.value)
  }

  override val Name: String = "RunningTotalsNoInit"
}

object RunningTotalsReversedNoInit extends Implementation {
  override def apply(values: Seq[Int], threshold: Int): Seq[Int] = {
    RunningTotalReversedNoInit.ofSeq(values).
      takeWhile(_.total <= threshold).
      map(_.value)
  }

  override val Name: String = "RunningTotalsReversedNoInit"
}

object RunningStateTotals extends Implementation {
  override def apply(values: Seq[Int], threshold: Int): Seq[Int] = {
    RunningState.ofSeq(values)(0)(_ + _).takeWhile(_.state <= threshold).map(_.value)
  }

  override val Name: String = "RunningStateTotals"
}
