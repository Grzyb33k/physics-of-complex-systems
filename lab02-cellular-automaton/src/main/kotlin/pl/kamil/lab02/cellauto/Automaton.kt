package org.example.pl.kamil.lab02.cellauto
import kotlin.random.Random
import java.io.File

class Automaton(
    val size: Int = 60,
    private val bornRules: Set<Int>,
    private val surviveRules: Set<Int>,
) {

    private var grid = Array(size) { BooleanArray(size) }
    private var time = 0

    init {
        for (i in 0 until size) {
            for (j in 0 until size) {
                grid[i][j] = Random.nextBoolean()
            }
        }
    }

    private fun isAlive(x: Int, y: Int): Int {
        val px = (x + size) % size
        val py = (y + size) % size
        return if (grid[px][py]) 1 else 0
    }

    private fun countNeighbors(x: Int, y: Int): Int {
        var count = 0
        for (dx in -1..1) {
            for (dy in -1..1) {
                if (dx == 0 && dy == 0) continue
                count += isAlive(x + dx, y + dy)
            }
        }
        return count
    }

    fun evolve() {
        val nextGrid = Array(size) { BooleanArray(size) }

        for (x in 0 until size) {
            for (y in 0 until size) {
                val neighbors = countNeighbors(x, y)
                val wasAlive = grid[x][y]

                nextGrid[x][y] = if (wasAlive) {
                    neighbors in surviveRules
                }
                else {
                    neighbors in bornRules
                }
            }
        }
        grid = nextGrid
        time++
    }

    fun getTime() = time

    fun display() {
        for (row in grid) {
            println(row.joinToString("") {if (it) "x" else "_"})
        }
    }

    fun saveToCsv(run: Int) {
        val dirName = "output"

        val directory = File(dirName)

        if (!directory.exists()) {
            directory.mkdirs()
        }

        val fileName = "${run}evolution_run_t${getTime()}.csv"
        val file = File(directory, fileName)
        file.printWriter().use { out ->
            grid.forEach { row ->
                out.println(row.joinToString(",") {if (it) "1" else "0"})
            }
        }
        println("Saved to: $fileName")
    }

}