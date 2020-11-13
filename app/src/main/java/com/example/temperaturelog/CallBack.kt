package com.example.temperaturelog

open class CallBack {
    open fun onSuccess(result: String) {}

    open fun onFail(message: String?, requestCode: Int) {}

    open fun onFailure(message: String?) {
        System.err.println(message)
    }
}