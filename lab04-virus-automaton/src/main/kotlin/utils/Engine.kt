package pl.kamil.utils

import pl.kamil.automaton.VirusAutomaton
import java.io.File

class Engine {
    private var dirName = "output"
    private var directory = File(dirName)
    private var fileName = "epidemic_evolution.csv"
    private var file = File(directory, fileName)

    private fun filePrepare(newFileName: String) {
        dirName = "output"
        directory = File(dirName)
        fileName = newFileName
        file = File(directory, fileName)

        if (!directory.exists()) {
            directory.mkdirs()
        }
    }

    fun runEpidemicAndSave(size: Int, alpha: Double, b: Double, n: Int) {

        filePrepare(
            "epidemic_evolution_$alpha.csv"
        )

        val va = VirusAutomaton(
            size,
            alpha,
            b,
        )

//        va.spreadInfection(1)

        va.placePatientZero(x = size / 2, y = size / 2)

        file.printWriter().use {
                out -> out.println("i,x,y,state,value")

            for (i in 0 until n) {

                val nS = va.getStateS()
                val nI = va.getStateI()
                val nR = va.getStateR()

//                println("s = $nS, i = $nI, r = $nR")

                val actualGrid = va.getStringGrid()
                val actualValueGrid = va.getValueGrid()

                for (x in 0 until size) {
                    for (y in 0 until size) {
                        out.println("$i,$x,$y,${actualGrid[x][y]},${actualValueGrid[x][y]}")
                    }
                }

                va.evolve()
            }

        }

        println("Saved to: ${file.absolutePath}")

    }
}