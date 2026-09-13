package pl.kamil.graph

import java.io.File
import kotlin.random.Random

class BAGraphFast(
    private val n: Int,
    private val m0: Int,
    private val m: Int,
) {
    private val degree = IntArray(n)
    private val urn = mutableListOf<Int>()

    init {
        require(m <= m0)
        require(n > m0)

        // Initial complete graph on m0 nodes.
        for (u in 0 until m0) {
            for (v in u + 1 until m0) {
                addEdgeByDegreeOnly(u, v)
            }
        }

        for (newNode in m0 until n) {
            if (newNode % 100000 == 0) {
                println("Fast BA progress: $newNode / $n")
            }

            val targets = mutableSetOf<Int>()
            while (targets.size < m) {
                val t = urn[Random.nextInt(urn.size)]
                targets.add(t)
            }

            for (t in targets) {
                addEdgeByDegreeOnly(newNode, t)
            }
        }
    }

    private fun addEdgeByDegreeOnly(u: Int, v: Int) {
        if (u == v) return
        degree[u]++
        degree[v]++
        urn.add(u)
        urn.add(v)
    }

    fun getDegreeList(): IntArray = degree

    fun saveDegreeList(path: String) {
        File(path).printWriter().use { out ->
            out.println("node,degree")
            degree.forEachIndexed { node, deg ->
                out.println("$node,$deg")
            }
        }
    }
}
