package pl.kamil

import pl.kamil.model.LogisticMap
import pl.kamil.engine.SimulationEngine
import pl.kamil.exporter.CsvExporter

import java.io.File


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    val map = LogisticMap()

    val sim = SimulationEngine(map)

    // TASK 1 //

    val x0Arr = DoubleArray(9) { 0.1 * (it + 1)}

    val r = 2.0

    var n = 50

    val resultsTask1 = mutableMapOf<Double, DoubleArray>()

    for (x0 in x0Arr) {
        resultsTask1[x0] = sim.runSimulation(x0, r, n)
    }

    CsvExporter.exportMultipleRowData(resultsTask1, "task1.csv")

    val x0 = 0.5
    val rArr = doubleArrayOf(1.0, 2.0, 3.0, 3.5, 3.55, 3.6)

    val resultsTask2 = mutableMapOf<Double, DoubleArray>()

    n = 100

    for (r in rArr) {
        resultsTask2[r] = sim.runSimulation(x0, r, n)
    }

    CsvExporter.exportMultipleRowData(resultsTask2, "task2.csv")


    val rArr2 = (1000..4000).map {it / 1000.0}.toDoubleArray()

    rArr2.forEach {println(it)}

    val resultsTask3 = mutableMapOf<Double, DoubleArray>()

    n = 10_000

    for(r in rArr2) {
        resultsTask3[r] = sim.runSimulation(x0, r, n)
    }

    CsvExporter.exportMultipleRowData(resultsTask3, "task3.csv", 4)

}