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
    if (values.isEmpty) {
      Stream.empty[RunningState[Value, State]]
    } else {
      lazy val states: Stream[RunningState[Value, State]] =
        RunningState(values.head, combine(values.head, init)) #:: {
          for {
            (value, RunningState(_, previousState)) <- values.toStream.tail.zip(states)
          } yield RunningState(value, combine(value, previousState))
        }

      states
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
