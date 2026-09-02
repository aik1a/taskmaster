# AGENTS.md — TaskMaster

## Autoridad

Después de este archivo:

1. leer completo `TaskMaster_ESPECIFICACION_CANONICA.md`;
2. inspeccionar el código real del repositorio.

La especificación canónica es la única fuente funcional y técnica. Este archivo regula el procedimiento de trabajo; no redefine el producto.

La rúbrica, evaluación y material docente se consultan como evidencia cuando sea necesario verificar trazabilidad o una contradicción real; no son especificaciones paralelas.

No usar documentos anteriores de alcance, stack, contrato o decisiones. No combinar versiones.

## Ejecución por fases

- trabajar únicamente la fase autorizada por el usuario;
- no adelantar archivos o lógica de fases posteriores;
- inspeccionar el estado real antes de editar;
- preservar cambios del usuario;
- no completar vacíos con supuestos;
- ante contradicción o ausencia normativa, usar el formato de bloqueo;
- no hacer commit, push, publicación ni entrega sin aprobación expresa.

Antes de editar:

```text
FASE ACTUAL
Objetivo:
Estado inicial:
Archivos que modificaré:
Indicadores cubiertos:
Cambios fuera de alcance: ninguno
```

Después de editar:

1. ejecutar el build indicado por la especificación;
2. ejecutar las pruebas reales de la fase;
3. ejecutar `git diff --check`;
4. ejecutar `git status --short`;
5. informar archivos, cambios, pruebas realizadas y no realizadas;
6. comprobar el gate de la fase;
7. proponer el commit, sin crearlo;
8. responder `STOP — esperando aprobación`.

## Bloqueo

```text
BLOQUEO
Fase:
Punto incompatible o ausente:
Documentos implicados:
Regla A:
Regla B:
Cambio mínimo propuesto:
Impacto:
Archivos afectados:
Esperando aprobación.
```

Un bloqueo no autoriza a inventar una solución ni a modificar la especificación.
