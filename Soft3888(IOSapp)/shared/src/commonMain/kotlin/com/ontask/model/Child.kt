package com.ontask.model

/**
 * Child
 * subclass of User
 *
 * @constructor
 *
 * @param name
 * @param dateOfBirth
 */
data class Child(
    val userID: String,
    var name: String,
    var dateOfBirth: String,
    var chooseTheme: Theme,
    var avatarPic: String?,
    var points: Int){
    var qrCode: QRcode ?= null
    var totalCreditPoint: Int = 0
    var finishedChoreList:  List<ChoreTask> = emptyList<ChoreTask>()

}