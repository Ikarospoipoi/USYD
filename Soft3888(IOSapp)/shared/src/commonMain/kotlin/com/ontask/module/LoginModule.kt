package com.ontask.module

/**
 * Login module
 *
 * @constructor Create empty Login module
 */
class LoginModule() {



    /**
     * Authenticate
     *
     * @param username
     * @param password
     * @return
     */
    fun authenticate_1(username: String): Boolean{
        if(isEmailValid(username)){
            return true
        }

        return false
    }

    fun checkPassword(password: String): Boolean {
        if(password.length >=6){
            return true
        }
        return false
    }

    fun final_check(username: String, password: String): Boolean{
        if(authenticate_1(username)==true && checkPassword(password)==true){
            return true
        }
        return false
    }

    private fun isEmailValid(email: String) = emailRegex.matches(email)

    companion object {
        private val emailRegex =
            ("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+").toRegex()
    }
}