package com.everything.questioner.data.models

data class Question(val order: Int, val statement: String)

val victimQuestions = arrayListOf(
    Question(order = 1, "Es/son inseguros para hacer sus actividades"),
    Question(order = 2, "No sabe/n relacionarse"),
    Question(order = 3, "Más débil//es en algo"),
    Question(order = 4, "Pocos amigos"),
    Question(order = 5, "Buen/os alumno/s"),
    Question(order = 6, "Le/s quitan la merienda"),
    Question(order = 7, "Le/s rompen la mochila o libretas o la ropa"),
    Question(order = 8, "No le da/n las quejas a la maestra"),
    Question(order = 9, "Casi siempre tiene/n moretones"),
    Question(order = 10, "Casi no sale/n al receso"),
    Question(order = 11, "Tratan de faltar a la escuela"),
    Question(order = 12, "No se defienden ante amenazas"),
    Question(order = 13, "No se defienden ante ofensas"),
    Question(order = 14, "No saben responder a las burlas")
)

val aggressorQuestions = arrayListOf(
    Question(order = 1, "Sus relaciones son de fuerza o imposición"),
    Question(order = 2, "Más fuertes en algo"),
    Question(order = 3, "Sus amigos le siguen por temor"),
    Question(order = 4, "Mal/os alumno/s"),
    Question(order = 5, "Abusa/n de los débiles"),
    Question(order = 6, "Rompe/n los objetos de otros sin razón"),
    Question(order = 7, "No respeta/n y desafía/n la autoridad"),
    Question(order = 8, "Provocan problemas por gusto para hacer daño"),
    Question(order = 9, "Fuma/n o bebe/n"),
    Question(order = 10, "No entiende el dolor del otro"),
    Question(order = 11, "No se arrepienten de sus actos"),
    Question(order = 12, "Gusta/n de amenazar a los otros"),
    Question(order = 13, "Gusta/n de ofender a los otros"),
    Question(order = 14, "Gusta/n de burlarse de los otros")
)