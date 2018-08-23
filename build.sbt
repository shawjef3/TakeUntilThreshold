lazy val implementations = project.in(file("implementations"))

lazy val benchmarks = project.in(file("benchmarks")).dependsOn(implementations)

lazy val root = project.in(file(".")).aggregate(
  implementations,
  benchmarks
)
