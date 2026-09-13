package pl.kamil
import pl.kamil.model.SandpileModel
import kotlin.random.Random

import java.io.File
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    val dirName = "output"
    val directory = File(dirName)
    if (!directory.exists()) { directory.mkdirs() }

    ///////////////
    // ZADANIE 1 //
    ///////////////

    var sm = SandpileModel(10)

    sm.fillWithRandom()

    File(directory, "zad1.txt").printWriter().use { out ->


        sm.changeOutputStream(out)

        out.println("Siatka po wypełnieniu losowymi wartościami: ")

        sm.printGrid()

        var s = 0
        var it = 1

        sm.shouldPrintResults(true)

        while (s < 8) {
            out.println("---------- ITERACJA $it ----------")
            sm.step()
            s = sm.getAvalancheCount()
            out.println("Rozmiar lawiny: $s")
            it++
        }

    }

    ///////////////////
    // ZADANIE 2 i 3 //
    ///////////////////

    sm = SandpileModel(20)

    sm.fillWithRandom()
    sm.shouldPrintResults(false)

    val maxIt = 1e5.toInt()

    val data = mutableListOf<Int>()

    var s = 0

    File(directory, "zad2.txt").printWriter().use { out ->

        for (i in 0..maxIt) {
            sm.step()
            s = sm.getAvalancheCount()
//            data.add(s)
            if ( s > 0 ) {
                out.println(s)
            }
        }

    }


}