package pl.kamil

import pl.kamil.graph.BAGraph
import pl.kamil.graph.BAGraphFast
import java.io.File

fun main() {

    // ZAD 1 //

    val g1 = BAGraph(100, 5, 4)

    val dirName = "output"
    val directory = File(dirName)
    if (!directory.exists()) { directory.mkdirs() }


    g1.saveEdgeList("output/bag_edges_zad1.csv")


    // ZAD 2 //

    val g2 = BAGraph(1e5.toInt(), 5, 4)

    g2.saveDegreeList("output/bag_degrees_zad2.csv")

    // ZAD 3 //

    val g3 = BAGraph(100, 1, 1)

    g3.saveEdgeList("output/bag_edges_zad3.csv")
    g3.saveDegreeList("output/bag_degrees_zad3.csv")

    var step = 0
    var isConnected = true

    while (isConnected) {
        isConnected = g3.checkIntegrity(start = 0)
        println("isConnected = $isConnected, step = $step")

        val edges = g3.getEdgeListUnique()
        if (edges.isEmpty()) {
            println("No edges found")
            break
        }

        val (u, v) = edges.random()
        g3.removeEdge(u, v)
        println("Removed edge $u: $v")

        step++
    }

}