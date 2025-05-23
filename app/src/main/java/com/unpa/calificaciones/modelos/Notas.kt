class Notas() {
    var parcial1: Double? = null
    var parcial2: Double? = null
    var parcial3: Double? = null
    var ordinario: Double? = null
    var pFinal: Double? = null
    var extraOrdinario1: Double? = null
    var extraOrdinario2: Double? = null
    var especial : Double? = null

    // Lista de notas en orden para facilitar iteraciones
    val listaNotas: List<String?>
        get() = listOf(
            parcial1?.toString(),
            parcial2?.toString(),
            parcial3?.toString(),
            ordinario?.toString(),
            pFinal?.toString(),
            extraOrdinario1?.toString(),
            extraOrdinario2?.toString(),
            especial?.toString()
        )
    fun getNotaPorIndice(indice: Int): String? {
        return listaNotas.getOrNull(indice)
    }

    fun tieneValor(indice: Int): Boolean {
        return getNotaPorIndice(indice)?.isNotBlank() == true
    }

    fun getUltimaNotaDisponible(): Int {
        for (i in listaNotas.indices.reversed()) {
            if (!listaNotas[i].isNullOrBlank()) {
                return i
            }
        }
        return -1
    }
    fun calcularPromedio(): Double? {
        return if (parcial1 != null && parcial2 != null && parcial3 != null && ordinario != null) {
            val promedioParcial = (parcial1!! + parcial2!! + parcial3!!) / 3
            val promedio = (promedioParcial + ordinario!!) / 2
            "%.2f".format(promedio).toDouble()
        } else {
            null
        }
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Notas) return false

        return parcial1 == other.parcial1 &&
                parcial2 == other.parcial2 &&
                parcial3 == other.parcial3 &&
                ordinario == other.ordinario &&
                pFinal == other.pFinal &&
                extraOrdinario1 == other.extraOrdinario1 &&
                extraOrdinario2 == other.extraOrdinario2 &&
                especial == other.especial
    }

    override fun hashCode(): Int {
        var result = parcial1?.hashCode() ?: 0
        result = 31 * result + (parcial2?.hashCode() ?: 0)
        result = 31 * result + (parcial3?.hashCode() ?: 0)
        result = 31 * result + (ordinario?.hashCode() ?: 0)
        result = 31 * result + (pFinal?.hashCode() ?: 0)
        result = 31 * result + (extraOrdinario1?.hashCode() ?: 0)
        result = 31 * result + (extraOrdinario2?.hashCode() ?: 0)
        result = 31 * result + (especial?.hashCode() ?: 0)
        return result
    }



}
