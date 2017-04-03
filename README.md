A simple test of collection libraries on JVM.

No JMH, raw loops. You can blame me. :D

```
size: 1024, Java 8 streams:  79224, Kotlin sequence:106786, Kotlin list:  91538, Imperative: 48224, Solid: 76012, Pass:  49493
size:  512, Java 8 streams:  37234, Kotlin sequence: 39450, Kotlin list:  37235, Imperative: 23282, Solid: 37406, Pass:  25652
size:  256, Java 8 streams:  17081, Kotlin sequence: 18599, Kotlin list:  19104, Imperative: 10337, Solid: 17722, Pass:  11359
size:  128, Java 8 streams:   8680, Kotlin sequence:  9760, Kotlin list:   9273, Imperative:  5001, Solid:  8710, Pass:   5280
size:   64, Java 8 streams:   4172, Kotlin sequence:  4711, Kotlin list:   4686, Imperative:  2292, Solid:  4274, Pass:   2703
size:   32, Java 8 streams:   2065, Kotlin sequence:  2174, Kotlin list:   2202, Imperative:  1158, Solid:  2153, Pass:   1315
size:   16, Java 8 streams:   1074, Kotlin sequence:  1094, Kotlin list:   1156, Imperative:   552, Solid:  1089, Pass:    653
size:    8, Java 8 streams:    571, Kotlin sequence:   549, Kotlin list:    559, Imperative:   261, Solid:   550, Pass:    336
size:    4, Java 8 streams:    307, Kotlin sequence:   263, Kotlin list:    261, Imperative:   124, Solid:   295, Pass:    165
size:    2, Java 8 streams:    183, Kotlin sequence:   125, Kotlin list:    136, Imperative:    52, Solid:   125, Pass:     77

OVERHEAD:    size: 1024, Java 8 streams:  30999, Kotlin sequence: 58561, Kotlin list:  43313, Solid: 27787, Pass:   1269
OVERHEAD:    size:  512, Java 8 streams:  13952, Kotlin sequence: 16168, Kotlin list:  13952, Solid: 14123, Pass:   2370
OVERHEAD:    size:  256, Java 8 streams:   6743, Kotlin sequence:  8261, Kotlin list:   8767, Solid:  7385, Pass:   1021
OVERHEAD:    size:  128, Java 8 streams:   3678, Kotlin sequence:  4758, Kotlin list:   4271, Solid:  3709, Pass:    278
OVERHEAD:    size:   64, Java 8 streams:   1880, Kotlin sequence:  2418, Kotlin list:   2394, Solid:  1982, Pass:    411
OVERHEAD:    size:   32, Java 8 streams:    907, Kotlin sequence:  1016, Kotlin list:   1044, Solid:   994, Pass:    157
OVERHEAD:    size:   16, Java 8 streams:    521, Kotlin sequence:   541, Kotlin list:    603, Solid:   536, Pass:    101
OVERHEAD:    size:    8, Java 8 streams:    310, Kotlin sequence:   288, Kotlin list:    297, Solid:   288, Pass:     74
OVERHEAD:    size:    4, Java 8 streams:    183, Kotlin sequence:   138, Kotlin list:    137, Solid:   171, Pass:     40
OVERHEAD:    size:    2, Java 8 streams:    130, Kotlin sequence:    73, Kotlin list:     83, Solid:    72, Pass:     24
```
