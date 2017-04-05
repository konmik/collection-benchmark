A simple test of collection libraries on JVM.

No JMH, raw loops. You can blame me. :D

Tested:

- Java 8 streams
- Kotlin sequence
- Kotlin list
- CopyList (simple functions which take a list and return a list)
- Imperative code
- Solid library
- Pass (my new streaming library prototype)

According to the benchmark, imperative code is the fastest. Not a big surprise.

The big surprise is the overall poor performance of lazy libraries.
In most cases it is not better than *CopyList* - my trivial implementation of
operations on list which allocated a new list every time.

The most interesting and perspective is *Pass* - my new look on collection libraries.
Instead of being lazy, it is strict, so it does not need to keep counters
and temporary variables for each operation. It also does not need to call
`hasNext` for each processed item.
*Pass* adds **from 2x to 20x less overhead** than lazy streaming libraries.

My overall conclusion is that lazy evaluations are
overcomplicated, slow, unsafe and overvalued.

```
TOTALS
                SIZE   1024    512    256    128     64     32     16      8      4      2
     Kotlin sequence  78878  55096  23205   9217   4540   2670   1234    723    317    148
         Kotlin list  73244  42902  19850  10224   4904   2628   1132    699    316    155
      Java 8 streams  68229  37215  18002  11124   5243   2493   1158    673    377    197
               Solid  72016  56111  18570  10260   5204   2910   1226    639    290    160
            CopyList  66673  33480  16314  10906   5041   2576   1231    606    285    145
                Pass  51870  35591  11850   6046   3421   1544    718    382    190    102
          Imperative  43083  26227  10527   5929   2950   1468    687    324    140     60
OVERHEAD
                SIZE   1024    512    256    128     64     32     16      8      4      2
     Kotlin sequence  35794  28869  12677   3287   1589   1202    546    399    176     87
         Kotlin list  30161  16674   9323   4294   1954   1160    445    375    176     95
      Java 8 streams  25145  10987   7475   5195   2293   1024    470    349    237    136
               Solid  28932  29883   8042   4330   2253   1441    539    314    150     99
            CopyList  23589   7253   5786   4976   2091   1107    543    282    145     85
                Pass   8786   9364   1322    116    471     75     30     57     50     42
          Imperative      0      0      0      0      0      0      0      0      0      0
```
