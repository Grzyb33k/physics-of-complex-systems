package pl.kamil.automaton

import kotlin.math.pow
import kotlin.random.Random

enum class State {S, I, R}  // S - susceptible
                            // I - infected
                            // R - recovered

class VirusAutomaton(
    val size: Int = 60,
    val alpha: Double,
    val b: Double,
) {
    private var nSusceptible = 0
    private var nInfected = 0
    private var nRecovered = 0

    private var grid = Array(size) {Array(size) {State.S} }
    private var time = 0

    private fun countInfectedNeighbors(x: Int, y: Int): Int {
        var count = 0

        for (dx in -1..1) {
            for (dy in -1..1) {
                if (dx == 0 && dy == 0) continue

                val px = (x + dx + size) % size
                val py = (y + dy + size) % size

                if (grid[px][py] == State.I) count++
            }
        }
        return count
    }

    fun spreadInfection(n: Int) {

        for (i in 0 until n) {

            val x = Random.nextInt(0, size)
            val y = Random.nextInt(0, size)

            grid[x][y] = State.I

        }

        updateCount()

        while (nInfected < n) {
            val x = Random.nextInt(0, size)
            val y = Random.nextInt(0, size)

            grid[x][y] = State.I

            updateCount()
        }
    }

    fun placePatientZero(x: Int, y: Int) {
        grid[x][y] = State.I
    }

    fun evolve() {
        val nextGrid = Array(size) {Array(size) {State.S} }

        for(x in 0 until size) {
            for (y in 0 until size) {

                val infectedNeighbors = countInfectedNeighbors(x, y)

                val currentState = grid[x][y]

                nextGrid[x][y] = when (currentState) {

                    State.S -> {
                        if (infectedNeighbors > 0 ) {
                            val p = 1 - (1 - alpha).pow(infectedNeighbors.toDouble())
                            if (Random.nextDouble() < p) {
                                State.I
                            } else {
                                State.S
                            }
                        }else {
                            State.S
                        }
                    }

                    State.I -> {
                        if (Random.nextDouble() < b) {
                            State.R
                        } else {
                            State.I
                        }

                    }
                    State.R -> {
                        State.R
                    }
                }

            }
        }

        grid = nextGrid

        updateCount()
    }

    private fun updateCount() {
        nSusceptible = 0
        nInfected = 0
        nRecovered = 0

        grid.forEach { it.forEach { state ->
            if (state == State.S) {nSusceptible++ }
            if (state == State.I) {nInfected++ }
            if (state == State.R) {nRecovered++ }
        } }
    }

    fun getStateS(): Int {
        return nSusceptible
    }

    fun getStateI(): Int {
        return nInfected
    }

    fun getStateR(): Int {
        return nRecovered
    }

    fun getGrid(): Array<Array<State>> {
        return grid
    }

    fun getStringGrid(): Array<Array<String>> {

        var stringGrid = Array(size) {Array(size) {"S"} }

        for(x in 0 until size) {
            for(y in 0 until size) {

                val currentState = grid[x][y]

                when (currentState) {
                    State.S -> {

                    }

                    State.I -> {
                        stringGrid[x][y] = "I"
                    }

                    State.R -> {
                        stringGrid[x][y] = "R"
                    }
                }

            }
        }

        return stringGrid
    }

    fun getValueGrid(): Array<Array<Int>> {

        var valueGrid = Array(size) {Array(size) {-1} }

        for(x in 0 until size) {
            for(y in 0 until size) {

                val currentState = grid[x][y]

                when (currentState) {
                    State.S -> {

                    }

                    State.I -> {
                        valueGrid[x][y] = 0
                    }

                    State.R -> {
                        valueGrid[x][y] = 1
                    }
                }

            }
        }

        return valueGrid

    }

}