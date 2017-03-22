A simple test of collection libraries on JVM.

No JMH, raw loops. You can blame me. :D

```
    size: 1024, Java 8 streams:  72864, Kotlin sequence:103666, Kotlin list:  85345, Imperative: 49900, Solid: 85653
    size:  512, Java 8 streams:  33132, Kotlin sequence: 35798, Kotlin list:  35992, Imperative: 21068, Solid: 37244
    size:  256, Java 8 streams:  16423, Kotlin sequence: 17844, Kotlin list:  17561, Imperative: 10330, Solid: 18371
    size:  128, Java 8 streams:   8094, Kotlin sequence:  8772, Kotlin list:   8497, Imperative:  4911, Solid:  9426
    size:   64, Java 8 streams:   4071, Kotlin sequence:  4587, Kotlin list:   4672, Imperative:  2343, Solid:  4659
    size:   32, Java 8 streams:   2082, Kotlin sequence:  2196, Kotlin list:   2207, Imperative:  1121, Solid:  2320
    size:   16, Java 8 streams:   1103, Kotlin sequence:  1119, Kotlin list:   1113, Imperative:   537, Solid:  1239
    size:    8, Java 8 streams:    606, Kotlin sequence:   588, Kotlin list:    617, Imperative:   271, Solid:   708
    size:    4, Java 8 streams:    300, Kotlin sequence:   264, Kotlin list:    262, Imperative:   132, Solid:   349
    size:    2, Java 8 streams:    176, Kotlin sequence:   122, Kotlin list:    132, Imperative:    59, Solid:   161
```
