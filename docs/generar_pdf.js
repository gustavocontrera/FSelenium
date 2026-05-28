const fs = require('fs');
const { mdToPdf } = require('md-to-pdf');
const path = require('path');

// Archivos en orden de aparición para el manual final
const archivosMD = [
    'fundamentos-java.md',
    'curso-completo-selenium.md',
    'manual-localizadores.md',
    'tips-explicaciones.md'
];

async function generarPDF() {
    try {
        console.log("Iniciando la lectura de manuales...");
        let contenidoUnificado = "# Manual Definitivo: FSeleniumIA\n\n";

        for (const archivo of archivosMD) {
            const ruta = path.join(__dirname, archivo);
            if (fs.existsSync(ruta)) {
                console.log(`- Agregando: ${archivo}`);
                const contenido = fs.readFileSync(ruta, 'utf-8');
                // Agregar un salto de página para el PDF
                contenidoUnificado += contenido + "\n\n<div class=\"page-break\"></div>\n\n";
            } else {
                console.log(`- Advertencia: No se encontró ${archivo}`);
            }
        }

        const rutaSalida = path.join(__dirname, 'Manual-FSeleniumIA.pdf');
        console.log("\nGenerando PDF (esto puede tomar unos segundos)...");

        await mdToPdf(
            { content: contenidoUnificado },
            { 
                dest: rutaSalida,
                pdf_options: { 
                    format: 'A4', 
                    margin: '20mm',
                    printBackground: true
                },
                css: `
                    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; line-height: 1.6; }
                    h1, h2, h3 { color: #2c3e50; }
                    .page-break { page-break-after: always; }
                    pre { background-color: #f8f9fa; border: 1px solid #e9ecef; padding: 15px; border-radius: 5px; }
                    code { color: #d63384; font-weight: bold; }
                    blockquote { border-left: 4px solid #0d6efd; padding-left: 15px; color: #6c757d; }
                `
            }
        );

        console.log(`\n¡Éxito! PDF generado correctamente en: ${rutaSalida}`);

    } catch (error) {
        console.error("Error al generar el PDF:", error);
    }
}

generarPDF();
