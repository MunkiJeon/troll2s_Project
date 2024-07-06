package com.example.trolls

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        val n = 123L
        n.inv()
    }

    fun solution(numbers: IntArray): String {
        val sortedNumbers = numbers.map { it.toString() }
            .sortedWith { a, b ->
                (b + a).compareTo(a + b)
            }

        val result = sortedNumbers.joinToString("")

        return if (result.startsWith("0")) "0" else result
    }
}