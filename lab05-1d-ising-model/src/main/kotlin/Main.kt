package pl.kamil

import pl.kamil.model.IsingModel1D
import pl.kamil.mcsimulator.Metropolis

import java.io.File

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    val dirName = "output"
    val directory = File(dirName)
    var fileName = "probabilities.csv"
    var file = File(directory, fileName)

    if (!directory.exists()) {
        directory.mkdirs()
    }

    val nSpins = 1000.toInt()
    val exchangeInteger = 1.0
    val temperature = 0.5

    var model = IsingModel1D(nSpins, exchangeInteger, temperature = temperature)

    val index = nSpins / 2

    val previousIndex = (index - 1 + nSpins) % nSpins
    val nextIndex = (index + 1 + nSpins) % nSpins

    val betas = doubleArrayOf(0.5, 1.0, 1.5, 4.0)

    file.printWriter().use {

        out -> out.println("beta,left,center,right,p")

        betas.forEach {

            val temperature = 1 / it

            model.changeTemperature(temperature)

//            println("Temperature: %.2f".format(temperature))

            for (i in arrayOf(-1, 1)) {
                for (j in arrayOf(-1, 1)) {
                    for (k in arrayOf(-1, 1)) {

                        model.setSpinAt(index, i)
                        model.setSpinAt(previousIndex, j)
                        model.setSpinAt(nextIndex, k)

                        val spinBefore = model.getSpinAt(index)

                        val energyDiff = model.calcEnergyDifference(index)

                        val p = model.getAcceptProb(energyDiff)

//                        println("[${model.getSpinAt(previousIndex)}, " +
//                                "${model.getSpinAt(index)}, " +
//                                "${model.getSpinAt(nextIndex)}] : p = %.3f".format(p))

                        out.println("%.2f,".format(it) +
                                "${model.getSpinAt(previousIndex)}," +
                                "${model.getSpinAt(index)}," +
                                "${model.getSpinAt(nextIndex)},%.5f".format(p))


                    }
                }
            }

        }



    }



    // ZADANIE 2 //

    var nSteps = 1e4.toInt()

    fileName = "task2_random.csv"
    file = File(directory, fileName)


    file.printWriter().use {

        out -> out.println("beta,t,density")

        betas.forEach {

            var t = 0

            val temperature = 1 / it

            model = IsingModel1D(nSpins, exchangeInteger, temperature = temperature)
            val simulation = Metropolis(model)

            for (i in 0 until nSteps) {

                val energyDens = model.getTotalEnergyDensity()

                out.println("%.2f,$t,".format(it) + "%.2f".format(energyDens))

                simulation.update()

                t++

            }


        }



    }

    fileName = "task2_uniform.csv"
    file = File(directory, fileName)


    file.printWriter().use {

            out -> out.println("beta,t,density")

        betas.forEach {

            var t = 0

            val temperature = 1 / it

            model = IsingModel1D(nSpins, exchangeInteger, temperature = temperature)

            model.fillSpinsWith(1)

            val simulation = Metropolis(model)

            for (i in 0 until nSteps) {

                val energyDens = model.getTotalEnergyDensity()

                out.println("%.2f,$t,".format(it) + "%.2f".format(energyDens))

                simulation.update()

                t++

            }


        }



    }

    nSteps = 1e5.toInt()

}