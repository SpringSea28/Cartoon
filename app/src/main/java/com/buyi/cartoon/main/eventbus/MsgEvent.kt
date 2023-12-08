package com.buyi.cartoon.main.eventbus


class MsgEvent() {
    var msgObject: Any? = null
    var msgType:Int? = null


    companion object{
        val COLLECT_ADD = 0
        val COLLECT_REMOVE = 1
    }

    override fun toString(): String {
        return "MsgEvent(msgObject=$msgObject, msgType=$msgType)"
    }


}