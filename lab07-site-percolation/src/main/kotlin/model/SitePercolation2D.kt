package pl.kamil.model

import kotlin.random.Random

class SitePercolation2D(val L: Int, val p: Double) {
    val grid = Array(L) { BooleanArray(L) { Random.nextDouble() < p } }
    val labels = Array(L) { IntArray(L) { 0 } }
}