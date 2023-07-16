package com.ontask.module

import com.ontask.model.*


/**
 * Dashboard module
 * functionalities needed in dashboard, this class might need to work with cloud database in the futrue
 *
 * @constructor Create empty Dashboard module
 */
class DashboardModule {

    /**
     * Add child to the family
     *
     * @param name child's name
     * @param dateOfBirth child's date of birth
     * @return true if child is added successfully, otherwise false
     */
    fun addChild(name: String, dateOfBirth: String): Boolean {

        return false
    }

    /**
     * Assign task to a child
     *
     * @param choreTask the task will be assigned
     * @param parent who will assign this task
     * @param child who will be assigned this task to
     */
    fun assignTask(choreTask: ChoreTask, parent: Parent, child: Child){

    }

    /**
     * Create chore
     * achievements will be tagged to the created chore
     *
     * @param name name of the chore
     * @param description description of the chore
     * @param achievements all achievements for the chore based on different completion
     */
    fun createChore(name: String, description: String, achievements: ArrayList<Achievement>){

    }

    /**
     * List chores
     * for UI to easily access the list of chores
     *
     * @return the list of chores
     */
    fun listChores(): ArrayList<ChoreTask>?{

        return null
    }

    /**
     * Create contract
     *
     * @param name name of the contract
     * @param maxPoint max point of the contract
     * @param rewards different rewards for different point
     * @param sponser who initiate this contract
     * @param receiver who will agree to this contract
     * @param detail detail of contract
     */
    fun createContract(name: String, maxPoint: Int, rewards: ArrayList<Reward>, sponser: Parent, receiver: Child, detail: String?){

    }

    /**
     * List contracts
     * for UI to easily access the list of contracts
     *
     * @return the list of contract
     */
    fun listContracts(): ArrayList<Contract>?{

        return null
    }

    /**
     * Create achievement
     *
     * @param points point will be gained in this achievement
     * @param message message will display when achieved
     * @return the created achievements
     */
    fun createAchievement(points: Int, message: String): Achievement?{
        return null
    }

    /**
     * List children
     * for UI to easily access the list of children
     *
     * @return a list of children
     */
    fun listChildren(): ArrayList<Child>?{
        return null
    }

    /**
     * Change theme
     *
     * @param theme
     * @return
     */
    fun changeTheme(theme: Theme): Boolean{
        return false
    }

    /**
     * Edit profile
     *
     * @param user the user who will be changed profile
     * @param name the name he/she wants to change to
     * @param dateOfBirth the date of birth he/she wants to change to
     * @return
     */
    fun editProfile(user: Child, name: String, dateOfBirth: String): Boolean{
        return false
    }

}