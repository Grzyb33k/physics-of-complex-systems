package pl.kamil.model

import kotlin.math.exp
import kotlin.random.Random
import kotlin.math.min

class IsingModel2D(
    val L: Int,
    val exchInt: Double = 1.0,
    val magnFieldB: Double = 0.0,
    var temperature: Double = 1.0
) {
    private val kB = 1.0

    private var spinsGrid = Array(L) { IntArray(L) { if (Random.nextBoolean()) 1 else -1 } }

    fun calcEnergyDifference(x: Int, y: Int): Double {
        val sOld = spinsGrid[y][x]

        val top = spinsGrid[(y - 1 + L) % L][x]
        val bottom = spinsGrid[(y + 1) % L][x]
        val left = spinsGrid[y][(x - 1 + L) % L]
        val right = spinsGrid[y][(x + 1) % L]

        val sumNeighbors = top + bottom + left + right

        return 2.0 * sOld * (exchInt * sumNeighbors + magnFieldB)
    }

    fun inverseSpinAt(x: Int, y: Int) {
        spinsGrid[y][x] *= -1
    }

    fun getAcceptProb(deltaE: Double) = min(1.0, exp(-deltaE / (kB * temperature)))

    fun changeTemperature(newTemperature: Double) {
        temperature = newTemperature
    }

    fun getSpinAt(x: Int, y: Int) = spinsGrid[(y + L) % L][(x + L) % L]

    fun getTotalMagnetization(): Double {
        var totalM = 0.0
        for (y in 0 until L) {
            for (x in 0 until L) {
                totalM += spinsGrid[y][x]
            }
        }
        return totalM
    }

    fun fillSpinsWith(spin: Int) {
        spinsGrid = Array(L) { IntArray(L) { spin } }
    }
}