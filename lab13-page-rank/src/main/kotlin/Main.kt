package pl.kamil
import pl.kamil.graph.Graph
import pl.kamil.tasks.Tasks


fun main() {

    val nOfNodes = 4
    val epsilon = 1e-6
    val dumpingFactor = 0.85

    val g1edges = listOf<Pair<Int, Int>>(
        Pair(0, 1),
        Pair(0, 2),
        Pair(0, 3),
        Pair(1, 0),
        Pair(2, 0),
        Pair(2, 1),
        Pair(3, 1),
        Pair(3, 2),
    )

    val g2edges = listOf<Pair<Int, Int>>(
        Pair(0, 1),
        Pair(1, 0),
        Pair(2, 1),
        Pair(2, 3),
        Pair(3, 0),
        Pair(3, 2),
    )

    val g3edges = listOf<Pair<Int, Int>>(
        Pair(0, 1),
        Pair(2, 1),
        Pair(2, 3),
        Pair(3, 0),
        Pair(3, 2),
    )

    val g1 = Graph(nOfNodes, g1edges)
    val g2 = Graph(nOfNodes, g2edges)
    val g3 = Graph(nOfNodes, g3edges)

    val graphs = listOf<Graph>(g1, g2, g3)

    graphs.forEachIndexed { index, graph ->

        println("========= WYNIKI DLA ${index + 1} GRAFU TESTOWEGO =========")

        println("Błądzenie losowe bez teleportacji")
        Tasks.task1(graph, epsilon)

        println("Błądzenie losowe z teleportacją")
        Tasks.task2(graph, epsilon, dumpingFactor)

        println("PageRank bez teleportacji")
        Tasks.task3(graph, epsilon)

        println("PageRank z teleportacją")
        Tasks.task4(graph, epsilon, dumpingFactor)

    }

}