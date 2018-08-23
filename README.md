### Various implementations of takeWhile with a state

```
[info] Benchmark              (implementationName)  (maxTotal)   Mode  Cnt         Score         Error  Units
[info] Benchmarks.run                          Var           0  thrpt   10  23886121.593 ± 3782168.304  ops/s
[info] Benchmarks.run                          Var          10  thrpt   10  22813679.150 ±  160952.847  ops/s
[info] Benchmarks.run                          Var         100  thrpt   10   9581988.065 ± 1482600.413  ops/s
[info] Benchmarks.run                          Var        1000  thrpt   10    704276.236 ±   15066.949  ops/s
[info] Benchmarks.run                          Var       10000  thrpt   10     47710.181 ±     581.828  ops/s
[info] Benchmarks.run                          Var      100000  thrpt   10      1921.467 ±      10.370  ops/s
[info] Benchmarks.run                         Fold           0  thrpt   10        82.355 ±       0.809  ops/s
[info] Benchmarks.run                         Fold          10  thrpt   10        78.665 ±      20.262  ops/s
[info] Benchmarks.run                         Fold         100  thrpt   10        83.540 ±       0.590  ops/s
[info] Benchmarks.run                         Fold        1000  thrpt   10        77.826 ±       0.764  ops/s
[info] Benchmarks.run                         Fold       10000  thrpt   10        84.276 ±       0.573  ops/s
[info] Benchmarks.run                         Fold      100000  thrpt   10        79.843 ±       1.011  ops/s
[info] Benchmarks.run                   FoldReturn           0  thrpt   10  21481259.868 ± 1410622.624  ops/s
[info] Benchmarks.run                   FoldReturn          10  thrpt   10   4259724.092 ±  386477.488  ops/s
[info] Benchmarks.run                   FoldReturn         100  thrpt   10    804567.363 ±  580976.457  ops/s
[info] Benchmarks.run                   FoldReturn        1000  thrpt   10     96877.924 ±    4300.354  ops/s
[info] Benchmarks.run                   FoldReturn       10000  thrpt   10     10202.057 ±     673.402  ops/s
[info] Benchmarks.run                   FoldReturn      100000  thrpt   10      1036.203 ±      21.593  ops/s
[info] Benchmarks.run                RunningTotals           0  thrpt   10   6975357.517 ±  139739.499  ops/s
[info] Benchmarks.run                RunningTotals          10  thrpt   10   7127274.894 ±  150390.287  ops/s
[info] Benchmarks.run                RunningTotals         100  thrpt   10   7417250.939 ±  131157.888  ops/s
[info] Benchmarks.run                RunningTotals        1000  thrpt   10   6755402.376 ±  214982.195  ops/s
[info] Benchmarks.run                RunningTotals       10000  thrpt   10   6521602.370 ±  299580.551  ops/s
[info] Benchmarks.run                RunningTotals      100000  thrpt   10   6867498.221 ±   35392.582  ops/s
[info] Benchmarks.run          RunningTotalsNoInit           0  thrpt   10  12549517.736 ±  339562.022  ops/s
[info] Benchmarks.run          RunningTotalsNoInit          10  thrpt   10  10759908.702 ±  165687.576  ops/s
[info] Benchmarks.run          RunningTotalsNoInit         100  thrpt   10  12520794.315 ±  223282.669  ops/s
[info] Benchmarks.run          RunningTotalsNoInit        1000  thrpt   10  11232896.830 ±  377921.030  ops/s
[info] Benchmarks.run          RunningTotalsNoInit       10000  thrpt   10  12057594.341 ±  242217.175  ops/s
[info] Benchmarks.run          RunningTotalsNoInit      100000  thrpt   10  11992446.184 ±  362566.203  ops/s
[info] Benchmarks.run        RunningTotalsReversed           0  thrpt   10   7158468.474 ±   88497.205  ops/s
[info] Benchmarks.run        RunningTotalsReversed          10  thrpt   10   6903421.203 ±  374349.880  ops/s
[info] Benchmarks.run        RunningTotalsReversed         100  thrpt   10   6980748.090 ±   76411.201  ops/s
[info] Benchmarks.run        RunningTotalsReversed        1000  thrpt   10   6871790.977 ±   30761.076  ops/s
[info] Benchmarks.run        RunningTotalsReversed       10000  thrpt   10   6893934.300 ±   34743.251  ops/s
[info] Benchmarks.run        RunningTotalsReversed      100000  thrpt   10   6869311.709 ±   45694.555  ops/s
[info] Benchmarks.run  RunningTotalsReversedNoInit           0  thrpt   10  12592206.169 ±   56890.563  ops/s
[info] Benchmarks.run  RunningTotalsReversedNoInit          10  thrpt   10  12637188.270 ±   87734.804  ops/s
[info] Benchmarks.run  RunningTotalsReversedNoInit         100  thrpt   10  12599547.557 ±  130038.358  ops/s
[info] Benchmarks.run  RunningTotalsReversedNoInit        1000  thrpt   10  12277456.197 ±   91103.418  ops/s
[info] Benchmarks.run  RunningTotalsReversedNoInit       10000  thrpt   10  12146434.645 ±   79632.990  ops/s
[info] Benchmarks.run  RunningTotalsReversedNoInit      100000  thrpt   10  12091365.321 ±   94201.469  ops/s
[info] Benchmarks.run           RunningStateTotals           0  thrpt   10  12436429.340 ±  100248.028  ops/s
[info] Benchmarks.run           RunningStateTotals          10  thrpt   10  12416999.264 ±  107560.933  ops/s
[info] Benchmarks.run           RunningStateTotals         100  thrpt   10  11787405.078 ± 1939469.029  ops/s
[info] Benchmarks.run           RunningStateTotals        1000  thrpt   10  11850309.753 ±   53463.524  ops/s
[info] Benchmarks.run           RunningStateTotals       10000  thrpt   10  11895466.375 ±   65770.492  ops/s
[info] Benchmarks.run           RunningStateTotals      100000  thrpt   10  11854458.776 ±   78308.430  ops/s
```
