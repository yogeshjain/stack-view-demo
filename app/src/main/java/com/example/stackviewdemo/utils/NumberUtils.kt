package com.example.stackviewdemo.utils

class NumberUtils {

}

/**
 * Return number rounded down to nearest 100 position for rounder amount values
 */
fun Int.toRounderAmount(): Int {
    return (this/100)*100
}

/**
 * Return INR representation of amount. For example 200000 becomes 2,00,000
 */
fun Int.cashify(): String {
    var res = ""
    if(this < 1000) {
        res = toString()
    } else if (this < 100000) {
        val str = toString()
        res = str.dropLast(3) +","+toString().drop(str.length - 3)
    } else {
        val str = toString()
        var pos = 3
        res = str
        while (pos < res.length) {
            res = res.dropLast(pos) +","+res.drop(res.length - pos)
            pos += 3
        }
    }

    return res
}