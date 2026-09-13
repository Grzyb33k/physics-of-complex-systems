package pl.kamil

import pl.kamil.model.SitePercolation2D
import pl.kamil.mcsimulator.HoshenKopelman2D
import java.io.File
import java.util.Locale

fun main() {
    val dirName = "output"
    val directory = File(dirName)
    if (!directory.exists()) { directory.mkdirs() }

    // ZADANIE 1 //

    val probsZad1 = doubleArrayOf(0.4, 0.6, 0.8)
    val L_zad1 = 16

    File(directory, "zad1.txt").printWriter().use { out ->

        for (p in probsZad1) {
            val model = SitePercolation2D(L_zad1, p)
            val hk = HoshenKopelman2D(model)
            hk.run()

            out.println("# p= %.2f".format(Locale.US, p))
            out.println("grid:")
            for (y in 0 until L_zad1) {
                for (x in 0 until L_zad1) {
                    out.print(String.format("%3s", if (model.grid[y][x]) "X" else "0"))
                }
                out.println()
            }
            out.println("labels:")
            for (y in 0 until L_zad1) {
                for (x in 0 until L_zad1) {
                    out.print(String.format("%3d", model.labels[y][x]))
                }
                out.println()
            }
            out.println(if (hk.percolates) "YES" else "NO")
            out.println("###########################################")
        }

    }

    // ZADANIE 2 i 3 //

    println("Zadanie 2 i 3")
    val sizes = intArrayOf(16, 32, 64, 128)
    val R = 1000


    File(directory, "zad23.csv").printWriter().use { out ->
        out.println("p,W,L")

        for (L in sizes) {

            println("Symulacja dla L = $L")

            for (i in 0..40) {
                val p = 0.4 + i * 0.01
                var percolationCount = 0

                for (r in 0 until R) {
                    val model = SitePercolation2D(L, p)
                    val hk = HoshenKopelman2D(model)
                    hk.run()
                    if (hk.percolates) {
                        percolationCount++
                    }
                }

                val W = percolationCount.toDouble() / R
                out.println(String.format(Locale.US, "%.2f,%.4f,%.0f", p, W, L.toDouble()))
            }

        }

    }

}