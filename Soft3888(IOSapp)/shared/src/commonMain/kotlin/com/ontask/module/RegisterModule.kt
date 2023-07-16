package com.ontask.module

import com.ontask.model.Child
import com.ontask.model.Parent

/**
 * Register module
 *
 * @constructor Create empty Register module
 */
class RegisterModule {

    /**
     * Check validation of the account
     *
     * @param username
     * @param password
     * @return
     */
    fun checkValidation(username: String, password: String): Boolean{

        return true
    }

    /**
     * Create family
     *
     * @param name
     * @param parents
     * @param children
     * @return
     */
    fun createFamily(name: String, parents: ArrayList<Parent>, children: ArrayList<Child>): Boolean{

        return false
    }

    /**
     * Register
     *
     * @param username
     * @param password
     * @return
     */
    fun register(username: String, password: String): Boolean{
        var validation: Boolean = checkValidation(username, password)

        return validation
    }

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