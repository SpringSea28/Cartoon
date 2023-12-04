package com.buyi.cartoon.http.bean

class ClassifyInfoBean(){
    var id:Int? = null
    var classification:String? = null

    constructor(id:Int,classification:String):this(){
        this.id = id
        this.classification = classification
    }
}
