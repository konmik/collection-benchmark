package com.konmik.collectionbenchmark

import java.util.*

object BenchmarkKotlin {

    fun benchmarkKotlinSequence(list: List<Int>) {
        list.asSequence()
                .filter { it -> it % 10 != 0 }
                .map(Int::toString)
                .flatMap { it -> sequenceOf(it, it + 1) }
                .toCollection(ArrayList())
    }

    fun benchmarkKotlinList(list: List<Int>) {
        list.filter { it -> it % 10 != 0 }
                .map(Int::toString)
                .flatMap { it -> mutableListOf(it, it + 1) }
                .toList()
    }
}

