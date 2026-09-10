package pl.kamil.model

class LogisticMap {

    fun calculateNext(xn: Double, r: Double) : Double {
        return r * xn * (1.0 - xn)
    }
}