#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Genera los manuales PDF del proyecto Solitario Klondike y sus versiones Markdown.

Requisitos:
    pip install reportlab pillow

Uso:
    python3 docs/generar_pdf.py
"""

from __future__ import annotations

import re
from pathlib import Path

from PIL import Image as PILImage
from reportlab.lib import colors
from reportlab.lib.enums import TA_CENTER, TA_JUSTIFY, TA_LEFT
from reportlab.lib.pagesizes import A4, landscape
from reportlab.lib.styles import ParagraphStyle, getSampleStyleSheet
from reportlab.lib.units import cm
from reportlab.pdfbase import pdfmetrics
from reportlab.pdfbase.ttfonts import TTFont
from reportlab.platypus import (
    BaseDocTemplate,
    Frame,
    HRFlowable,
    Image,
    KeepTogether,
    NextPageTemplate,
    PageBreak,
    PageTemplate,
    Paragraph,
    Preformatted,
    Spacer,
    Table,
    TableStyle,
)
from reportlab.platypus.tableofcontents import TableOfContents

BASE = Path(__file__).resolve().parent
ASSETS = BASE / "assets"
FONTS = Path("/usr/share/fonts/dejavu")

# ---------------------------------------------------------------------------
# Fuentes y estilos
# ---------------------------------------------------------------------------

pdfmetrics.registerFont(TTFont("DejaVuSans", str(FONTS / "DejaVuSans.ttf")))
pdfmetrics.registerFont(TTFont("DejaVuSans-Bold", str(FONTS / "DejaVuSans-Bold.ttf")))
pdfmetrics.registerFont(TTFont("DejaVuSans-Oblique", str(FONTS / "DejaVuSans-Oblique.ttf")))
pdfmetrics.registerFont(TTFont("DejaVuSans-BoldOblique", str(FONTS / "DejaVuSans-BoldOblique.ttf")))
pdfmetrics.registerFont(TTFont("DejaVuSansMono", str(FONTS / "DejaVuSansMono.ttf")))
pdfmetrics.registerFont(TTFont("DejaVuSansMono-Bold", str(FONTS / "DejaVuSansMono-Bold.ttf")))
pdfmetrics.registerFontFamily(
    "DejaVuSans",
    normal="DejaVuSans",
    bold="DejaVuSans-Bold",
    italic="DejaVuSans-Oblique",
    boldItalic="DejaVuSans-BoldOblique",
)

NAVY = colors.HexColor("#1F3A5F")
BLUE = colors.HexColor("#2E5E8C")
LIGHT_BLUE = colors.HexColor("#EAF1F8")
GOLD = colors.HexColor("#C9A227")
INK = colors.HexColor("#20252C")
MUTED = colors.HexColor("#5B6673")
GRID = colors.HexColor("#C9D2DC")
SOFT = colors.HexColor("#F5F7FA")
GREEN = colors.HexColor("#2F6F44")
RED = colors.HexColor("#9B2C2C")
AMBER = colors.HexColor("#8A5A00")

PAGE_W, PAGE_H = A4
MARGIN = 2 * cm
CONTENT_W = PAGE_W - 2 * MARGIN
LAND_W, LAND_H = landscape(A4)
LAND_CONTENT_W = LAND_W - 2 * MARGIN

styles = getSampleStyleSheet()
# ReportLab trae estilos llamados Bullet y Code; se reemplazan por los del manual.
for _conflict in ("Bullet", "Code"):
    if _conflict in styles.byName:
        del styles.byName[_conflict]

styles.add(ParagraphStyle(
    name="Body",
    fontName="DejaVuSans",
    fontSize=10.2,
    leading=15,
    textColor=INK,
    alignment=TA_LEFT,
    spaceAfter=6,
))
styles.add(ParagraphStyle(
    name="BodyLeft",
    parent=styles["Body"],
    alignment=TA_LEFT,
))
styles.add(ParagraphStyle(
    name="H1",
    fontName="DejaVuSans-Bold",
    fontSize=18,
    leading=22,
    textColor=NAVY,
    spaceBefore=16,
    spaceAfter=8,
    keepWithNext=True,
))
styles.add(ParagraphStyle(
    name="H2",
    fontName="DejaVuSans-Bold",
    fontSize=13,
    leading=17,
    textColor=BLUE,
    spaceBefore=12,
    spaceAfter=4,
    keepWithNext=True,
))
styles.add(ParagraphStyle(
    name="H3",
    fontName="DejaVuSans-Bold",
    fontSize=10.8,
    leading=14,
    textColor=INK,
    spaceBefore=8,
    spaceAfter=3,
    keepWithNext=True,
))
styles.add(ParagraphStyle(
    name="Bullet",
    parent=styles["Body"],
    alignment=TA_LEFT,
    leftIndent=16,
    bulletIndent=4,
    spaceAfter=3,
))
styles.add(ParagraphStyle(
    name="Step",
    parent=styles["Body"],
    alignment=TA_LEFT,
    leftIndent=18,
    firstLineIndent=-18,
    spaceAfter=3,
))
styles.add(ParagraphStyle(
    name="TableText",
    fontName="DejaVuSans",
    fontSize=8.4,
    leading=10.6,
    textColor=INK,
    alignment=TA_LEFT,
))
styles.add(ParagraphStyle(
    name="TableHeader",
    fontName="DejaVuSans-Bold",
    fontSize=8.4,
    leading=10.6,
    textColor=colors.white,
    alignment=TA_LEFT,
))
styles.add(ParagraphStyle(
    name="Code",
    fontName="DejaVuSansMono",
    fontSize=7.9,
    leading=9.8,
    textColor=INK,
    spaceBefore=0,
    spaceAfter=0,
))
styles.add(ParagraphStyle(
    name="Note",
    fontName="DejaVuSans",
    fontSize=9.4,
    leading=13,
    textColor=INK,
    backColor=colors.HexColor("#FFF8E1"),
    borderColor=colors.HexColor("#E0C56B"),
    borderWidth=0.8,
    borderPadding=7,
    spaceBefore=6,
    spaceAfter=8,
))
styles.add(ParagraphStyle(
    name="Caption",
    fontName="DejaVuSans-Oblique",
    fontSize=8.8,
    leading=11,
    textColor=MUTED,
    alignment=TA_CENTER,
    spaceBefore=3,
    spaceAfter=10,
))
styles.add(ParagraphStyle(
    name="CoverKicker",
    fontName="DejaVuSans-Bold",
    fontSize=11,
    leading=14,
    textColor=colors.white,
    alignment=TA_CENTER,
))
styles.add(ParagraphStyle(
    name="CoverTitle",
    fontName="DejaVuSans-Bold",
    fontSize=31,
    leading=38,
    textColor=NAVY,
    alignment=TA_CENTER,
    spaceAfter=6,
))
styles.add(ParagraphStyle(
    name="CoverSubtitle",
    fontName="DejaVuSans-Bold",
    fontSize=19,
    leading=24,
    textColor=BLUE,
    alignment=TA_CENTER,
    spaceAfter=4,
))
styles.add(ParagraphStyle(
    name="CoverSubtitle2",
    fontName="DejaVuSans",
    fontSize=13,
    leading=17,
    textColor=MUTED,
    alignment=TA_CENTER,
    spaceAfter=4,
))
styles.add(ParagraphStyle(
    name="CoverMetaLabel",
    fontName="DejaVuSans-Bold",
    fontSize=10.5,
    leading=14,
    textColor=NAVY,
    alignment=TA_LEFT,
))
styles.add(ParagraphStyle(
    name="CoverMetaValue",
    fontName="DejaVuSans",
    fontSize=10.5,
    leading=14,
    textColor=INK,
    alignment=TA_LEFT,
))
styles.add(ParagraphStyle(
    name="TOCHeading",
    fontName="DejaVuSans-Bold",
    fontSize=18,
    leading=22,
    textColor=NAVY,
    spaceAfter=10,
))
styles.add(ParagraphStyle(
    name="TOC1",
    fontName="DejaVuSans-Bold",
    fontSize=8.8,
    leading=10.8,
    leftIndent=0,
    firstLineIndent=0,
    spaceBefore=2,
))
styles.add(ParagraphStyle(
    name="TOC2",
    fontName="DejaVuSans",
    fontSize=7.8,
    leading=9.4,
    leftIndent=14,
    firstLineIndent=0,
))


def md_inline(text: str) -> str:
    """Convierte un subconjunto pequeño de Markdown a marcado de ReportLab."""
    text = str(text)
    text = text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
    text = re.sub(r"\*\*(.+?)\*\*", r"<b>\1</b>", text)
    text = re.sub(r"(?<!\*)\*(?!\*)(.+?)(?<!\*)\*(?!\*)", r"<i>\1</i>", text)
    text = re.sub(r"`(.+?)`", r'<font name="DejaVuSansMono">\1</font>', text)
    return text


def P(text: str, style: str = "Body") -> Paragraph:
    return Paragraph(md_inline(text), styles[style])


def bullets(items: list[str]) -> list[Paragraph]:
    return [
        Paragraph(md_inline(item), styles["Bullet"], bulletText="•")
        for item in items
    ]


def steps(items: list[str]) -> list[Paragraph]:
    result = []
    for i, item in enumerate(items, 1):
        result.append(Paragraph(f"<b>{i}.</b> " + md_inline(item), styles["Step"]))
    return result


def make_table(headers: list[str], rows: list[list[str]], widths_cm: list[float] | None = None,
               header_bg=NAVY, font_size: float | None = None) -> Table:
    if widths_cm is None:
        widths = [CONTENT_W / len(headers)] * len(headers)
    else:
        widths = [w * cm for w in widths_cm]
        total = sum(widths)
        if total > CONTENT_W:
            widths = [w * CONTENT_W / total for w in widths]

    data = [[Paragraph(md_inline(h), styles["TableHeader"]) for h in headers]]
    for row in rows:
        data.append([Paragraph(md_inline(str(cell)), styles["TableText"]) for cell in row])

    table = Table(data, colWidths=widths, repeatRows=1, hAlign="LEFT")
    table.setStyle(TableStyle([
        ("BACKGROUND", (0, 0), (-1, 0), header_bg),
        ("TEXTCOLOR", (0, 0), (-1, 0), colors.white),
        ("VALIGN", (0, 0), (-1, -1), "TOP"),
        ("GRID", (0, 0), (-1, -1), 0.45, GRID),
        ("LEFTPADDING", (0, 0), (-1, -1), 4),
        ("RIGHTPADDING", (0, 0), (-1, -1), 4),
        ("TOPPADDING", (0, 0), (-1, -1), 4),
        ("BOTTOMPADDING", (0, 0), (-1, -1), 4),
        ("ROWBACKGROUNDS", (0, 1), (-1, -1), [colors.white, SOFT]),
    ]))
    return table


def make_note(text: str) -> Paragraph:
    return Paragraph("<b>Nota.</b> " + md_inline(text), styles["Note"])


def make_code(text: str) -> Table:
    code = Preformatted(text.strip("\n"), styles["Code"])
    table = Table([[code]], colWidths=[CONTENT_W], hAlign="LEFT")
    table.setStyle(TableStyle([
        ("BACKGROUND", (0, 0), (-1, -1), colors.HexColor("#F2F4F7")),
        ("BOX", (0, 0), (-1, -1), 0.6, colors.HexColor("#D6DCE4")),
        ("LEFTPADDING", (0, 0), (-1, -1), 8),
        ("RIGHTPADDING", (0, 0), (-1, -1), 8),
        ("TOPPADDING", (0, 0), (-1, -1), 6),
        ("BOTTOMPADDING", (0, 0), (-1, -1), 6),
        ("VALIGN", (0, 0), (-1, -1), "TOP"),
    ]))
    return table


def make_image(path: str, caption: str, width_cm: float) -> list:
    image_path = ASSETS / path
    with PILImage.open(image_path) as im:
        w, h = im.size
    width = width_cm * cm
    height = width * h / w
    return [
        KeepTogether([
            Image(str(image_path), width=width, height=height, hAlign="CENTER"),
            Paragraph(md_inline(caption), styles["Caption"]),
        ])
    ]


def build_story(blocks: list[tuple]) -> list:
    story: list = []
    for block in blocks:
        kind = block[0]
        if kind == "h1":
            story.append(Paragraph(md_inline(block[1]), styles["H1"]))
        elif kind == "h2":
            story.append(Paragraph(md_inline(block[1]), styles["H2"]))
        elif kind == "h3":
            story.append(Paragraph(md_inline(block[1]), styles["H3"]))
        elif kind == "p":
            story.append(P(block[1]))
        elif kind == "bullets":
            story.extend(bullets(block[1]))
        elif kind == "steps":
            story.extend(steps(block[1]))
        elif kind == "table":
            _, headers, rows, widths = block
            story.append(Spacer(1, 3))
            story.append(make_table(headers, rows, widths))
            story.append(Spacer(1, 7))
        elif kind == "code":
            story.append(Spacer(1, 3))
            story.append(make_code(block[1]))
            story.append(Spacer(1, 8))
        elif kind == "note":
            story.append(make_note(block[1]))
        elif kind == "image":
            story.extend(make_image(block[1], block[2], block[3]))
        elif kind == "pagebreak":
            story.append(PageBreak())
        elif kind == "landscape":
            story.append(NextPageTemplate("landscape"))
            story.append(PageBreak())
        elif kind == "portrait":
            story.append(NextPageTemplate("portrait"))
            story.append(PageBreak())
        elif kind == "spacer":
            story.append(Spacer(1, block[1] * cm))
        else:
            raise ValueError(f"Bloque desconocido: {kind}")
    return story


# ---------------------------------------------------------------------------
# Plantilla de documento, portada y encabezados
# ---------------------------------------------------------------------------

class ManualDocTemplate(BaseDocTemplate):
    def __init__(self, filename: str, manual_label: str, cover: dict):
        self.manual_label = manual_label
        self.cover = cover
        self.toc = TableOfContents()
        self.toc.levelStyles = [styles["TOC1"], styles["TOC2"]]
        self.toc.dotsMinLevel = 0
        super().__init__(
            filename,
            pagesize=A4,
            leftMargin=MARGIN,
            rightMargin=MARGIN,
            topMargin=2.6 * cm,
            bottomMargin=2.0 * cm,
            title=f"{cover['title']} - Solitario Klondike",
            author="John Anibal Riascos",
            subject="Proyecto de Estructuras de Datos",
            creator="ReportLab",
        )
        cover_frame = Frame(
            MARGIN,
            2.0 * cm,
            CONTENT_W,
            PAGE_H - 2.0 * cm - 4.0 * cm,
            id="cover_frame",
            leftPadding=0,
            rightPadding=0,
            topPadding=0,
            bottomPadding=0,
        )
        portrait_frame = Frame(
            MARGIN,
            2.0 * cm,
            CONTENT_W,
            PAGE_H - 2.0 * cm - 2.6 * cm,
            id="portrait_frame",
            leftPadding=0,
            rightPadding=0,
            topPadding=0,
            bottomPadding=0,
        )
        land_frame = Frame(
            MARGIN,
            2.0 * cm,
            LAND_W - 2 * MARGIN,
            LAND_H - 2.0 * cm - 2.6 * cm,
            id="landscape_frame",
            leftPadding=0,
            rightPadding=0,
            topPadding=0,
            bottomPadding=0,
        )
        self.addPageTemplates([
            PageTemplate(id="cover", frames=[cover_frame], onPage=self.draw_cover),
            PageTemplate(id="portrait", frames=[portrait_frame], onPage=self.draw_header_footer),
            PageTemplate(id="landscape", frames=[land_frame], pagesize=landscape(A4),
                         onPage=self.draw_header_footer),
        ])

    def afterFlowable(self, flowable):
        if not isinstance(flowable, Paragraph):
            return
        style = flowable.style.name
        text = flowable.getPlainText()
        if style == "H1" and text != "Contenido":
            self.notify("TOCEntry", (0, text, self.page))
        elif style == "H2":
            self.notify("TOCEntry", (1, text, self.page))

    def draw_cover(self, canvas, doc):
        w, h = canvas._pagesize
        canvas.saveState()

        # Símbolos decorativos tenues (se dibujan antes de las bandas para
        # que la banda superior los recorte limpiamente).
        canvas.setFillColor(colors.HexColor("#E7EDF5"))
        canvas.setFont("DejaVuSans", 76)
        canvas.drawRightString(w - MARGIN, h - 5.1 * cm, "♠")
        canvas.setFont("DejaVuSans", 52)
        canvas.drawRightString(w - MARGIN - 1.4 * cm, h - 6.0 * cm, "♥")
        canvas.drawRightString(w - MARGIN - 3.2 * cm, h - 5.8 * cm, "♦")
        canvas.setFont("DejaVuSans", 64)
        canvas.drawRightString(w - MARGIN - 4.2 * cm, h - 6.3 * cm, "♣")

        # Banda superior.
        canvas.setFillColor(NAVY)
        canvas.rect(0, h - 3.2 * cm, w, 3.2 * cm, fill=1, stroke=0)
        canvas.setFillColor(colors.white)
        canvas.setFont("DejaVuSans-Bold", 11)
        canvas.drawCentredString(w / 2, h - 1.95 * cm, "PROYECTO DE ESTRUCTURAS DE DATOS")
        canvas.setStrokeColor(GOLD)
        canvas.setLineWidth(1.2)
        canvas.line(MARGIN + 4 * cm, h - 2.55 * cm, w - MARGIN - 4 * cm, h - 2.55 * cm)

        # Banda inferior.
        canvas.setFillColor(colors.HexColor("#EEF3F8"))
        canvas.rect(0, 0, w, 1.55 * cm, fill=1, stroke=0)
        canvas.setFillColor(MUTED)
        canvas.setFont("DejaVuSans", 8.5)
        canvas.drawString(MARGIN, 0.72 * cm, "John Anibal Riascos · Estructuras de Datos · Noviembre de 2026")
        canvas.drawRightString(w - MARGIN, 0.72 * cm, "Solitario Klondike · Versión 1.0")

        canvas.restoreState()

    def draw_header_footer(self, canvas, doc):
        w, h = canvas._pagesize
        canvas.saveState()
        canvas.setStrokeColor(colors.HexColor("#D6DCE4"))
        canvas.setLineWidth(0.5)
        canvas.line(MARGIN, h - 1.75 * cm, w - MARGIN, h - 1.75 * cm)

        canvas.setFillColor(MUTED)
        canvas.setFont("DejaVuSans", 7.8)
        canvas.drawString(MARGIN, h - 1.35 * cm,
                          "Solitario Klondike — Proyecto de Estructuras de Datos")
        canvas.drawRightString(w - MARGIN, h - 1.35 * cm, doc.manual_label)

        canvas.line(MARGIN, 1.55 * cm, w - MARGIN, 1.55 * cm)
        canvas.setFont("DejaVuSans", 7.8)
        canvas.drawString(MARGIN, 1.0 * cm, "John Anibal Riascos")
        canvas.drawRightString(w - MARGIN, 1.0 * cm, f"Página {doc.page}")
        canvas.restoreState()


def cover_story(cover: dict) -> list:
    meta_rows = [
        ["Estudiante", "John Anibal Riascos"],
        ["Asignatura", "Estructuras de Datos"],
        ["Programa / Institución", "______________________________"],
        ["Fecha", "20 de noviembre de 2026"],
        ["Versión", "1.0"],
    ]
    meta_data = [
        [Paragraph(md_inline(a), styles["CoverMetaLabel"]),
         Paragraph(md_inline(b), styles["CoverMetaValue"])]
        for a, b in meta_rows
    ]
    meta = Table(meta_data, colWidths=[5.2 * cm, 8.0 * cm], hAlign="CENTER")
    meta.setStyle(TableStyle([
        ("VALIGN", (0, 0), (-1, -1), "TOP"),
        ("LEFTPADDING", (0, 0), (-1, -1), 6),
        ("RIGHTPADDING", (0, 0), (-1, -1), 6),
        ("TOPPADDING", (0, 0), (-1, -1), 5),
        ("BOTTOMPADDING", (0, 0), (-1, -1), 5),
        ("LINEBELOW", (0, 0), (-1, -2), 0.35, colors.HexColor("#D6DCE4")),
    ]))

    return [
        Spacer(1, 0.25 * cm),
        Paragraph(md_inline(cover["title"]), styles["CoverTitle"]),
        Paragraph(md_inline(cover["subtitle"]), styles["CoverSubtitle"]),
        Paragraph(md_inline(cover["tagline"]), styles["CoverSubtitle2"]),
        Spacer(1, 0.55 * cm),
        HRFlowable(width="58%", thickness=1.1, color=GOLD, spaceBefore=2, spaceAfter=16,
                   hAlign="CENTER"),
        meta,
        Spacer(1, 0.55 * cm),
        Paragraph(md_inline(cover["note"]), styles["Note"]),
        Spacer(1, 0.45 * cm),
        Paragraph(
            '<font color="#5B6673" size="8.5">Repositorio: '
            'https://github.com/AnibalRiascos/solitario-klondike</font>',
            styles["BodyLeft"],
        ),
        NextPageTemplate("portrait"),
        PageBreak(),
    ]


def build_pdf(filename: Path, cover: dict, blocks: list[tuple], manual_label: str) -> None:
    doc = ManualDocTemplate(str(filename), manual_label, cover)
    story = cover_story(cover)
    story.append(Paragraph("Contenido", styles["TOCHeading"]))
    story.append(doc.toc)
    story.append(PageBreak())
    story.extend(build_story(blocks))
    doc.multiBuild(story)


# ---------------------------------------------------------------------------
# Versiones Markdown editables
# ---------------------------------------------------------------------------

def markdown_for(cover: dict, blocks: list[tuple]) -> str:
    lines: list[str] = []
    lines.append(f"# {cover['title']}")
    lines.append("")
    lines.append(f"## {cover['subtitle']}")
    lines.append("")
    lines.append(f"*{cover['tagline']}*")
    lines.append("")
    lines.append("| Campo | Detalle |")
    lines.append("|---|---|")
    lines.append("| Estudiante | John Anibal Riascos |")
    lines.append("| Asignatura | Estructuras de Datos |")
    lines.append("| Programa / Institución | ______________________________ |")
    lines.append("| Fecha | 20 de noviembre de 2026 |")
    lines.append("| Versión | 1.0 |")
    lines.append("")
    lines.append(f"> {cover['note']}")
    lines.append("")
    lines.append("---")
    lines.append("")

    headings = []
    for block in blocks:
        if block[0] == "h1":
            headings.append((1, block[1]))
        elif block[0] == "h2":
            headings.append((2, block[1]))
    lines.append("## Contenido")
    lines.append("")
    for level, text in headings:
        indent = "  " * (level - 1)
        lines.append(f"{indent}- {text}")
    lines.append("")
    lines.append("---")
    lines.append("")

    for block in blocks:
        kind = block[0]
        if kind == "h1":
            lines.append(f"# {block[1]}")
            lines.append("")
        elif kind == "h2":
            lines.append(f"## {block[1]}")
            lines.append("")
        elif kind == "h3":
            lines.append(f"### {block[1]}")
            lines.append("")
        elif kind == "p":
            lines.append(block[1])
            lines.append("")
        elif kind == "bullets":
            for item in block[1]:
                lines.append(f"- {item}")
            lines.append("")
        elif kind == "steps":
            for i, item in enumerate(block[1], 1):
                lines.append(f"{i}. {item}")
            lines.append("")
        elif kind == "table":
            _, headers, rows, _widths = block
            lines.append("| " + " | ".join(headers) + " |")
            lines.append("|" + "|".join(["---"] * len(headers)) + "|")
            for row in rows:
                cells = [str(c).replace("|", "\\|") for c in row]
                lines.append("| " + " | ".join(cells) + " |")
            lines.append("")
        elif kind == "code":
            lines.append("```text")
            lines.append(block[1].strip("\n"))
            lines.append("```")
            lines.append("")
        elif kind == "note":
            lines.append(f"> **Nota.** {block[1]}")
            lines.append("")
        elif kind == "image":
            lines.append(f"![{block[2]}](assets/{block[1]})")
            lines.append("")
            lines.append(f"*{block[2]}*")
            lines.append("")
        elif kind == "pagebreak":
            lines.append("---")
            lines.append("")
        elif kind in ("landscape", "portrait", "spacer"):
            continue
        else:
            raise ValueError(f"Bloque desconocido en Markdown: {kind}")

    lines.append("")
    lines.append("---")
    lines.append("")
    lines.append("*Documento generado para el proyecto Solitario Klondike. "
                 "John Anibal Riascos — Noviembre de 2026.*")
    lines.append("")
    return "\n".join(lines)


def main() -> None:
    from manual_user_content import COVER as USER_COVER, BLOCKS as USER_BLOCKS
    from manual_tech_content import COVER as TECH_COVER, BLOCKS as TECH_BLOCKS

    build_pdf(BASE / "Manual_de_Usuario_Solitario_Klondike.pdf",
              USER_COVER, USER_BLOCKS, "Manual de Usuario")
    build_pdf(BASE / "Manual_Tecnico_Solitario_Klondike.pdf",
              TECH_COVER, TECH_BLOCKS, "Manual Técnico")

    (BASE / "Manual_de_Usuario_Solitario_Klondike.md").write_text(
        markdown_for(USER_COVER, USER_BLOCKS), encoding="utf-8")
    (BASE / "Manual_Tecnico_Solitario_Klondike.md").write_text(
        markdown_for(TECH_COVER, TECH_BLOCKS), encoding="utf-8")

    print("PDF y Markdown generados en", BASE)


if __name__ == "__main__":
    main()
