package pl.kamil.mcsimulator

import pl.kamil.model.SitePercolation2D

class HoshenKopelman2D(val model: SitePercolation2D) {
    var percolates = false
        private set

    fun run() {
        val L = model.L
        val maxLabels = (L * L) / 2 + 2
        val parent = IntArray(maxLabels) { it }

        fun find(x: Int): Int {
            var y = x
            while (parent[y] != y) y = parent[y]

            var curr = x
            while (curr != y) {
                val nxt = parent[curr]
                parent[curr] = y
                curr = nxt
            }
            return y
        }

        fun union(x: Int, y: Int) {
            val rootX = find(x)
            val rootY = find(y)
            if (rootX != rootY) {
                if (rootX < rootY) parent[rootY] = rootX
                else parent[rootX] = rootY
            }
        }

        var largestLabel = 0

        for (y in 0 until L) {
            for (x in 0 until L) {
                if (model.grid[y][x]) {
                    val up = if (y > 0) model.labels[y - 1][x] else 0
                    val left = if (x > 0) model.labels[y][x - 1] else 0

                    if (up == 0 && left == 0) {
                        largestLabel++
                        parent[largestLabel] = largestLabel
                        model.labels[y][x] = largestLabel
                    } else if (up > 0 && left == 0) {
                        model.labels[y][x] = up
                    } else if (up == 0 && left > 0) {
                        model.labels[y][x] = left
                    } else {
                        union(up, left)
                        model.labels[y][x] = find(up)
                    }
                }
            }
        }

        val topLabels = mutableSetOf<Int>()
        val bottomLabels = mutableSetOf<Int>()

        for (y in 0 until L) {
            for (x in 0 until L) {
                if (model.grid[y][x]) {
                    val rootLabel = find(model.labels[y][x])
                    model.labels[y][x] = rootLabel

                    if (y == 0) topLabels.add(rootLabel)
                    if (y == L - 1) bottomLabels.add(rootLabel)
                }
            }
        }

        percolates = topLabels.intersect(bottomLabels).isNotEmpty()
    }
}