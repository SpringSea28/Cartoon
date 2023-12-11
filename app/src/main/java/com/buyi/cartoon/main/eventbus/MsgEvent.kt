package com.buyi.cartoon.main.eventbus


class MsgEvent() {
    var msgObject: Any? = null
    var msgType:Int? = null


    companion object{
        const val COLLECT_ADD = 0
        const val COLLECT_REMOVE = 1
        const val COLLECT_UPDATE = 2
    }

    override fun toString(): String {
        return "MsgEvent(msgObject=$msgObject, msgType=$msgType)"
    }


}