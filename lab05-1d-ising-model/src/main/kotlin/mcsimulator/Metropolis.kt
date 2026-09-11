package pl.kamil.mcsimulator

import pl.kamil.model.IsingModel1D

import kotlin.random.Random

class Metropolis(
    val model: IsingModel1D,
) {

    private val size = model.getSize()

    fun update() {

        val i = Random.nextInt(0, size)

        val energyDiff = model.calcEnergyDifference(i)

        if (energyDiff < 0) {
            model.inverseSpinAt(i)
            model.updateEnergy(energyDiff)
        } else {
            val p = model.getAcceptProb(energyDiff)
            if (Random.nextDouble() < p) {
                model.inverseSpinAt(i)
                model.updateEnergy(energyDiff)
            }
        }

    }

}