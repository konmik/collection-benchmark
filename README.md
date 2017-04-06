A simple test of collection libraries on JVM.

Tested:

- Java 8 streams
- Kotlin sequence
- Kotlin list
- CopyList (simple functions which take a list and return a list)
- Imperative code
- Solid library
- Pass (my new streaming library prototype)

According to the benchmark, imperative code is the fastest.
Not a big surprise.

The big surprise is the overall poor performance of lazy libraries.
In most cases it is not better than *CopyList* - my trivial implementation of
operations on list which allocated a new list every time.

The most interesting and perspective is *Pass* - my new look on collection libraries.
Instead of being lazy, it is strict, so it does not need to keep excessive temporary variables.
It also does not need to call `hasNext` for each processed item so it is thread-safe.
*Pass* adds **from 2x to 5x less overhead** than lazy streaming libraries.

My overall conclusion is that lazy evaluations are
overcomplicated, slow, unsafe and overvalued.

JMH tested results on my Mac with max 3% error:

```
LIST SIZE                       2     8    32   128  1024

BENCHMARK, nanoseconds
BenchmarkJmh.imperative        46   198  1158  4848 39345
BenchmarkJmh.pass              73   289  1424  5634 46695
BenchmarkJmh.passUnoptimized   87   342  1693  6741 54779
BenchmarkJmh.solid            109   472  2093  8626 71177
BenchmarkJmh.copyList         116   411  2074  8022 63981
BenchmarkJmh.kotlinSequence   116   471  2187  8945 71930
BenchmarkJmh.kotlinList       131   506  2189  8530 71752
BenchmarkJmh.java8            165   520  2153  8043 64735

OVERHEAD, nanoseconds
BenchmarkJmh.imperative         0     0     0     0     0
BenchmarkJmh.pass              27    91   266   785  7350
BenchmarkJmh.passUnoptimized   41   144   535  1892 15433
BenchmarkJmh.solid             63   274   935  3777 31831
BenchmarkJmh.copyList          69   213   916  3173 24635
BenchmarkJmh.kotlinSequence    70   274  1029  4097 32584
BenchmarkJmh.kotlinList        85   308  1031  3682 32407
BenchmarkJmh.java8            119   323   996  3195 25390
```
