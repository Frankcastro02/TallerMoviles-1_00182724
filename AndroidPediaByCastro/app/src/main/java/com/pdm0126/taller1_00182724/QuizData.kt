package com.pdm0126.taller1_00182724
data class AndroidTask(
    val id: Int,
    val enunciado: String,
    val variantes: List<String>,
    val respuestaCorrecta: String,
    val curiosidad: String
)

val listaDeRetos = listOf(
    AndroidTask(
        id = 1,
        enunciado = "¿Cuál era el nombre original del motor de búsqueda de Google antes de llamarse así?",
        variantes = listOf("Googol", "BackRub", "The Stanford Search", "PageRank Engine"),
        respuestaCorrecta = "BackRub",
        curiosidad = "Se llamaba así porque el algoritmo de Page y Brin se basaba en analizar los \"backlinks\" (enlaces entrantes) para determinar la importancia de un sitio web."
    ),
    AndroidTask(
        id = 2,
        enunciado = "¿En qué año adquirió Google la empresa Android Inc.?",
        variantes = listOf("2003", "2005", "2007", "2008"),
        respuestaCorrecta = "2005",
        curiosidad = "Google compró Android por unos 50 millones de dólares. En ese momento, muchos analistas pensaron que era una compra extraña porque Android estaba enfocado en cámaras digitales, no en teléfonos."
    ),
    AndroidTask(
        id = 3,
        enunciado = "¿Qué significa el nombre Google?",
        variantes = listOf("Un tipo de buscador", "Es un juego de palabras del término 'Googol'", "Es un acrónimo de Stanford", "El apellido de un matemático"),
        respuestaCorrecta = "Es un juego de palabras del término 'Googol'",
        curiosidad = "Un Googol es el término matemático para un 1 seguido de 100 ceros. El nombre fue elegido para reflejar la misión de la empresa de organizar la inmensa cantidad de información disponible en la web."
    )
)