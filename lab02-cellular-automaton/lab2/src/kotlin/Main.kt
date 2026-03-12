package org.example
import org.example.pl.kamil.lab02.cellauto.Automaton

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    val size = 60
    val bornRules = setOf(4, 6, 7, 8)
    val surviveRules = setOf(3, 5, 6, 7, 8)
    val snapshots = setOf(0, 1, 2, 5, 10, 20, 50, 100)

    val runs = 3

    for (run in 0 until runs) {
        val ca = Automaton(
            size = size,
            bornRules = bornRules,
            surviveRules = surviveRules,
        )

        for (i in 0 .. snapshots.maxOrNull()!!) {
            if (ca.getTime() in snapshots) {
                ca.saveToCsv(run + 1)
            }
            ca.evolve()
        }
    }

}