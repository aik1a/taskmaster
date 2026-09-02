# TaskMaster — Especificación canónica de implementación
## Evaluación final · Unidad I · Programación Android

**Versión:** 3.0  
**Fecha:** 2026-09-01  
**Estado:** APROBADA PARA IMPLEMENTACIÓN POR FASES  
**Autoridad:** única fuente normativa funcional y técnica del proyecto.

---

# 1. Autoridad documental

Jerarquía para implementar:

1. `AGENTS.md`, como protocolo operativo;
2. esta especificación, como única fuente funcional y técnica;
3. código real del repositorio, como estado material que debe inspeccionarse.

`TaskMaster_ALCANCE.md` y `TaskMaster_STACK_NIVEL.md` son ayudas de lectura humana, no especificaciones paralelas. El prompt inicial y las auditorías tampoco pueden redefinir el producto.

La rúbrica, la evaluación, el material y el código docente se conservan como evidencia y trazabilidad. Si una verificación demuestra que el canon contradice un requisito docente explícito, detenerse y registrar un bloqueo; no combinar versiones ni corregir el canon por cuenta propia.

---

# 2. Alcance académico

TaskMaster es una aplicación académica local para demostrar:

- Activities Java y layouts XML;
- navegación mediante `Intent`;
- formularios y validaciones;
- `SharedPreferences` para un único perfil;
- widgets y contenedores exigidos en Unidad I;
- POO básica con modelo, Adapter y ViewHolder;
- gestión de versiones con Git.

No es una aplicación de producción. No usa base de datos, backend, red ni autenticación real.

## 2.1. Flujo

```text
LoginActivity (launcher)
├── Registrarse → RegisterActivity → volver al login
└── Ingresar → MainActivity → TaskActivity
```

Activities exactas:

- `LoginActivity`;
- `RegisterActivity`;
- `MainActivity`;
- `TaskActivity`.

## 2.2. Persistencia

| Dato | Mecanismo | Duración |
|---|---|---|
| único perfil local | `SharedPreferences` | permanece al cerrar y reabrir |
| tareas | `ArrayList<TaskModel>` de `TaskActivity` | solo durante esa instancia de la Activity |

Las tareas se pierden cuando `TaskActivity` es destruida o recreada, incluso por rotación, salida y reingreso o cierre del proceso. No restaurarlas con `savedInstanceState`, archivos, JSON, `SharedPreferences`, ViewModel ni otro mecanismo.

No agregar callbacks vacíos del ciclo de vida. Usar `onCreate()` y únicamente callbacks que tengan una necesidad funcional real definida aquí.

---

# 3. Estructura exacta

```text
app/src/main/java/com/example/taskmaster/
├── LoginActivity.java
├── RegisterActivity.java
├── MainActivity.java
├── TaskActivity.java
├── TaskModel.java
└── TaskAdapter.java

app/src/main/res/layout/
├── activity_login.xml
├── activity_register.xml
├── activity_main.xml
├── activity_task.xml
└── item_task.xml

gradle/
└── libs.versions.toml
```

No crear packages, capas ni clases adicionales.

## 3.1. Manifest

```text
LoginActivity     exported=true + MAIN/LAUNCHER
RegisterActivity  exported=false
MainActivity      exported=false
TaskActivity      exported=false
```

No agregar permisos Android.

---

# 4. Stack congelado

| Elemento | Valor |
|---|---|
| Proyecto | `TaskMaster` |
| Namespace/package | `com.example.taskmaster` |
| Lenguaje | Java 11 |
| UI | XML Views |
| Activity base | `AppCompatActivity` |
| Scripts Gradle | Groovy |
| Catálogo | `gradle/libs.versions.toml` |
| Gradle wrapper | `9.5.0` |
| AGP | `9.3.2` |
| `compileSdk` | Android `36.1` |
| `minSdk` | `24` |
| `targetSdk` | `36` |
| `versionCode` | `1` |
| `versionName` | `1.0` |
| Rama | `main` |

Configuración de `compileSdk`:

```groovy
compileSdk {
    version = release(36) {
        minorApiLevel = 1
    }
}
```

Dependencias autorizadas:

```text
androidx.appcompat:appcompat                    1.6.1
com.google.android.material:material            1.10.0
androidx.activity:activity-ktx                  1.13.0
androidx.constraintlayout:constraintlayout      2.1.4
androidx.recyclerview:recyclerview              1.1.0
androidx.cardview:cardview                       1.0.0
```

Conservar las dependencias de prueba del baseline. No añadir ni actualizar otras dependencias.

## 4.1. Técnicas permitidas

- `onCreate()`, `findViewById()` y listeners;
- `Intent` y extras;
- `SharedPreferences` solo para el perfil;
- `setError()`, `Toast` y `AlertDialog`;
- `Patterns.EMAIL_ADDRESS`;
- `View.postDelayed()`;
- `ArrayAdapter<String>`;
- `ArrayList<TaskModel>`;
- RecyclerView Adapter/ViewHolder;
- métodos pequeños y POO básica.

## 4.2. Prohibiciones técnicas

- Kotlin, Compose, Kotlin DSL, ViewBinding o DataBinding;
- Fragments o Navigation Component;
- ViewModel, LiveData, Flow, corrutinas o RxJava;
- Room, SQLite, Firebase, JSON, API, backend o red;
- Repository, DAO, `UserModel`, `SessionManager`, MVVM/MVP/Clean Architecture;
- inyección de dependencias;
- servicios, permisos o componentes Android adicionales;
- `Thread.sleep()`;
- persistencia o restauración de tareas;
- credenciales reales.

---

# 5. Perfil local

Archivo de preferencias:

```text
TaskMasterPrefs
```

Claves:

```text
USER_REGISTERED  boolean
USER_NAME        String
USER_LAST_NAME   String
USER_GENDER      String
USER_EMAIL       String
USER_PHONE       String
USER_PASSWORD    String
```

`RegisterActivity` escribe y `LoginActivity` lee. No existe sesión persistente ni autologin: al abrir la aplicación siempre se muestra `LoginActivity`.

---

# 6. Registro

## 6.1. Campos exactos

1. Nombre;
2. Apellidos;
3. Género;
4. Correo electrónico;
5. Teléfono móvil;
6. Contraseña.

No pedir RUT, edad, dirección, nombre de usuario ni confirmación de contraseña.

## 6.2. Layout e IDs

Raíz `ConstraintLayout`. Dentro, un `ScrollView` con un único `LinearLayout` vertical.

| ID | Tipo/función |
|---|---|
| `mainRegister` | raíz `ConstraintLayout` |
| `scrollRegistro` | `ScrollView` |
| `llRegistro` | formulario vertical |
| `etRegistroNombre` | nombre |
| `etRegistroApellidos` | apellidos |
| `spinnerRegistroGenero` | género |
| `etRegistroCorreo` | correo |
| `etRegistroTelefono` | 9 dígitos nacionales |
| `etRegistroPassword` | contraseña |
| `btnRegistrarUsuario` | registrar |

El teléfono debe mostrar `+56` como prefijo fijo fuera de la parte editable. El campo editable acepta solo los nueve dígitos nacionales.

Los campos de contraseña deben permitir mostrar u ocultar el contenido mediante el control estándar de visibilidad de Material, sin añadir dependencias.

Cada campo debe conservar una etiqueta comprensible; no depender solo del `hint`. Usar acciones de teclado **Siguiente** y **Listo** cuando correspondan.

## 6.3. Género

Opciones visibles exactas y en este orden:

```text
Selecciona tu género
Femenino
Masculino
No binario
Prefiero no responder
```

La primera entrada es un placeholder no válido. Si permanece seleccionada, no guardar y mostrar:

```text
Selecciona tu género.
```

No usar `Otro`, campo libre, pronombres, selección múltiple ni lógica posterior basada en género.

## 6.4. Validaciones

Validar en el orden visual del formulario. Ante error:

- no guardar;
- asociar el error al campo cuando corresponda;
- mover el foco al primer campo inválido;
- conservar los demás valores escritos;
- retirar el error cuando el valor vuelva a ser válido.

### Nombre

- aplicar `trim()`;
- obligatorio;
- entre 2 y 50 caracteres;
- admitir Unicode, espacios, guion y apóstrofo;
- no usar regex restrictiva.

```text
Ingresa tu nombre.
El nombre debe tener entre 2 y 50 caracteres.
```

### Apellidos

- aplicar `trim()`;
- obligatorios;
- entre 2 y 80 caracteres;
- admitir Unicode, espacios, guion y apóstrofo;
- no usar regex restrictiva.

```text
Ingresa tus apellidos.
Los apellidos deben tener entre 2 y 80 caracteres.
```

### Correo

- aplicar `trim()` y convertir a minúsculas;
- obligatorio;
- máximo 254 caracteres;
- sin espacios internos;
- validar con `Patterns.EMAIL_ADDRESS`.

```text
Ingresa tu correo.
Ingresa un correo válido.
```

### Teléfono móvil chileno

- obligatorio;
- exactamente 9 dígitos nacionales;
- comenzar por `9`;
- guardar normalizado como `+569XXXXXXXX`;
- no admitir otros países.

```text
Ingresa tu teléfono móvil.
Ingresa un teléfono móvil chileno válido.
```

### Contraseña

- obligatoria;
- mínimo 8 caracteres;
- no aplicar `trim()` ni transformar el valor;
- sin requisitos obligatorios de mayúsculas, números o símbolos;
- sin máximo adicional definido.

```text
Ingresa tu contraseña.
La contraseña debe tener mínimo 8 caracteres.
```

## 6.5. Guardado y reemplazo

Si el formulario es válido y no existe perfil:

1. guardar los seis datos normalizados;
2. guardar `USER_REGISTERED=true`;
3. mostrar `Usuario registrado.`;
4. ejecutar `finish()` para volver al login;
5. no iniciar sesión automáticamente.

Si ya existe un perfil:

1. mostrar un `AlertDialog` que advierta que el nuevo registro reemplazará el perfil local;
2. guardar solo después de confirmación explícita;
3. si se cancela, conservar íntegro el perfil existente.

No crear historial ni múltiples cuentas.

---

# 7. Login

## 7.1. Layout e IDs

Raíz `ConstraintLayout`.

| ID | Tipo/función |
|---|---|
| `mainLogin` | raíz |
| `tvLoginTitulo` | `TaskMaster` |
| `etLoginIdentificador` | correo o teléfono |
| `etLoginPassword` | contraseña |
| `btnIngresar` | validar |
| `btnIrRegistro` | abrir registro |
| `progressLogin` | progreso de validación |

`etLoginIdentificador` debe admitir texto libre: no usar teclado exclusivamente numérico, no detectar por el primer carácter y no insertar `+56` mientras se escribe. Un correo válido puede comenzar con números.

El campo debe admitir el correo máximo definido y teléfonos escritos con formato. La contraseña debe permitir mostrar u ocultar su contenido. Usar la acción de teclado **Ingresar** cuando corresponda.

## 7.2. Identificador

Al pulsar **Ingresar**:

### Si contiene `@`

1. aplicar `trim()`;
2. convertir a minúsculas;
3. validar con `Patterns.EMAIL_ADDRESS`;
4. comparar con `USER_EMAIL`.

Formato inválido:

```text
Ingresa un correo válido.
```

### Si no contiene `@`

Intentar teléfono chileno:

1. retirar espacios, guiones y paréntesis;
2. aceptar `+56` si fue escrito;
3. obtener nueve dígitos nacionales que comiencen por `9`;
4. normalizar a `+569XXXXXXXX`;
5. comparar con `USER_PHONE`.

Entradas válidas equivalentes:

```text
912345678
+56912345678
+56 9 1234 5678
```

Si no puede normalizarse, incluido un valor como `9123abc`:

```text
Ingresa un correo o teléfono válido.
```

## 7.3. Contraseña y resultados

- no aplicar `trim()`;
- comparar exactamente con `USER_PASSWORD`;
- si está vacía: `Ingresa tu contraseña.`;
- si no existe perfil: `No hay un usuario registrado.`;
- si las credenciales no coinciden: `Correo/teléfono o contraseña incorrectos.`.

No revelar qué credencial falló.

## 7.4. ProgressBar

```text
ID: progressLogin
tipo: indeterminado
estado inicial: GONE
retraso: 500 ms
mecanismo: progressLogin.postDelayed(..., 500)
porcentaje visible: no
```

Algoritmo:

1. ignorar el toque si ya hay una validación activa;
2. comprobar que existe perfil y validar campos/formato;
3. mostrar `progressLogin` y deshabilitar `btnIngresar`;
4. ejecutar la comparación local tras 500 ms;
5. ante fallo, ocultar progreso, habilitar botón y mostrar el error;
6. ante éxito, restablecer el estado visual y abrir `MainActivity` con `EXTRA_NOMBRE`.

No simular red, descarga, porcentaje, tiempos aleatorios ni espera de 1000 ms.

---

# 8. Bienvenida

`MainActivity` usa raíz `ConstraintLayout`.

| ID | Tipo/uso |
|---|---|
| `main` | raíz |
| `ivLogo` | `@mipmap/ic_launcher` |
| `tvTitulo` | `TaskMaster` |
| `tvDescripcion` | `Hola, {nombre}` |
| `btnComenzar` | abre `TaskActivity` |

Leer `EXTRA_NOMBRE`. No generar un logo ni añadir HomeActivity independiente.

---

# 9. Gestión de tareas

## 9.1. Layout

Raíz `ConstraintLayout`. Para evitar un RecyclerView dentro de ScrollView:

```text
ConstraintLayout
└── LinearLayout vertical (match constraints)
    ├── ScrollView (0dp + weight 1)
    │   └── LinearLayout vertical
    │       ├── formulario
    │       ├── TableLayout
    │       └── ProgressBar de tareas
    ├── TextView Tareas
    └── RecyclerView (0dp + weight 1)
```

IDs:

```text
mainTask
llContenidoTask
scrollFormulario
llFormulario
etNombreTarea
spinnerCategoria
rgPrioridad
rbAlta
rbMedia
rbBaja
ratingDificultad
cbCompletada
btnAgregarTarea
tableResumen
tvTotalValor
tvCompletadasValor
tvPendientesValor
progressCompletadas
tvTituloLista
rvTareas
```

Categorías exactas: `Personal`, `Estudio`, `Trabajo`.  
Prioridades exactas: `Alta`, `Media`, `Baja`; ninguna inicial.  
Rating inicial: `0`. CheckBox inicial: desmarcado. Lista, resumen y progreso iniciales: `0`.

## 9.2. Modelo e ítem

`TaskModel`:

```java
private String nombre;
private String categoria;
private String prioridad;
private float dificultad;
private boolean completada;
```

Constructor y getters; booleano `isCompletada()`. Sin setters, ID, fecha ni persistencia.

`TaskAdapter` contiene dataset, constructor, `onCreateViewHolder`, `onBindViewHolder`, `getItemCount` y `TaskViewHolder`. Sin listeners.

`item_task.xml`: `CardView` con `LinearLayout` vertical e IDs:

```text
tvItemNombre
tvItemCategoria
tvItemPrioridad
tvItemDificultad
tvItemEstado
```

## 9.3. Lógica

Campos de `TaskActivity`:

```text
ArrayList<TaskModel> listaTareas
TaskAdapter taskAdapter
int totalTareas = 0
int tareasCompletadas = 0
```

Métodos exactos:

```text
configurarSpinner()
configurarRecyclerView()
agregarTarea()
actualizarResumen()
limpiarFormulario()
```

Agregar tarea:

1. validar nombre: `Ingresa una tarea`;
2. validar prioridad: `Selecciona una prioridad`;
3. leer categoría, prioridad, dificultad y estado;
4. crear y agregar `TaskModel`;
5. ejecutar `notifyDataSetChanged()`;
6. actualizar contadores, resumen y progreso;
7. limpiar formulario;
8. mostrar `Tarea agregada`.

Resumen:

```text
pendientes = totalTareas - tareasCompletadas
progreso = totalTareas == 0 ? 0 : tareasCompletadas * 100 / totalTareas
```

`progressCompletadas` es determinado, mínimo 0, máximo 100 y sin retraso artificial.

Limpiar: nombre vacío, categoría índice 0, prioridad limpia, rating 0, checkbox desmarcado y foco en nombre.

---

# 10. Fuera de alcance

- múltiples usuarios, sesión persistente, logout o recuperación de contraseña;
- editar perfil;
- OTP, SMS, biometría o proveedores externos de login;
- editar, eliminar, persistir o restaurar tareas;
- cambiar el estado desde la CardView;
- fechas, alarmas o notificaciones;
- búsqueda, filtros u ordenamiento;
- categorías editables o múltiples listas;
- red, API, backend o base de datos;
- arquitectura avanzada;
- ramas, pull requests, publicación o entrega externa sin autorización.

---

# 11. Fases

Una fase autorizada por turno.

| Fase | Alcance | Commit propuesto |
|---|---|---|
| 0 | baseline Android, catálogo Gradle y Git | `chore: crear proyecto TaskMaster` |
| 1 | UI login/registro y manifest | `feat: crear acceso y registro local` |
| 2 | persistencia, validaciones y login | `feat: implementar registro e inicio de sesion local` |
| 3 | bienvenida y navegación | `feat: crear pantalla de bienvenida` |
| 4 | UI de tareas | `feat: crear interfaz de gestion de tareas` |
| 5 | modelo, Adapter, CardView y RecyclerView | `feat: implementar recycler view y card view` |
| 6 | creación temporal de tareas | `feat: implementar creacion de tareas` |
| 7 | resumen y progreso | `feat: agregar resumen y barra de progreso` |
| 8 | auditoría integral | `fix: validar funcionamiento contra rubrica` |
| 9 | preparación de entrega | sin cambio funcional obligatorio |

Cada gate exige:

1. `./gradlew assembleDebug`;
2. pruebas reales de la fase;
3. `git diff --check`;
4. `git status --short`;
5. informe de pruebas realizadas y no realizadas;
6. detenerse sin commit hasta aprobación.

---

# 12. Pruebas de aceptación

## Perfil y navegación

| ID | Caso | Resultado |
|---|---|---|
| T01 | abrir app | `LoginActivity` |
| T02 | abrir registro | `RegisterActivity` |
| T03 | campos vacíos o solo espacios | no registra; muestra errores |
| T04 | nombre/apellidos fuera de rango | no registra |
| T05 | correo inválido | no registra |
| T06 | teléfono de longitud incorrecta o que no comienza por 9 | no registra |
| T07 | contraseña menor de 8 caracteres | no registra |
| T08 | género en placeholder | no registra |
| T09 | registro válido | guarda perfil y vuelve al login |
| T10 | perfil existente | pide confirmación antes de reemplazar |
| T11 | cancelar reemplazo | conserva perfil anterior |
| T12 | cerrar y reabrir | perfil permanece; vuelve al login sin autologin |
| T13 | login con correo registrado | autentica |
| T14 | login con teléfono en los tres formatos admitidos | normaliza y autentica |
| T15 | correo que comienza con números y contiene `@` | se trata como correo |
| T16 | `9123abc` sin `@` | identificador inválido |
| T17 | credenciales incorrectas | mensaje único definido |
| T18 | doble toque durante validación | una sola validación |
| T19 | login correcto | progreso de 500 ms y bienvenida |
| T20 | saludo | nombre correcto |
| T21 | gestionar tareas | abre `TaskActivity` |

## Tareas

| ID | Caso | Resultado |
|---|---|---|
| T22 | ScrollView | formulario desplazable |
| T23 | Spinner categoría | tres opciones exactas |
| T24 | RadioGroup prioridad | exclusión mutua |
| T25 | tarea sin nombre | no agrega |
| T26 | tarea sin prioridad | no agrega |
| T27 | tarea válida pendiente | CardView correcta |
| T28 | tarea válida completada | CardView correcta |
| T29 | resumen | valores correctos |
| T30 | progreso sin tareas | 0 |
| T31 | progreso con tareas | porcentaje real |
| T32 | recrear `TaskActivity` | tareas se pierden; no se restauran |
| T33 | cerrar/reabrir app | tareas se pierden; perfil permanece |
| T34 | build | exitoso |

---

# 13. Terminado

El proyecto termina únicamente si:

- compila;
- cumple el flujo completo;
- el perfil persiste sin autologin;
- las tareas no persisten ni se restauran;
- los widgets y contenedores exigidos tienen evidencia;
- las pruebas informan resultados reales;
- no existe código fuera de alcance;
- Git demuestra evolución por fases.
