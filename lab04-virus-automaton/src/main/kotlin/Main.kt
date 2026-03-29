package pl.kamil

import pl.kamil.automaton.VirusAutomaton
import pl.kamil.utils.Engine

import java.io.File

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    val ut = Engine()

    val size = 60
    val alpha = DoubleArray(9) {x -> (x + 1) / 10.0}
    val b = 0.1
    val n = 200

    for (i in 0 until alpha.size) {
        ut.runEpidemicAndSave(
            size,
            alpha[i],
            b,
            n
        )
    }
}