package me.jeffshaw.takeuntil

case class RunningState[Value, State](
  value: Value,
  state: State
)

/**
  * A generalization of [[me.jeffshaw.takeuntil.RunningTotal]] where the state is arbitrary.
  *
  * The state at each node is a combination of the state at the previous node (or init if
  * the node is the first one), and the value at the current node.
  */
object RunningState {
  def ofSeq[Value, State](values: Seq[Value])(init: State)(combine: (Value, State) => State): Stream[RunningState[Value, State]] = {
    values.toStream match {
      case head #:: tail =>
        lazy val states: Stream[RunningState[Value, State]] =
          RunningState(head, combine(head, init)) #:: {
            for {
              (value, RunningState(_, previousState)) <- tail.zip(states)
            } yield RunningState(value, combine(value, previousState))
          }

        states
      case _ =>
        Stream.empty[RunningState[Value, State]]
    }
  }

  object Syntax {
    implicit class SeqRunningState[Value](values: Seq[Value]) {
      def runningTotals[State](init: State)(combine: (Value, State) => State): Stream[RunningState[Value, State]] = {
        RunningState.ofSeq(values)(init)(combine)
      }
    }
  }
}
