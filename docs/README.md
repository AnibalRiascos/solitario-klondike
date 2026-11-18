# Documentación del proyecto

Esta carpeta contiene los manuales del **Solitario Klondike**, sus fuentes editables, las imágenes y el script que genera los PDF.

## Contenido

| Archivo | Descripción |
|---|---|
| `Manual_de_Usuario_Solitario_Klondike.pdf` | Manual de usuario: instalación, interfaz, controles, reglas, capturas, victoria y derrota. |
| `Manual_Tecnico_Solitario_Klondike.pdf` | Manual técnico: requisitos, compilación, organización del código, diagrama de clases, estructuras, complejidades y pruebas. |
| `Manual_de_Usuario_Solitario_Klondike.md` | Versión editable del manual de usuario. |
| `Manual_Tecnico_Solitario_Klondike.md` | Versión editable del manual técnico. |
| `manual_user_content.py` | Contenido estructurado del manual de usuario. |
| `manual_tech_content.py` | Contenido estructurado del manual técnico. |
| `generar_pdf.py` | Genera ambos PDF y las versiones Markdown. |
| `assets/` | Capturas de la interfaz y diagrama de clases. |

Los **PDF son la entrega principal**. Los archivos Markdown son una copia editable de referencia; si se modifican, hay que regenerar los PDF con el script para mantener la misma versión.

## Regenerar los PDF

Requisitos: Python 3, `reportlab` y `pillow`.

```bash
python3 -m venv .venv
source .venv/bin/activate
pip install reportlab pillow
python docs/generar_pdf.py
```

El script escribe los PDF y los Markdown en esta misma carpeta. Las imágenes se leen desde `docs/assets/`.

## Notas

- El campo **Programa / Institución** de la portada se deja en blanco para que se complete con el dato académico correspondiente.
- Las capturas son representaciones de la interfaz generadas sin pantalla física; el diagrama de clases se dibujó localmente para garantizar su legibilidad.
- La fecha de los documentos es **20 de noviembre de 2026**.
