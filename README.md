# Taller 2 — Simulador de Pokemon
## Descripción

Este proyecto consiste en un juego de simulación Pokémon desarrollado en Java. La aplicación permite al jugador crear o cargar una partida, capturar Pokémon explorando distintas zonas, gestionar su equipo y enfrentarse a líderes de gimnasio en orden. También incluye la opción de acceder al PC para intercambiar Pokémon del equipo y curar al equipo completo entre batallas.
 
El sistema incluye un módulo de combate que determina el resultado de cada pelea en base a la suma de estadísticas de cada Pokémon, aplicando multiplicadores según la tabla de tipos. Para desbloquear el Alto Mando se deben obtener las 8 medallas de gimnasio, tras lo cual el jugador enfrenta a todos sus miembros de forma consecutiva sin poder curar ni acceder al PC entre batallas. Los datos se almacenan en archivos de texto (`Pokedex.txt`, `Gimnasios.txt`, `Alto Mando.txt`, `Habitats.txt` y `Registros.txt`) y se cargan en memoria mediante listas de objetos que se modifican durante la sesión y persisten al guardar.

---
## Autores

>Andrés Rojas | RUT: 22.065.446-k | andresalitre
>
>Jorge Callejas | RUT: 21.926.182-9 | Satoolio
## Estructura del Proyecto

```
Taller2/
├── src/
│   └── Taller2/
│       ├── Main.java
│       ├── Pokemon.java
│       ├── PokemonJugador.java
│       ├── Jugador.java
│       ├── LiderGym.java
│       ├── AltoMando.java
│       ├── Habitat.java
│       └── TablaTipos.java
├── Pokedex.txt
├── Gimnasios.txt
├── Alto Mando.txt
├── Habitats.txt
├── Registros.txt
├── DiagramaDeClases.pdf
├── ModeloDeDominio.pdf
└── README.md
```

---

## Archivos de Datos

### `Pokedex.txt`
Contiene todos los Pokémon disponibles en el juego.
```
nombre;habitat;porcentajeAparicion;hp;atk;def;spAtk;spDef;spd;tipo
```
Ejemplo:
```
Mawile;Cueva;0.2;50;85;55;85;55;50;Acero
```

### `Habitats.txt`
Lista de zonas donde el jugador puede explorar y capturar Pokémon.
```
Lago
Cueva
Montaña
Bosque
Prado
Mar
```

### `Gimnasios.txt`
Define los líderes de gimnasio, su estado y sus Pokémon.
```
N°;Nombre;Estado;cantPokemons;Pokemon1;Pokemon2;...
```
Ejemplo:
```
1;EmmaLaArdillaRabiosa;Sin derrotar;3;Minun;Plusle;Emolga
```

### `Alto Mando.txt`
Define los miembros del Alto Mando y sus 6 Pokémon.
```
N°;Nombre;Pokemon1;Pokemon2;Pokemon3;Pokemon4;Pokemon5;Pokemon6
```
Ejemplo:
```
1;MartinGalactico;Magikarp;Noivern;Excadrill;Steelix;Lucario;Scizor
```

### `Registros.txt`
Guarda el estado de la partida del jugador.
```
nombreJugador;medalla1;medalla2;...
Pokemon1;Estado
Pokemon2;Estado
GYM;NombreLider;Estado
```
Ejemplo:
```
Clapt;EmmaLaArdillaRabiosa
Mawile;Vivo
Groudon;Debilitado
GYM;EmmaLaArdillaRabiosa;Derrotado
```

---

## Instrucciones de Ejecución

1. Clonar el repositorio.
2. Asegurarse de que los archivos `.txt` estén en la raíz del proyecto (mismo nivel que `src/`).
3. Compilar y ejecutar `Main.java`.

> **Requiere Java 11 o superior.**

---

## Menús

### Menú Inicial
```
1) Continuar.
2) Nueva Partida.
3) Salir.
```

### Menú de Partida
```
1) Revisar equipo.
2) Salir a capturar.
3) Acceso al PC (cambiar Pokémon del equipo).
4) Retar un gimnasio.
5) Desafío al Alto Mando.
6) Curar Pokémon.
7) Guardar.
8) Guardar y Salir.
```

---

## Mecánicas Principales

### Captura
Al explorar una zona se genera un Pokémon aleatorio respetando su porcentaje de aparición. El jugador puede capturarlo o huir. No se pueden capturar Pokémon duplicados.

### Combate
El ganador se determina por la **suma de estadísticas** de cada Pokémon, aplicando el multiplicador de la tabla de tipos:

| Efectividad | Multiplicador |
|---|---|
| Super efectivo | x2 |
| Poco efectivo | x0.5 |
| No afecta | x0 |
| Normal | x1 |

### Equipo y PC
- Los primeros **6 Pokémon** de la lista conforman el equipo activo.
- El resto queda almacenado en el PC.
- Se puede intercambiar Pokémon entre equipo y PC desde el menú.

### Gimnasios
- Deben derrotarse en orden (no se puede saltar un líder).
- Al ganar se obtiene la medalla del líder correspondiente.

### Alto Mando
- Requiere las **8 medallas** de gimnasio.
- Los 7 miembros se enfrentan de forma **consecutiva**.
- No se puede acceder al PC ni curar entre batallas.
- Si el jugador pierde o se rinde, debe empezar desde el primero.

---

## Tecnologías y Librerías

- **Java** — lenguaje principal
- `Scanner` — lectura de archivos
- `BufferedWriter` — escritura/sobrescritura de archivos
- `Random` — generación aleatoria de Pokémon en hábitats
- `ArrayList` — manejo de colecciones dinámicas

---
