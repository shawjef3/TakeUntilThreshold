package me.jeffshaw

import org.openjdk.jmh.infra.Blackhole

package object takeuntil {

  /**
    * Runs a function a given number of times, and ensures that the
    * JVM can't optimize it away.
    * @param n
    * @param blackhole
    * @param f
    * @tparam A
    */
  def repeatedly[A](n: Int, blackhole: Blackhole)(f: => A): Unit = {
    var i = 0
    while (i < n) {
      blackhole.consume(f)
      i += 1
    }
  }

}
