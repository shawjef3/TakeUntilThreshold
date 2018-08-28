package me.jeffshaw.takeuntil

case class RunningTotal[N](
  value: N,
  total: N
)

object RunningTotal {
  def ofSeq[N](values: Seq[N])(implicit num: Numeric[N]): Stream[RunningTotal[N]] = {
    values.toStream match {
      case head #:: tail =>
        lazy val totals: Stream[RunningTotal[N]] =
          RunningTotal(head, head) #:: {
            for {
              (value, RunningTotal(_, previousTotal)) <- tail.zip(totals)
            } yield RunningTotal(value, num.plus(value, previousTotal))
          }

        totals

      case _ =>
        Stream.empty[RunningTotal[N]]
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
    // Don't use +: pattern matching here, because it might create a new collection for the tail.
    if (values.nonEmpty) {
      lazy val totals: Stream[RunningTotal[N]] =
        RunningTotal(values.head, values.head) #:: {
          for {
            (value, RunningTotal(_, previousTotal)) <- values.toStream.tail.zip(totals)
          } yield RunningTotal(value, num.plus(value, previousTotal))
        }

      totals
    } else Stream.empty[RunningTotal[N]]
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
    values.toStream match {
      case head +: tail =>
        lazy val totals: Stream[RunningTotal[N]] =
          RunningTotal(head, head) #:: {
            for {
              (RunningTotal(_, previousTotal), value) <- totals.zip(tail)
            } yield RunningTotal(value, num.plus(value, previousTotal))
          }

        totals
      case _ =>
        Stream.empty[RunningTotal[N]]
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
    // Don't use +: pattern matching here, because it might create a new collection for the tail.
    if (values.nonEmpty) {
      lazy val totals: Stream[RunningTotal[N]] =
        RunningTotal(values.head, values.head) #:: {
          for {
            (RunningTotal(_, previousTotal), value) <- totals.zip(values.toStream.tail)
          } yield RunningTotal(value, num.plus(value, previousTotal))
        }

      totals
    } else Stream.empty[RunningTotal[N]]
  }

  object Syntax {
    implicit class SeqRunningTotal[N](values: Seq[N])(implicit num: Numeric[N]) {
      def runningTotals: Stream[RunningTotal[N]] = {
        RunningTotal.ofSeq(values)
      }
    }
  }

}
