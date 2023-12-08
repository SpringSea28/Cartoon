package com.buyi.cartoon.collect.data

class CollectUpdateBean<T> {

    var type :Int? = null
    var content:T? = null

    companion object{
        val TYPE_READING_CHAPTER = 0
    }
}