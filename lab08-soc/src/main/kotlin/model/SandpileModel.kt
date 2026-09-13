package pl.kamil.model

import kotlin.random.Random
import java.io.PrintWriter
import java.io.File

class SandpileModel(val n: Int) {
    var grid = Array(n) { IntArray(n) {0} }
    var s = 0
    var printResults = true
    private var out: PrintWriter = PrintWriter(System.out, true)


    fun changeOutputStream(newOut: PrintWriter) {
        this.out = newOut
    }

    fun fillWithRandom() {
        grid = Array(n) {IntArray(n) { Random.nextInt(0, 4) } }
    }

    fun addWithRandom() {
        val x = Random.nextInt(0, grid.size)
        val y = Random.nextInt(0, grid.size)

        addGrain(x, y)

    }

    fun shouldPrintResults(print: Boolean) {
        printResults = print
    }

    private fun isInGrid(x: Int, y: Int): Boolean {
        return x >= 0 && y >= 0 && x < n && y < n
    }

    fun removeGrains(x: Int, y: Int, n: Int) {
        grid[x][y] -= n
    }

    fun addGrain(x: Int, y: Int) {

        if ( isInGrid(x, y) ) {

            grid[x][y] += 1

        }

    }

    private fun avalanche(x: Int, y: Int) {

        addGrain(x - 1, y)
        addGrain(x + 1, y)
        addGrain(x, y - 1)
        addGrain(x, y + 1)

        removeGrains(x, y, 4)

    }

    private fun topple(x: Int, y: Int) {
        val points = MutableList(1) { Pair(x, y) }

        if (printResults) {
            printGrid()
        }

        while (points.isNotEmpty()) {
            val p = points.removeFirst()


            if(grid[p.first][p.second] >= 4) {
                s += 4

                if (printResults) {

                    out.println("Lawina dla punktu: (${p.second + 1}, ${p.first + 1})")

                }

                avalanche(p.first, p.second)

                if (printResults) {
                    printGrid()
                }

                var a = p.first + 1
                var b = p.second

                if ( isInGrid(a, b) ) {
                    points.add(Pair(a, b))
                }

                a = p.first - 1
                b = p.second

                if ( isInGrid(a, b) ) {
                    points.add(Pair(a, b))
                }

                a = p.first
                b = p.second + 1

                if ( isInGrid(a, b) ) {
                    points.add(Pair(a, b))
                }

                a = p.first
                b = p.second - 1

                if ( isInGrid(a, b) ) {
                    points.add(Pair(a, b))
                }

                if (grid[p.first][p.second] >= 4) {
                    points.add(Pair(p.first, p.second))
                }

            }

        }

    }

    fun printGrid() {

        out.println("#".repeat(grid.size * 3))

        for (row in grid) {
            val rowString = row.joinToString(" ") {x -> if (x == 0) ".".padStart(2) else x.toString().padStart(2)}

            out.println(rowString)
        }

        out.println("#".repeat(grid.size * 3))
    }

    fun getAvalancheCount() = s

    fun step() {

        s = 0

        val x = Random.nextInt(0, grid.size)
        val y = Random.nextInt(0, grid.size)

        addGrain(x, y)

        topple(x, y)

//        println("Avalanche counts: $s")

//        printGrid()



    }

}