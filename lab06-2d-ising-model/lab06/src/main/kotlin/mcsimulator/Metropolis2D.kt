package pl.kamil.mcsimulator

import pl.kamil.model.IsingModel2D
import kotlin.random.Random

class Metropolis2D(val model: IsingModel2D) {

    fun update() {
        val nSpins = model.L * model.L

        for (i in 0 until nSpins) {
            val x = Random.nextInt(0, model.L)
            val y = Random.nextInt(0, model.L)

            val energyDiff = model.calcEnergyDifference(x, y)

            if (energyDiff <= 0) {
                model.inverseSpinAt(x, y)
            } else {
                val p = model.getAcceptProb(energyDiff)
                if (Random.nextDouble() < p) {
                    model.inverseSpinAt(x, y)
                }
            }
        }
    }
}