package pl.kamil.graph
import kotlin.random.Random


class RandomGraph(
    val n: Int,
    val alpha: Double,
) {

    var graph = Array(n) {BooleanArray(n) }

    val p = alpha / n

    var nOfEdges = 0

    val inf = 999

    init {
        for (i in 0 until n) {
            for (j in 0..i) {
                if (i != j) {
                    if (Random.nextDouble() < p) {
                        graph[i][j] = true
                        graph[j][i] = true
                    }
                }
            }
        }

        nOfEdges = graph.sumOf { row -> row.count { it } } / 2
    }


    fun returnAdjacencyMatrix() = graph

    fun getNumberOfEdges() = nOfEdges

    fun printAdjacencyMatrix() {
        returnAdjacencyMatrix().forEach { it.forEach { print("${if (it) 1 else 0} ") }; println() }
    }

    fun getMeanNodesDegree() = 2 * nOfEdges / n

    fun getDistanceMatrix(): Array<IntArray> {

        val distanceMatrix = Array(n) { IntArray(n)}

        for (i in 0 until n) {
            for (j in 0 until n) {
                if (i == j) {
                    distanceMatrix[i][j] = 0
                }
                else {
                    if (graph[i][j]) {
                        distanceMatrix[i][j] = 1
                    }
                    else {
                        distanceMatrix[i][j] = inf
                    }
                }
            }
        }

        for (k in 0 until n) {
            for (i in 0 until n) {
                for (j in 0 until n) {
                    distanceMatrix[i][j] = minOf(distanceMatrix[i][j], distanceMatrix[i][k] + distanceMatrix[k][j])
                }
            }
        }

        return distanceMatrix

    }

    fun getAverageDistance(): Double {
        val d = getDistanceMatrix()
        val distances = d.flatMapIndexed {i, row ->
            row.filterIndexed {j, dist ->
                i != j && dist < inf
            }
        }

        val avgDist = distances.average().takeIf { !it.isNaN() } ?: 0.0

        return avgDist
    }

    fun getListOfDegrees(): MutableList<Int> {
        val arr = mutableListOf<Int>()
        graph.forEach { row -> arr.add(row.count{ it })}
        return arr
    }

    fun countTriangles(): Int {
        var count = 0

        for (i in 0 until n) {
            for (j in i + 1 until n) {
                if (graph[i][j]) {
                    for (k in j + 1 until n) {
                        if (graph[j][k] && graph[k][i]) {
                            count++
                        }
                    }
                }
            }
        }

        return count
    }
}