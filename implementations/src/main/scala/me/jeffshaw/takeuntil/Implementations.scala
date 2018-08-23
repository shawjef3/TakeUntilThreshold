package me.jeffshaw.takeuntil

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
  def apply(values: Seq[Int], threshold: Int): Seq[Int] = {
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
  def apply(values: Seq[Int], threshold: Int): Seq[Int] = {
    RunningTotalNoInit.ofSeq(values).
      takeWhile(_.total <= threshold).
      map(_.value)
  }

  override val Name: String = "RunningTotalsNoInit"
}

object RunningTotalsReversedNoInit extends Implementation {
  def apply(values: Seq[Int], threshold: Int): Seq[Int] = {
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
