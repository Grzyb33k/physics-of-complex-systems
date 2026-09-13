package pl.kamil.kuramoto
import kotlin.random.Random as kotlinRandom
import java.util.Random as javaRandom
import kotlin.math.PI

object KuramotoGenerator {
    fun generateRandomOmegasNormal(n: Int, mu: Double = 0.0, sigma: Double = 1.0): DoubleArray {
        return DoubleArray(n) {
            mu + sigma * javaRandom().nextGaussian()
        }
    }

    fun generateRandomThetasUniform(n: Int, min: Double = 0.0, max: Double = 2 * PI): DoubleArray {
        return DoubleArray(n) {
            kotlinRandom.nextDouble(min, max)
        }
    }

    fun generateTask3InitialThetas(n: Int): DoubleArray {
        val thetasArr = DoubleArray(n) { 0.0 }

        for (i in 0 until n) {

            if (i < n / 2) {
                thetasArr[i] = kotlinRandom.nextDouble(0.0, 2.0 * PI)
            }
            else {
                thetasArr[i] = kotlinRandom.nextDouble(0.0, PI / 12.0)
            }

        }

        return thetasArr
    }

}