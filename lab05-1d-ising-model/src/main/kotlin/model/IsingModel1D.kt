package pl.kamil.model

import kotlin.math.exp
import kotlin.random.Random
import kotlin.math.min

class IsingModel1D(
    val nSpins: Int,
    val exchInt: Double = 1.0,
    val magnFieldB: Double = 0.0,
    var temperature: Double = 1.0,
) {


    private var kB = 1.0

    private var totalEnergy: Double = 0.0

    private var spinsArr = IntArray(nSpins) { if (Random.nextBoolean()) 1 else -1 }

    init { totalEnergy = calcTotalEnergy() }

    fun calcTotalEnergy(): Double {

        var totE = 0.0

        for (i in 0 until nSpins) {
            val j = (i + 1 + nSpins) % nSpins

            totE -= exchInt * spinsArr[i] * spinsArr[j] +  magnFieldB * spinsArr[i]

        }

        return totE
    }

    fun calcEnergyDifference(index: Int): Double {
        val sOld = spinsArr[index]
        val nextIndex = (index + 1 + nSpins) % nSpins
        val prevIndex = (index - 1 + nSpins) % nSpins

        return 2 * (sOld) * (   exchInt * spinsArr[nextIndex]
                                 + magnFieldB
                                 + exchInt * spinsArr[prevIndex]  )
    }

    private fun updateTotalEnergy() {
        totalEnergy = calcTotalEnergy()
    }

    fun updateEnergy(dE: Double) {
        totalEnergy += dE
    }

    fun getTotalEnergy() = totalEnergy

    fun getSize() = nSpins

    fun inverseSpinAt(index: Int) {
        spinsArr[index] *= -1
    }

    fun getAcceptProb(deltaE: Double) = min(1.0, exp(-deltaE / (kB * temperature)))

    fun changeTemperature(newTemperature: Double) {
        temperature = newTemperature
    }

    fun getSpinAt(index: Int) = spinsArr[(index + nSpins) % nSpins]

    fun setSpinAt(index: Int, value: Int) {
        spinsArr[(index + nSpins) % nSpins] = value
    }

    fun getTotalEnergyDensity(): Double {

        var totalDens = 0.0

        for (i in 0 until nSpins) {
            totalDens -= getSpinAt(i) * getSpinAt(i + 1)
        }

        return totalDens / nSpins
    }

    fun fillSpinsWith(spin: Int) {
        spinsArr = IntArray(nSpins) {spin}
        updateTotalEnergy()
    }
}