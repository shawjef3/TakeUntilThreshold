package me.jeffshaw.takeuntil

case class RunningTotal[N](
  value: N,
  total: N
)

object RunningTotal {
  def ofSeq[N](values: Seq[N])(implicit num: Numeric[N]): Stream[RunningTotal[N]] = {
    if (values.isEmpty) {
      Stream.empty[RunningTotal[N]]
    } else {
      val valueStream = values.toStream
      val firstValue = valueStream.head

      lazy val totals: Stream[RunningTotal[N]] =
        RunningTotal(firstValue, firstValue) #:: {
          for {
            (value, RunningTotal(_, previousTotal)) <- valueStream.tail.zip(totals)
          } yield RunningTotal(value, num.plus(value, previousTotal))
        }

      totals
    }
  }

  object Syntax {
    implicit class SeqRunningTotal[N](values: Seq[N])(implicit num: Numeric[N]) {
      def runningTotals: Stream[RunningTotal[N]] = {
        RunningTotal.ofSeq(values)
      }
    }
  }
}

object RunningTotalNoInit {
  def ofSeq[N](values: Seq[N])(implicit num: Numeric[N]): Stream[RunningTotal[N]] = {
    if (values.isEmpty) {
      Stream.empty[RunningTotal[N]]
    } else {
      lazy val totals: Stream[RunningTotal[N]] =
        RunningTotal(values.head, values.head) #:: {
          for {
            (value, RunningTotal(_, previousTotal)) <- values.toStream.tail.zip(totals)
          } yield RunningTotal(value, num.plus(value, previousTotal))
        }

      totals
    }
  }

  object Syntax {
    implicit class SeqRunningTotal[N](values: Seq[N])(implicit num: Numeric[N]) {
      def runningTotals: Stream[RunningTotal[N]] = {
        RunningTotal.ofSeq(values)
      }
    }
  }
}

object RunningTotalReversed {
  def ofSeq[N](values: Seq[N])(implicit num: Numeric[N]): Stream[RunningTotal[N]] = {
    if (values.isEmpty) {
      Stream.empty[RunningTotal[N]]
    } else {
      val valueStream = values.toStream
      val firstValue = valueStream.head

      lazy val totals: Stream[RunningTotal[N]] =
        RunningTotal(firstValue, firstValue) #:: {
          for {
            (RunningTotal(_, previousTotal), value) <- totals.zip(valueStream.tail)
          } yield RunningTotal(value, num.plus(value, previousTotal))
        }

      totals
    }
  }

  object Syntax {
    implicit class SeqRunningTotal[N](values: Seq[N])(implicit num: Numeric[N]) {
      def runningTotals: Stream[RunningTotal[N]] = {
        RunningTotal.ofSeq(values)
      }
    }
  }

}

object RunningTotalReversedNoInit {
  def ofSeq[N](values: Seq[N])(implicit num: Numeric[N]): Stream[RunningTotal[N]] = {
    if (values.isEmpty) {
      Stream.empty[RunningTotal[N]]
    } else {
      lazy val totals: Stream[RunningTotal[N]] =
        RunningTotal(values.head, values.head) #:: {
          for {
            (RunningTotal(_, previousTotal), value) <- totals.zip(values.toStream.tail)
          } yield RunningTotal(value, num.plus(value, previousTotal))
        }

      totals
    }
  }

  object Syntax {
    implicit class SeqRunningTotal[N](values: Seq[N])(implicit num: Numeric[N]) {
      def runningTotals: Stream[RunningTotal[N]] = {
        RunningTotal.ofSeq(values)
      }
    }
  }

}
