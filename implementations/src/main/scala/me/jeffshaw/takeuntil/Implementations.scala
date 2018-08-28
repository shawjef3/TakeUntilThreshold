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
      case VectorRecursiveFunction.Name => VectorRecursiveFunction
      case StreamRecursiveFunction.Name => StreamRecursiveFunction
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

object VectorRecursiveFunction extends Implementation {
  override val Name: String = "VectorRecursiveFunction"

  // Using an iterator lets us not worry about `values`'s indexed lookup efficiency.
  override def apply(values: Seq[Int], threshold: Int): Seq[Int] = {
    @tailrec
    def aux(accum: mutable.Builder[Int, Seq[Int]], sum: Int, i: Iterator[Int]): Seq[Int] = {
      if (i.hasNext) {
        val next = i.next()
        val nextSum = sum + next
        if (nextSum <= threshold) {
          aux(accum += next, nextSum, i)
        } else accum.result()
      } else accum.result()
    }

    aux(Vector.newBuilder[Int], 0, values.iterator)
  }
}

object StreamRecursiveFunction extends Implementation {
  override val Name: String = "StreamRecursiveFunction"

  // Using an iterator lets us not worry about `values`'s indexed lookup efficiency.
  override def apply(values: Seq[Int], threshold: Int): Seq[Int] = {
    def aux(sum: Int, i: Iterator[Int]): Stream[Int] = {
      if (i.hasNext) {
        val next = i.next()
        val nextSum = sum + next
        if (nextSum <= threshold) {
          next #:: aux(nextSum, i)
        } else Stream.empty
      } else Stream.empty
    }

    aux(0, values.iterator)
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
