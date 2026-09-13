package pl.kamil.kuramoto
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations
import kotlin.math.sin

class KuramotoModel(
    private val n: Int,
    private val k: Double,
    private val omegas: DoubleArray,
) : FirstOrderDifferentialEquations {
    override fun getDimension(): Int = n

    override fun computeDerivatives(t: Double, y: DoubleArray, yDot: DoubleArray) {
        for (i in 0 until n) {
            var sum = 0.0

            for (j in 0 until n) {
                sum += sin(y[j] - y[i])
            }

            yDot[i] = omegas[i] + k / n * sum
        }
    }
}