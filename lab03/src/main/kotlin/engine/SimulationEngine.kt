package pl.kamil.engine
import pl.kamil.model.LogisticMap

import java.io.File

class SimulationEngine(
    private val map: LogisticMap,
) {


    fun runSimulation(x0: Double, r: Double, n: Int): DoubleArray {
        val result = DoubleArray(n)

        var xn = x0

        for (i in 0 until n) {
            val next = map.calculateNext(xn, r)
            result[i] = next
            xn = next
        }

        return result
    }

}