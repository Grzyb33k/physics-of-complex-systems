package pl.kamil.tasks
import pl.kamil.graph.Graph
import kotlin.math.abs

object Tasks {

    fun task1(graph: Graph, epsilon: Double) {

        val g = graph
        val nOfNodes = g.nOfNodes

        var p = DoubleArray(nOfNodes) { 1.0 / nOfNodes }

        var diff = 1.0
        var iterations = 0

        while (diff > epsilon) {
            val newP = DoubleArray(nOfNodes) { 0.0 }

            for (i in 0 until nOfNodes) {
                val neighbors = g.getAdjacencyList()[i]

                if (neighbors.isNotEmpty()) {
                    for (neighbor in neighbors) {
                        newP[neighbor] += p[i] / neighbors.size.toDouble()
                    }
                }
                else {
                    for (node in 0 until nOfNodes) {
                        newP[node] += p[i] / nOfNodes
                    }
                }


            }

            diff = 0.0
            for (i in 0 until nOfNodes) {
                diff += abs(newP[i] - p[i])
            }

            p = newP
            iterations++
        }

        println("Osiągnięto zbieżność po $iterations iteracjach (eps = $epsilon)")
        p.forEachIndexed { index, d ->  println("$index: %.3f".format(d)) }
        println("Suma: ${p.sum()}")
    }

    fun task2(graph: Graph, epsilon: Double, dumpingFactor: Double) {
        val g = graph
        val nOfNodes = g.nOfNodes

        var p = DoubleArray(nOfNodes) { 1.0 / nOfNodes }

        var diff = 1.0
        var iterations = 0

        while (diff > epsilon) {
            val newP = DoubleArray(nOfNodes) { 0.0 }

            for (i in 0 until nOfNodes) {
                val neighbors = g.getAdjacencyList()[i]

                if (neighbors.isEmpty()) {
                    for (node in 0 until nOfNodes) {
                        newP[node] += p[i] / nOfNodes
                    }
                }
                else {
                    for (node in 0 until nOfNodes) {
                        if (node in neighbors) {
                            newP[node] += p[i] / neighbors.size.toDouble() * dumpingFactor
                        }
                        newP[node] += p[i] / nOfNodes * (1 - dumpingFactor)
                    }
                }


            }

            diff = 0.0
            for (i in 0 until nOfNodes) {
                diff += abs(newP[i] - p[i])
            }

            p = newP
            iterations++
        }

        println("Osiągnięto zbieżność po $iterations iteracjach (eps = $epsilon)")
        p.forEachIndexed { index, d ->  println("$index: %.3f".format(d)) }
        println("Suma: ${p.sum()}")
    }

    fun task3(graph: Graph, epsilon: Double) {
        val g = graph
        val nOfNodes = g.nOfNodes

        var diff = 1.0

        var v = DoubleArray(nOfNodes) { 1.0 / nOfNodes }

        var P = g.getTransitionMatrix()

        var iterations = 0

        while (diff > epsilon * nOfNodes) {
            val vNew = DoubleArray(nOfNodes) { 0.0 }

            for (i in 0 until nOfNodes) {
                val vi = v[i]

                for (j in 0 until nOfNodes) {
                    vNew[j] += vi * P[i][j]
                }
            }

            diff = 0.0
            for (i in 0 until nOfNodes) {
                diff += abs(vNew[i] - v[i])
            }

            v = vNew
            iterations++
        }

        println("Osiągnięto zbieżność po $iterations iteracjach (eps = $epsilon)")
        v.forEachIndexed { index, d ->  println("$index: %.3f".format(d)) }
        println("Suma: ${v.sum()}")

    }

    fun task4(graph: Graph, epsilon: Double, dumpingFactor: Double) {
        val g = graph
        val nOfNodes = g.nOfNodes

        var diff = 1.0

        var v = DoubleArray(nOfNodes) { 1.0 / nOfNodes }

        val A = g.getTransitionMatrix()
        val M = Array(nOfNodes) { DoubleArray(nOfNodes) }

        for (i in 0 until nOfNodes) {
            for (j in 0 until nOfNodes) {
                M[i][j] = dumpingFactor * A[i][j] + (1 - dumpingFactor) * (1.0 / nOfNodes)
            }
        }

        var iterations = 0

        while (diff > nOfNodes * epsilon) {
            val vNew = DoubleArray(nOfNodes) { 0.0 }

            for (i in 0 until nOfNodes) {
                val vi = v[i]

                for (j in 0 until nOfNodes) {
                    vNew[j] += vi * M[i][j]
                }
            }

            diff = 0.0
            for (i in 0 until nOfNodes) {
                diff += abs(vNew[i] - v[i])
            }

            v = vNew
            iterations++
        }

        println("Osiągnięto zbieżność po $iterations iteracjach (eps = $epsilon)")
        v.forEachIndexed { index, d ->  println("$index: %.3f".format(d)) }
        println("Suma: ${v.sum()}")
    }

}