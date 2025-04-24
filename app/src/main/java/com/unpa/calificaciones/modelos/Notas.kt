data class Notas(
    val primerParcial: String? = null,
    val segundoParcial: String? = null,
    val tercerParcial: String? = null,
    val ordinario: String? = null,
    val final: String? = null,
    val ex1: String? = null,
    val ex2: String? = null,
) {
    private val listaNotas = listOf(
        primerParcial,
        segundoParcial,
        tercerParcial,
        ordinario,
        final,
        ex1,
        ex2
    )

    /**
     * Devuelve la nota correspondiente a la posición:
     * 0 = primer parcial, 1 = segundo parcial, ..., 6 = ex2
     */
    fun getNotaPorIndice(indice: Int): String? {
        return if (indice in listaNotas.indices) listaNotas[indice] else null
    }
    /**
     * Devuelve todas las notas en orden, con nombre y valor.
     */
    fun getUltimaNotaDisponible():Int{
        for (i in listaNotas.indices.reversed()) {
            val nota = listaNotas[i]
            if (!nota.isNullOrBlank()) {
                return i
            }
        }
        return 0;
    }
}
