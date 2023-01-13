package com.everything.questioner.data.models

data class Question(val statement: String)

val victimQuestions = arrayListOf(
    Question("Es/son inseguros para hacer sus actividades"),
    Question("No sabe/n relacionarse"),
    Question("Más débil//es en algo"),
    Question("Pocos amigos"),
    Question("Buen/os alumno/s"),
    Question("Le/s quitan la merienda"),
    Question("Le/s rompen la mochila o libretas o la ropa"),
    Question("No le da/n las quejas a la maestra"),
    Question("Casi siempre tiene/n moretones"),
    Question("Casi no sale/n al receso"),
    Question("Tratan de faltar a la escuela"),
    Question("No se defienden ante amenazas"),
    Question("No se defienden ante ofensas"),
    Question("No saben responder a las burlas")
)

val aggressorQuestions = arrayListOf(
    Question("Sus relaciones son de fuerza o imposición"),
    Question("Más fuertes en algo"),
    Question("Sus amigos le siguen por temor"),
    Question("Mal/os alumno/s"),
    Question("Abusa/n de los débiles"),
    Question("Rompe/n los objetos de otros sin razón"),
    Question("No respeta/n y desafía/n la autoridad"),
    Question("Provocan problemas por gusto para hacer daño"),
    Question("Fuma/n o bebe/n"),
    Question("No entiende el dolor del otro"),
    Question("No se arrepienten de sus actos"),
    Question("Gusta/n de amenazar a los otros"),
    Question("Gusta/n de ofender a los otros"),
    Question("Gusta/n de burlarse de los otros")
)