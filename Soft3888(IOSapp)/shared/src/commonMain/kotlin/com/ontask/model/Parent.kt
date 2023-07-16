package com.ontask.model

/**
 * Parent
 *
 * @constructor
 *
 * @param userID
 * @param name
 * @param dateOfBirth
 * @param chooseTheme
 * @param avatarPic
 */
class Parent(
    var userID: String,
    var name: String,
    var dateOfBirth: String,
    var email: String?,
    var chooseTheme: Theme?,
    var avatarPic: String?){
    var qrCodes: ArrayList<QRcode> = ArrayList()
    var choress: ArrayList<ChoreTask>? = ArrayList()
    var contracts: ArrayList<Contract> = ArrayList()

}