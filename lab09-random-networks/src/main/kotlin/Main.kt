package pl.kamil

import pl.kamil.graph.RandomGraph

import java.io.File
import kotlin.math.ln
import kotlin.math.pow

fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 2..n) {
        result *= i
    }
    return result
}

fun main() {

    val dirName = "output"
    val directory = File(dirName)
    if (!directory.exists()) { directory.mkdirs() }

    val n = 100
    val alphas = listOf(4.0, 10.0)

    val nIt = 100

    for (alpha in alphas) {

        File(directory, "zad4_alpha_$alpha.csv").printWriter().use { out ->

            println("### Results for alpha:  $alpha ###")

            val expectedL: Double = alpha * (n - 1) / 2
            val expectedK: Double = 2 * expectedL / n
            val expectedD: Double = ln(n.toDouble()) / ln(expectedK)
            val expectedT: Double = factorial(n) / (factorial(3) * factorial(n - 3)) * (alpha / n).pow(3)

            var totalNOfEdges = 0.0
            var totalMeanNodes = 0.0
            var totalMeanDistance = 0.0
            var totalMeanTriangles = 0.0

            for (i in 0 until nIt) {

                val rg = RandomGraph(n, alpha)

                totalNOfEdges += rg.getNumberOfEdges().toDouble() / nIt
                totalMeanNodes += rg.getMeanNodesDegree().toDouble() / nIt
                totalMeanDistance += rg.getAverageDistance() / nIt
                totalMeanTriangles += rg.countTriangles().toDouble() / nIt

                rg.getListOfDegrees().forEach { degree -> out.println(degree)}

            }

            println("Theoretical mean n of edges: %.2f".format(expectedL))
            println("Mean of $nIt iterations: %.2f".format(totalNOfEdges))
            println()
            println("Theoretical expected nodes degree: %.2f".format(expectedK))
            println("Mean of $nIt iterations: %.2f".format(totalMeanNodes))
            println()
            println("Theoretical expected mean distance: %.2f".format(expectedD))
            println("Mean of $nIt iterations: %.2f".format(totalMeanDistance))
            println()
            println("Theoretical expected triangle count: %.2f".format(expectedT))
            println("Mean of $nIt iterations: %.2f".format(totalMeanTriangles))

            println("-".repeat(50))
            println()


        }


    }

}