package com.ontask.model

/**
 * Family
 *
 * @property name
 * @property parents
 * @property children
 * @constructor Create empty Family
 */
data class Family(
    var name: String,
    var parents: ArrayList<Parent>,
    var children: ArrayList<Child>)