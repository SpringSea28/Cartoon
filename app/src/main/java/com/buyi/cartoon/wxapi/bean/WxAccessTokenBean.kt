package com.buyi.cartoon.wxapi.bean

class WxAccessTokenBean {
    var access_token:String? = null
    var expires_in:Int? = null
    var refresh_token:String? = null
    var openid:String? = null
    var scope:String? = null
    var unionid:String? = null

    var errcode:Int? = null
    var errmsg:String? = null

}