package com.buyi.cartoon.http.bean

import kotlinx.coroutines.flow.callbackFlow

class ClassifyInfoBean(){
    var id:Int? = null
    var classification:String? = null

    constructor(id:Int,classification:String):this(){
        this.id = id
        this.classification = classification
    }
}
