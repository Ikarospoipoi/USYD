package com.ontask.model

/**
 * Chore task
 *
 * @property taskID
 * @property name
 * @property description
 * @property achievements
 * @property iconImage
 * @constructor Create empty Chore task
 */
class ChoreTask(
    var taskID:String,
    var name: String,
    var description: String?,
    var achievement: Achievement,
    var iconImage: String,
    var createdDate: String) {
    var children: ArrayList<Child> = ArrayList()
}