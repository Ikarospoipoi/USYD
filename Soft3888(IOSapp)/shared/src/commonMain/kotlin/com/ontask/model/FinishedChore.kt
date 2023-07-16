package com.ontask.model


/**
 * finished Chore
 *
 * @property taskID
 * @property name
 * @property description
 * @property achievements
 * @property iconImage
 * @constructor Create empty Chore task
 */
//class ChoreTask(
//    var taskID:String,
//    var name: String,
//    var description: String?,
//    var achievement: Achievement,
//    var iconImage: String) {
//    var children: ArrayList<Child> = ArrayList()
//}

class FinishedChore(
    var choreID: String,
    var name: String,
    var finishedDate: String,
    var point: Int,
    var choreImg: String){

}