package com.ontask

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}