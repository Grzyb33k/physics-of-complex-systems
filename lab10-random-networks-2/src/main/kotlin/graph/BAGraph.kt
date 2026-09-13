package pl.kamil.graph
import java.io.File

class BAGraph(
    val n: Int,
    val m0: Int,
    val m: Int,
) {

    val adj: MutableList<MutableSet<Int>> = MutableList(n) { mutableSetOf() }

    private val degree = IntArray(n)

    init {

        require(m <= m0)
        require(n > m0)

        for (u in 0 until m0) {
            for (v in u + 1 until m0) {
                addEdge(u, v)
            }
        }

        for (i in m0 until n) {
            val targets = mutableSetOf<Int>()
            if(i % 10000 == 0) {
                println("Done $i out of $n")
            }

            while (targets.size < m) {
                val t = pickNodeByDegree(i)
                targets.add(t)
            }

            targets.forEach { t -> addEdge(i, t) }

        }


    }

    fun addEdge(u: Int, v: Int) {
        if (u == v) return
        if (adj[u].add(v)) {
            adj[v].add(u)
            degree[u]++
            degree[v]++
        }
    }

    fun removeEdge(u: Int, v: Int) {
        adj[u].remove(v)
        adj[v].remove(u)
        degree[u]--
        degree[v]--
    }

    private fun pickNodeByDegree(uptoExclusive: Int): Int {
        val total = (0 until uptoExclusive).sumOf { degree[it] }

        if (total == 0) {
            return kotlin.random.Random.nextInt(uptoExclusive)
        }

        val r = kotlin.random.Random.nextInt(total)

        var acc = 0
        for (i in 0 until uptoExclusive) {
            acc += degree[i]
            if (r < acc) return i
        }
        return uptoExclusive - 1
    }

    fun saveEdgeList(path: String) {
        File(path).printWriter().use { out ->
            out.println("source,target")
            for (u in adj.indices) {
                for (v in adj[u]) {
                    if (u < v) {
                        out.println("$u,$v")
                    }
                }
            }
        }
    }

    fun getEdgeListUnique(): List<Pair<Int, Int>> {
        val edges = mutableListOf<Pair<Int, Int>>()
        for (u in adj.indices) {
            for (v in adj[u]) {
                if (u < v) {
                    edges.add(Pair(u, v))
                }
            }
        }
        return edges
    }

    fun getDegreeList() = degree

    fun saveDegreeList(path: String) {
        File(path).printWriter().use { out ->
            out.println("node,degree")
            for (i in 0 until degree.size) {
                out.println("$i,${degree[i]}")
            }
        }
    }

    fun checkIntegrity(start: Int): Boolean {
        val visited = BooleanArray(n)

        fun dfs(u: Int) {
            visited[u] = true
            for (v in adj[u]) {
                if (!visited[v]) dfs(v)
            }
        }

        dfs(start)
        return visited.all { it }
    }

}