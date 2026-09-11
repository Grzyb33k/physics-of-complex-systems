package pl.kamil

import pl.kamil.model.IsingModel2D
import pl.kamil.mcsimulator.Metropolis2D
import java.io.File

fun main() {
    val dirName = "output"
    val directory = File(dirName)
    if (!directory.exists()) { directory.mkdirs() }

    val L = 32
    val N = L * L
    val exchInt = 1.0

    println("--- Start symulacji ---")

    // ==========
    // ZADANIE 1
    // ==========
    println("Zadanie 1")
    File(directory, "zad1_probabilities.csv").printWriter().use { out ->
        out.println("T,spin_i,sum_neighbors,dE,probability")
        val tempsZad1 = doubleArrayOf(1.0, 2.0, 2.5, 4.0)
        val spins = intArrayOf(1, -1)
        val neighborSums = intArrayOf(4, 2, 0, -2, -4)

        for (T in tempsZad1) {
            val dummyModel = IsingModel2D(L, exchInt, temperature = T)
            for (sigma_i in spins) {
                for (sumJ in neighborSums) {
                    val dE = 2.0 * sigma_i * (exchInt * sumJ)
                    val p = dummyModel.getAcceptProb(dE)
                    out.println("%.1f,%d,%d,%.1f,%.2e".format(T, sigma_i, sumJ, dE, p))
                }
            }
        }
    }

    // ==============
    // ZADANIE 2 i 3
    // ==============
    println("Zadanie 2 i 3")
    val tempsZad2 = doubleArrayOf(1.0, 2.0, 2.5, 4.0)
    val maxIterZad2 = 100_000

    for (T in tempsZad2) {
        println("  Symulacja dla T = $T ...")
        val model = IsingModel2D(L, exchInt, temperature = T)
        model.fillSpinsWith(1)
        val sim = Metropolis2D(model)

        File(directory, "zad2_m_vs_t_T_${T}.csv").printWriter().use { out ->
            out.println("t,m")
            for (t in 0..maxIterZad2) {
                val M = model.getTotalMagnetization()
                val m = M / N
                out.println("$t,%.4f".format(m))
                if (t < maxIterZad2) sim.update()
            }
        }


        File(directory, "zad3_grid_T_${T}.csv").printWriter().use { out ->
            out.println("x,y,spin")
            for (y in 0 until L) {
                for (x in 0 until L) {
                    out.println("$x,$y,${model.getSpinAt(x, y)}")
                }
            }
        }
    }

    // ==========
    // ZADANIE 4
    // ==========
    println("Zadanie 4")
    val maxIterZad4 = 100_000
    val tau = 10_000
    val termalizationSteps = maxIterZad4 - tau

    File(directory, "zad4_results.csv").printWriter().use { out ->
        out.println("T,m_avg,chi")


        var currentT = 0.25
        while (currentT <= 4.001) {
            println("  Obliczenia dla T = %.2f".format(currentT))
            val model = IsingModel2D(L, exchInt, temperature = currentT)
            model.fillSpinsWith(1)
            val sim = Metropolis2D(model)


            for (t in 0 until termalizationSteps) {
                sim.update()
            }


            var sumM = 0.0
            var sumM2 = 0.0

            for (t in 0 until tau) {
                sim.update()
                val M = model.getTotalMagnetization()
                sumM += M
                sumM2 += M * M
            }

            val avgM = sumM / tau
            val avgM2 = sumM2 / tau

            val m = Math.abs(avgM) / N

            val chi = (1.0 / currentT) * (1.0 / N) * (avgM2 - avgM * avgM)

            out.println("%.2f,%.6f,%.6f".format(currentT, m, chi))

            currentT += 0.25
        }
    }

}