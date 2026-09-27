Para que sirve y cómo usar GIT Worktree


**Git Worktree** es una de esas funciones ocultas de Git que, una vez que la descubrís, te cambia la forma de trabajar.

En pocas palabras, sirve para **tener varias ramas de un mismo repositorio abiertas y activas al mismo tiempo en carpetas separadas**, sin necesidad de duplicar el repositorio clonándolo de nuevo y sin andar lidiando con `git stash`.

---

## ¿Para qué sirve? (El problema que resuelve)

Imaginate este escenario clásico: estás en el medio de un desarrollo complejo en la rama `feature-nueva` con un montón de archivos modificados que todavía no querés commitear. De repente, surge un bug crítico en producción (`main`) que tenés que testear y fixear ya mismo.

Antes de conocer Worktree, tus opciones eran:

1. Hacer un `git stash`, cambiar de rama, fixear, volver a tu rama y hacer `git stash pop`. (Molesto si estás en el medio de algo caótico).
2. Commitear cosas a medio hacer con un mensaje tipo "wip" (work in progress) para limpiar el árbol.
3. Clonar el repositorio en otra carpeta distinta y bajarte todo de nuevo.

**Con Git Worktree**, simplemente creás un "árbol de trabajo" secundario en otra carpeta. Git vincula esa carpeta a la rama del bug, la arreglás ahí mismo, y tu carpeta principal sigue intacta con tu desarrollo a medias.

---

## ¿Cómo usar GIT Worktree? (Comandos clave)

Por defecto, cuando clonás un repositorio, tenés un solo *worktree* (la carpeta principal). Pero podés agregar los que quieras.

### 1. Crear un nuevo Worktree

Para crear una nueva carpeta vinculada a una rama (nueva o existente), usás:

```bash
git worktree add <ruta-de-la-carpeta> <nombre-de-rama>

```

* **Ejemplo 1 (Rama existente):** Querés revisar la rama `main` en una carpeta separada para ver un bug:
```bash
git worktree add ../fix-hotfix main

```


*Esto creará una carpeta llamada `fix-hotfix` al mismo nivel que tu carpeta actual, con el código de `main` listo para tocar.*
* **Ejemplo 2 (Rama nueva):** Querés crear una rama nueva (`feature-test`) directamente en otra carpeta:

```bash
    git worktree add -b feature-test ../nueva-feature
    ```

### 2. Listar los Worktrees activos
Si querés ver qué carpetas tenés vinculadas y a qué ramas apuntan:

```bash
git worktree list

```

Te va a devolver algo como esto:

```text
/usuarios/tu-usuario/proyecto         d3f4a2c [mi-rama-actual]
/usuarios/tu-usuario/fix-hotfix       a1b2c3d [main]

```

### 3. Eliminar un Worktree cuando terminaste

Una vez que terminaste de fixear el bug, pusheaste los cambios y ya no necesitás esa carpeta secundaria, **no la borres a mano con el explorador de archivos**. Usá el comando de Git para que limpie el registro correctamente:

```bash
git worktree remove <ruta-de-la-carpeta>

```

*Ejemplo:*

```bash
git worktree remove ../fix-hotfix

```

Esto borra la carpeta física y le avisa a Git que ya no use esa referencia.

---

## ⚠️ Regla de oro y limitaciones

La única restricción importante que tiene Git Worktree es que **no podés tener la misma rama activa en dos worktrees al mismo tiempo**.

Si estás en tu carpeta principal en la rama `main`, no podés crear un worktree secundario que también apunte a `main`. Git te va a tirar un error para evitar que pises tus propios cambios y hagas desastres con el puntero de la rama.

## Resumen de ventajas

* **Velocidad:** No descarga el proyecto de nuevo de GitHub/GitLab; usa la base de datos local de `.git` que ya tenés, por lo que es instantáneo (ideal para proyectos pesados).
* **Limpieza:** Te permite correr pruebas aisladas, correr servidores en paralelo en distintas ramas para comparar comportamientos, o revisar el código de un compañero en un toque sin romper lo tuyo.

```

```


Bueno hice un ejercicio y te cuento los detalles tengo un repositorio el cual tiene la rama principal main y una secundaria llamada dev desde donde voy a sacar las nuevas ramas entonces cree a mano las ramas llamadas rama1, rama2 y rama3 luego agregue un archivo en cada rama y luego me di cuenta que tengo que estar parado en una rama distinta a la rama con la que quiero hacer el worktree para que funcione, luego hice el "git worktree add ../rama1 rama1" y luego hice lo mismo con rama2 y rama3, para que cree las 3 carpetas. quiero saber ahora que hago es decir puedo abrir las 3 ramas en 3 ventanas de intellij? y luego cómo hago con el git push


¡Excelente ejercicio! Hiciste exactamente el flujo correcto y experimentaste de primera mano la regla de oro: **no podés crear un worktree de una rama si ya estás parado en ella** en la carpeta principal. El hecho de haber creado las ramas antes y luego haber mapeado cada una a su carpeta (`../rama1`, etc.) es la forma ideal de estructurarlo.

Ahora que tenés tus tres carpetas listas, vamos a responder tus dos dudas concretas, que son los pasos clave para el día a día.

---

## 1. ¿Puedo abrir las 3 ramas en 3 ventanas de IntelliJ?

**Sí, absolutamente.** Esa es, de hecho, la mayor ventaja de usar Git Worktree.

Podés ir a IntelliJ IDEA y abrir cada carpeta como si fuera un proyecto totalmente independiente:

1. Vas a `File -> Open`.
2. Buscás la carpeta `PruebaGITGus` (la principal), y la abrís.
3. Hacés lo mismo (`File -> Open`) para `rama1`, `rama2` y `rama3`. Cuando te pregunte si querés abrirla en una nueva ventana (*New Window*), le decís que sí.

### Lo que vas a notar en IntelliJ:

* Cada ventana va a entender perfectamente en qué rama está parada. Vas a ver abajo a la derecha de IntelliJ que una dice `rama1`, la otra `rama2`, etc.
* Podés levantar procesos, correr tests o editar código en las tres ventanas **en paralelo** sin que una pise a la otra.
* **Cuidado con un detalle:** Como cada ventana es un "proyecto" clonado del mismo origen, IntelliJ va a crear sus carpetas ocultas `.idea` dentro de cada worktree. No pasa nada, pero recordá tener un buen `.gitignore` para que no intente commitear esos archivos de configuración del IDE.

---

## 2. ¿Cómo hago con el `git push`?

Acá viene lo mejor: **cada carpeta se comporta como un repositorio Git normal.** Tenés dos formas de manejar los commits y los pushes, elegí la que te resulte más cómoda:

### Opción A: Desde la terminal de cada carpeta (Recomendado por simplicidad)

Si estás parado con la terminal (ya sea Git Bash o la terminal integrada de IntelliJ) dentro de la carpeta de la `rama1`:

1. Hacés tus cambios en los archivos.
2. Agregás y commiteás:
```bash
git add .
git commit -m "Añado cambios en rama 1"

```


3. Hacés el push. Como es la primera vez que vas a subir estas ramas locales al repositorio remoto (GitHub/GitLab), tenés que establecer la relación con el comando `--set-upstream` (o `-u`):

```bash
   git push -u origin rama1

```

*(Para los siguientes pushes en esa misma carpeta, ya te alcanza con escribir solo `git push`)*.

Hacés exactamente lo mismo metiéndote a la carpeta de `rama2` y `rama3`. Cada una sabe a qué rama tiene que empujar los cambios.

### Opción B: Usar la interfaz visual de IntelliJ

Si preferís no usar la consola:

1. En la ventana de IntelliJ de `rama1`, hacés los cambios.
2. Presionás `Ctrl + K` (Commit), seleccionás tus archivos, escribís el mensaje y le das a Commit.
3. Presionás `Ctrl + Shift + K` (Push). IntelliJ va a detectar automáticamente que estás en `rama1` y te va a proponer crearla en el remoto (`origin/rama1`). Le das a Push y listo.

---

## ¿Cómo sigue tu flujo de trabajo ahora?

Una vez que termines de trabajar en esas ramas y los cambios estén subidos (y eventualmente mergeados a `dev` o `main` mediante un Pull Request):

1. Cerrás las ventanas de IntelliJ de las ramas secundarias.
2. Volvés a tu terminal en la carpeta principal.
3. Borrás los worktrees para limpiar el disco de forma ordenada:

```bash
   git worktree remove ../rama1
   git worktree remove ../rama2
   git worktree remove ../rama3

```


## 🚀 Flujo de Trabajo Paso a Paso

### 1. Clonación Inicial (Una sola vez)

Cloná tu repositorio de forma normal y parate en la rama que uses como base para ramificar (por ejemplo, `dev`).

```bash
git clone <url-del-repositorio>
cd proyecto-principal
git checkout dev

```

### 2. Crear las Ramas y los Worktrees

⚠️ **REGLA DE ORO:** No podés crear un worktree para una rama si ya estás parado en ella en tu carpeta actual.

* **Opción A: Si las ramas ya existen localmente:**
  Asegurate de estar parado en otra rama (como `dev` o `main`) en tu terminal y corré:
```bash
git worktree add ../rama1 rama1
git worktree add ../rama2 rama2

```


* **Opción B: Crear la rama y el Worktree todo junto (Recomendado):**
  Este comando crea la rama, genera la carpeta y le hace el checkout automáticamente:
```bash
git worktree add -b rama1 ../rama1

```


### 3. Configuración en IntelliJ IDEA

1. Abrí IntelliJ y ve a **File → Open**.
2. Selecciona la carpeta secundaria (ej. `rama1`).
3. Cuando el IDE te pregunte, elegí **Open in New Window** (Abrir en una nueva ventana).

*Cada ventana de IntelliJ va a rastrear exclusivamente su propia rama. Verás el nombre de la rama abajo a la derecha del IDE.*

---

## 🛠️ Trabajo Diario (Commits y Push)

Cada carpeta secundaria se comporta como un repositorio Git normal. Los comandos se ejecutan dentro de la terminal de la carpeta específica:

| Acción | Comando / Consigna | Notas |
| --- | --- | --- |
| **Commits** | `git add .`<br>

<br>`git commit -m "Mis cambios"` | Afecta únicamente a la rama de esa carpeta. |
| **Push (Primera vez)** | `git push -u origin <nombre-rama>` | Vincula tu rama local con el servidor (GitLab/GitHub). |
| **Push (Siguientes)** | `git push` | Sube los cambios directo a su rama correspondiente. |
| **Actualizar todo** | `git fetch --all` | Al estar conectados, si lo corrés en cualquier carpeta, todas se enteran de los cambios del remoto. |

---

## 🛑 Solución de Problemas (Troubleshooting en Windows)

Windows suele ser muy celoso con los permisos de archivos cuando IntelliJ o Java están corriendo de fondo. Si al querer borrar un worktree te topás con errores como:

* *`Deletion of directory failed. Should I try again? (y/n)`*
* *`error: failed to delete... Permission denied`*

No entres en pánico, seguí estos pasos de rescate:

1. **Cerrá IntelliJ IDEA por completo** (para liberar los hilos de Java que indexan los archivos).
2. Si usás sincronización en la nube (Google Drive Desktop, OneDrive), **pausalos un momento**.
3. En tu terminal principal, ejecutá el comando de limpieza interna de Git:
```bash
git worktree prune

```


*(Esto le dice a Git que limpie su base de datos de cualquier referencia muerta).*
4. Ahora que Git se desvinculó de esa carpeta, andá al explorador de archivos de Windows o usá la terminal y borrá la carpeta rebelde manualmente (`Shift + Supr`).

---

## 🧹 Cierre de Tareas Ordenado

Cuando termines tu feature, hayas subido el código y metido el Pull Request, limpiá tu entorno desde la terminal de la **carpeta principal**:

```bash
# Elimina el worktree y borra la carpeta físicamente de forma segura
git worktree remove ../rama1

```

*(Recordá que esto borra la carpeta de trabajo, pero la rama en tu historial de Git sigue existiendo intacta).*
