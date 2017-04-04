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
*Pass* adds from 3x to 20x less overhead than streaming libraries.

Currently *Pass* does not look as good as other streaming libraries,
but it is just a matter of syntactic sugar, it can be easily wrapped
into streaming interface.

My overall conclusion is that lazy evaluations are
overcomplicated, slow, unsafe and overvalued.

```
TOTALS
                SIZE   1024    512    256    128     64     32     16      8      4      2
     Kotlin sequence  77061  37098  18252   9070   4524   2226   1132    569    269    121
         Kotlin list  72577  35747  17256   8640   4323   2214   1113    564    270    137
      Java 8 streams  65180  32574  16823   7964   4122   2089   1044    568    300    173
               Solid  73735  36747  18355   9113   4419   2214   1089    565    263    124
            CopyList  67823  31724  15657   7990   4089   2088   1018    484    249    127
                Pass  44649  23081  11540   5366   2707   1300    662    333    158     78
          Imperative  43756  21718  10376   5043   2353   1164    561    269    126     55
OVERHEAD
                SIZE   1024    512    256    128     64     32     16      8      4      2
     Kotlin sequence  33304  15379   7875   4027   2171   1061    571    300    142     66
         Kotlin list  28820  14028   6880   3597   1970   1049    551    294    144     82
      Java 8 streams  21423  10856   6447   2921   1769    925    482    298    173    118
               Solid  29979  15028   7979   4070   2065   1049    528    295    136     69
            CopyList  24066  10005   5281   2947   1736    923    456    215    122     71
                Pass    892   1362   1164    322    353    135    100     64     32     23
          Imperative      0      0      0      0      0      0      0      0      0      0
```
