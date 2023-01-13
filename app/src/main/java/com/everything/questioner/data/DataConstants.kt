package com.everything.questioner.data

object DataConstants {
    val programPl = """
        victimas(Persona) :-
            victima(Persona,les_quitan_la_merienda);
            victima(Persona,les_rompen_su_propiedad);
            victima(Persona,casi_siempre_tienen_moretones).
            
        agresores(Persona) :-
           agresor(Persona,abusan_debiles);
           agresor(Persona,rompe_objetos_otros);
           agresor(Persona,no_respetan_autoridad);
           agresor(Persona,provocar_problemas_dannos);
           agresor(Persona,no_entienden_dolor_otro).
    """.trimIndent()



}