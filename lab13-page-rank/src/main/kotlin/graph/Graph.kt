package pl.kamil.graph

class Graph(
    val nOfNodes: Int,
    edges: List<Pair<Int, Int>>,
) {

    private val adjacencyList: Array<List<Int>> = Array(nOfNodes) { emptyList() }

    init {
        val groupedEdges = edges.groupBy({ it.first }, { it.second })
        for (i in 0 until nOfNodes) {
            adjacencyList[i] = groupedEdges[i] ?: emptyList()
        }
    }

    fun getAdjacencyList() = adjacencyList

    fun getTransitionMatrix(): Array<DoubleArray> {
        val pMatrix = Array(nOfNodes) { DoubleArray(nOfNodes) }
        for (i in 0 until nOfNodes) {
            val neighbors = adjacencyList[i]
            if (neighbors.isNotEmpty()) {
                val probability = 1.0 / neighbors.size
                for (j in neighbors) {
                    pMatrix[i][j] = probability
                }
            }
            else {
                for (j in 0 until nOfNodes) {
                    pMatrix[i][j] = 1.0 / nOfNodes
                }
            }
        }
        return pMatrix
    }

}