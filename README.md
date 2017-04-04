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
     Kotlin sequence  78127  36437  18181   8952   4546   2337   1150    567    261    125
         Kotlin list  72202  35211  17237   8777   4308   2191   1111    572    271    139
      Java 8 streams  66450  33518  16599   8210   4070   2052   1062    578    296    170
            CopyList  63649  32198  15564   7926   4016   1995   1002    490    238    127
               Solid  72592  36178  17708   8619   4429   2182   1052    557    264    125
                Pass  47629  23010  11450   5633   2722   1331    653    334    163     77
          Imperative  45044  21391  10577   4830   2365   1132    538    259    121     52
OVERHEAD
                SIZE   1024    512    256    128     64     32     16      8      4      2
     Kotlin sequence  33083  15046   7604   4121   2181   1204    611    307    140     72
         Kotlin list  27158  13820   6660   3946   1942   1058    572    312    150     86
      Java 8 streams  21406  12126   6022   3380   1705    920    523    318    175    117
            CopyList  18605  10807   4986   3095   1650    863    463    230    117     74
               Solid  27548  14787   7131   3789   2064   1049    513    297    143     73
                Pass   2585   1619    872    802    357    198    114     74     42     24
          Imperative      0      0      0      0      0      0      0      0      0      0
```
