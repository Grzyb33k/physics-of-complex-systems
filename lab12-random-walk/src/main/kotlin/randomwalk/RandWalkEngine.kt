package pl.kamil.randomwalk
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

data class Point(val x: Int, val y: Int)

/**
 * @property pArr represents probabilities of choosing (left, up) path
 */
class RandWalkEngine(
    private val ndim: Int,
    private val size: Int,
    private val pArr: DoubleArray,
) {

    private var xPosition: Int = 0
    private var yPosition: Int = 0

    private var xPositionDouble: Double = 0.0
    private var yPositionDouble: Double = 0.0

    private var leftProb: Double = 0.0
    private var upProb: Double = 0.0
    private var rightProb: Double = 0.0
    private var downProb: Double = 0.0

    init {
        require(ndim == 1 || ndim == 2) { "Engine supports only 1 or 2 dimensional random walk"}
        require(pArr.size == ndim) { "pArr size should be $ndim but is ${pArr.size}" }

        leftProb = pArr[0] / ndim
        rightProb = (1.0 - pArr[0]) / ndim

        if (ndim == 2) {
            upProb = pArr[1] / ndim
            downProb = (1.0 - pArr[1]) / ndim
        }

//        println("Probabilities are: LEFT (%.2f) | RIGHT (%.2f) | UP (%.2f) | DOWN (%.2f)"
//            .format(leftProb, rightProb, upProb, downProb))
    }

    private fun makeStep() {
        val p = Random.nextDouble()

        val totalSize = 2 * size + 1

        if (p < leftProb) {
            xPosition = (xPosition - 1 + size).mod(totalSize) - size
//            println("moved left")
        }
        else if (p < rightProb + leftProb) {
            xPosition = (xPosition + 1 + size).mod(totalSize) - size
//            println("moved right")
        }
        else if (p < upProb + rightProb + leftProb) {
            yPosition = (yPosition + 1 + size).mod(totalSize) - size
//            println("moved up")
        }
        else {
            yPosition = (yPosition - 1 + size).mod(totalSize) - size
//            println("moved down")
        }

        xPositionDouble = xPosition.toDouble()
        yPositionDouble = yPosition.toDouble()
    }

    private fun makeStepAngle() {

        val angle = Random.nextDouble(0.0, 2.0 * PI)

        xPositionDouble += cos(angle)
        yPositionDouble += sin(angle)

    }

    private fun calculateDistance(): Double {
        return sqrt(xPositionDouble.pow(2.0) + yPositionDouble.pow(2.0))
    }

    private fun calculateDistance1D() = xPosition.toDouble()

    private fun printCoordinates() {
        println("(x, y) = ($xPosition, $yPosition)")
    }

    fun proceedRandomWalk(
        nSteps: Int,
        debug: Boolean = false,
        calculateSimpleDistance: Boolean = true,
        noGrid: Boolean = false,
        ): DoubleArray
    {

        val dHist = DoubleArray(nSteps)

        for (index in 0 until nSteps) {

            if (noGrid) {
                makeStepAngle()
            }
            else {
                makeStep()
            }

            if (debug) {
                printCoordinates()
            }

            dHist[index] = if (calculateSimpleDistance) calculateDistance1D() else calculateDistance()


        }

        return dHist
    }

    fun proceedRandomWalkCoordinates(
        nSteps: Int,
        debug: Boolean = false,
        withoutGrid: Boolean = false,
        noGrid: Boolean = false,
    ): Array<Point>
    {
        val xyHist = Array(nSteps) { Point(0, 0) }

        for (index in 0 until nSteps) {

            if (noGrid) {
                makeStepAngle()
            }
            else {
                makeStep()
            }


            if (debug) {
                printCoordinates()
            }

            xyHist[index] = Point(xPosition, yPosition)

        }

        return xyHist

    }

}