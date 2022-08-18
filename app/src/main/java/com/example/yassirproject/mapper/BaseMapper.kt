package com.example.yassirproject.mapper

interface BaseMapper<K, V> {
    fun mapFrom(to: V): K
    fun mapTo(from: K): V
}