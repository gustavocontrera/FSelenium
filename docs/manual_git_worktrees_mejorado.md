# 🌳 Guía Práctica: Git Worktrees + IntelliJ

> [!NOTE]
> **Objetivo:** Esta guía es un documento vivo que resume las mejores prácticas para trabajar con múltiples ramas en paralelo utilizando **Git Worktrees** y ventanas separadas de **IntelliJ IDEA**, evitando la necesidad de clonar repetidamente el mismo repositorio.

## 🎯 Beneficios Principales
* Trabajar en varias ramas simultáneamente sin conflictos.
* Usar una ventana de IntelliJ por rama/proyecto.
* Mantener un único repositorio local y evitar clones pesados innecesarios.
* Flujo de trabajo claro y seguro al interactuar con GitLab u otros remotos.

## 🧠 Conceptos Clave

| Concepto | Descripción |
| :--- | :--- |
| **Repositorio** | Solo existe **un** repositorio Git local que almacena la historia completa, ramas y objetos. |
| **Worktree** | Es una carpeta en el disco asociada a una rama específica. Todas las carpetas comparten el repositorio interno original. |

> [!TIP]
> Piensa en **Git Worktrees** como la posibilidad de hacer "checkout múltiples" de manera simultánea en diferentes directorios.

## 📁 Estructura en Disco Sugerida

La estructura recomendada para organizar tus proyectos es mantener el repositorio principal y los worktrees paralelos dentro de una carpeta padre:

```text
repos/
├── mi-proyecto/                  (Carpeta base -> rama main/develop)
├── mi-proyecto-feature-login/    (Worktree -> rama feature/login)
└── mi-proyecto-hotfix-123/       (Worktree -> rama hotfix/123)
```

## 🚀 Flujo de Trabajo

### 1. Clonación Inicial (Una sola vez)
Clona el repositorio base. Esta carpeta generalmente se mantiene en `main` o `develop`.

```bash
git clone git@gitlab.com:empresa/mi-proyecto.git
cd mi-proyecto
```

### 2. Crear Worktrees
Tienes dos opciones principales según lo que necesites:

**A. Crear un worktree para una rama existente:**
```bash
# Sintaxis: git worktree add <ruta-nueva-carpeta> <nombre-rama-existente>
git worktree add ../mi-proyecto-feature-login feature/login
```

**B. Crear una rama nueva y un worktree al mismo tiempo:**
```bash
# Sintaxis: git worktree add -b <nueva-rama> <ruta-nueva-carpeta>
git worktree add -b feature/nueva-api ../mi-proyecto-nueva-api
```
*(Esto crea la rama, la carpeta y hace el checkout automáticamente).*

### 3. Configuración en IntelliJ IDEA

1. Abre cada carpeta del worktree mediante **File → Open**.
2. Selecciona **Open in New Window**.

> [!NOTE]
> Cada ventana verá exclusivamente su propia rama y tendrá su indexación y caché independientes. No requiere ninguna configuración adicional especial en el IDE.

## 🛠️ Trabajo Diario (Commits, Push, Pull)

| Acción | Comando / Descripción | Detalles |
| :--- | :--- | :--- |
| **Commits** | `git add .`<br>`git commit -m "Mensaje"` | Son 100% locales en la rama del worktree. No afectan a las demás. |
| **Push** | `git push` | Sube **solo** la rama del worktree actual. |
| **Fetch** | `git fetch --all` | Compartido. Ejecútalo en **cualquier** worktree y todos verán las ramas remotas actualizadas. |
| **Pull** | `git pull` | Específico de cada rama. Solo se ejecuta en el worktree que necesita incorporar cambios remotos. |

## ⚠️ Reglas y Buenas Prácticas

> [!IMPORTANT]
> **Lo que SÍ está permitido:**
> - Mantener múltiples ramas activas al mismo tiempo en paralelo.
> - Realizar commits locales sin hacer push (WIP).
> - Tener múltiples ventanas de IntelliJ (una por rama/worktree).
> - Asignar **una rama = un worktree**.

> [!CAUTION]
> **Lo que NO debes hacer:**
> - Checkout de la **misma rama** en dos worktrees diferentes.
> - **Borrar manualmente las carpetas** de los worktrees desde el explorador de archivos.

### Cómo eliminar un Worktree correctamente
Nunca elimines la carpeta manualmente. Usa siempre Git:

```bash
git worktree remove ../mi-proyecto-feature-login
```
*(Esto elimina la carpeta del worktree, pero la rama en Git se mantiene intacta).*

## 💡 Cuándo utilizar Git Worktrees

* ✅ Desarrollo de varias features o historias en paralelo.
* ✅ Necesidad de resolver un Hotfix urgente mientras estás a mitad de desarrollo de otra tarea.
* ✅ Code Reviews locales de otras ramas sin perder el estado de tu código actual.
* ✅ Proyectos muy pesados donde el build y la indexación inicial son costosos.

---

### 🔍 Comandos Útiles Rápidos

```bash
# Listar todos los worktrees activos en tu entorno
git worktree list

# Ver todas las ramas locales y remotas
git branch -a

# Ver el estado de la rama actual en el worktree activo
git status
```
