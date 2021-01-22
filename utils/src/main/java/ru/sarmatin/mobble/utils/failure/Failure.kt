package ru.sarmatin.mobble.utils.failure

/**
 * Created by antonsarmatin
 * Date: 2020-02-25
 * Project: Mobble
 *
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure {

    object NetworkConnection : Failure()

    data class ServerError(
        val errorMessage: String = "Unknown Error",
        val statusCode: Int = 0
    ) : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()

    /** * This failure may be ignored.*/
    object Ignore : Failure()

    /** Stub */
    object NoFailure : Failure()
}