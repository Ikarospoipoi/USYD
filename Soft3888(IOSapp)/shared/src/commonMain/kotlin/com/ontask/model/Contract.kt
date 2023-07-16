package com.ontask.model

/**
 * Contract
 *
 * @property name
 * @property sponser
 * @property receiver
 * @property maxPoint
 * @property rewards
 * @property detail
 * @constructor Create empty Contract
 */
data class Contract(
    var parentId: String,
    var childName: String,
    var childId: String,
    var maxPoint: Int,
    var rewardList: List<String>,
    var pointList: List<String>,
    var detail: String?)