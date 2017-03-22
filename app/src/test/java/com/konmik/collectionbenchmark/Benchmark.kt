package com.konmik.collectionbenchmark

object Benchmark {
    fun benchmarkKotlin(list: ArrayList<Int>) {
        list.asSequence()
                .filter { it -> it % 10 != 0 }
                .map(Int::toString)
                .flatMap { it -> sequenceOf(it, it + 1) }
                .toCollection(ArrayList())
    }

    fun benchmarkKotlinDirect(list: ArrayList<Int>) {
        list.filter { it -> it % 10 != 0 }
                .map(Int::toString)
                .flatMap { it -> mutableListOf(it, it + 1) }
                .toList()
    }
}

