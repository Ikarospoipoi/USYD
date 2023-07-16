package com.ontask.android.model
/*
 * The model objects in this file can be changed accordingly based on the requirements and implementation.
 * These are just a guideline to describe the expectations. Please try and use meaningful names and appropriate
 * comments as seen below.
 *
 * NOTE: We will need to figure out how to track the different devices within a family so that they can access
 * the family information and so that they can receive push notifications.
 */
import com.ontask.model.Theme
import java.time.LocalDate

/**
 * Family describes the family relationship.
 *
 * @param familyId the id assigned to the family once the account is created. This id will belong to
 *                 each member of the family and can be used to access the family data from the database.
 * @param email the main account email and is optional.
 * @param phone the main account phone number and is optional.
 * @param parents a list of the parents in the family
 * @param children a list of the children in the family
 */
data class Family(
    val familyId: String,
    val email: String = "",
    val phone: String = "",
    val parents: List<FamilyMember> = listOf(),
    val children: List<FamilyMember> = listOf()
)


/**
 * Parent describes a parent/adult in a family.
 *
 * @param memberId the id assigned to a adult/parent member. This is unique Integer for the adult to make querying
 *                 their information easier.
 * @param name the name of the member.
 * @param dateOfBirth the date of birth of the member.
 * @param contract the contract for the child. This is null for the parent.
 * @param achievements the child members achievements. Empty for parent members.
 * @param rewards the child members achieved rewards. Empty for parent members.
 */
data class FamilyMember(
    val memberId: Int,
    val name: String,
    val dateOfBirth: LocalDate? = null,
    val contract: Contract? = null,
    val achievements: List<Achievement> = listOf(),
    val rewards: List<Reward> = listOf()
)


/**
 * Contract that describes the maximum points achievable and the point to reward mapping.
 *
 * @param contractId the contract id number.
 * @param maxPoints the maximum attainable points in this contract.
 * @param rewards the mapping of points to custom defined rewards.
 *                e.g., [25 to "$5", 50 to "$5", 75 to "1hr+ PS5"].
 */
data class Contract(
    val contractId: Int,
    val maxPoints: Int = 0,
    val rewards: Map<Int, Reward>
)


/**
 * Task describes the performable tasks or chores.
 *
 * @param taskId a unique task id.
 * @param name the task name.
 * @param image a link or location of the task image/emoji etc.
 */
data class Task(
    val taskId: Int,
    val name: String,
    val image: String = "link to default task image or something else here if an image is not provided"
)


/**
 * Achievement describes a task achieved by a child, the point value gained and the message given by the parent
 *
 * @param task the task completed.
 * @param points the points received for completing this task, 1, 2 or 3.
 * @param message the message accompanying this task and point score.
 */
data class Achievement(val task: Task?, val points: Int, val message: String)


/**
 * Reward describes a reward agreed upon by a child and parent.
 *
 * @param name the name or details of the reward. e.g., "$5".
 * @param image a link or location for the emoji of the image representing this reward.
 */
data class Reward(
    val name: String,
    val image: String = "link to default star image or something else here if an image is not provided"
)