package Clases.ClasesFinanzas;
import Clases.ConexionBD.Entidades_CRUD.DAO_Alumno;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.*;
import java.net.URL;

public class ComprobantePagoPDF {
    private Document documento;
    private FileOutputStream fileOutputStream;
    private String codigoAlumno;
    private String fechaPago;
    private String metodoPago;
    private String concepto = "Pago de cuota mensual";
    private double importe;

    public ComprobantePagoPDF(String codigoAlumno, String fechaPago, String metodoPago, double importe) {
        this.codigoAlumno = codigoAlumno;
        this.fechaPago = fechaPago;
        this.metodoPago = metodoPago;
        this.importe = importe;
    }

    //Fuentes para el documento
    Font fuenteSubtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 7, Font.BOLD);
    Font fuenteParrafo = FontFactory.getFont(FontFactory.HELVETICA, 6);
    Font fuenteComponentes = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 6, Font.BOLD);
    Font fuenteTabla = FontFactory.getFont(FontFactory.COURIER_BOLD, 7, Font.BOLD);
    Font fuentePiePagina = FontFactory.getFont(FontFactory.COURIER_BOLD, 6, Font.BOLD);

    //Metodo para crear el documento
    private void CrearDocumento() throws FileNotFoundException, DocumentException {
        documento = new Document(PageSize.A6, 35, 30, 30, 30);
        String rutaArchivo = "comprobantesDePago";
        java.io.File directorio = new java.io.File(rutaArchivo);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        fileOutputStream = new FileOutputStream(rutaArchivo + "\\ComprobanteDePago_" + codigoAlumno + "-" + fechaPago + ".pdf");
        PdfWriter.getInstance(documento, fileOutputStream);
    }

    //Metodo para abrir el documento
    private void AbrirDocumento() {
        documento.open();
    }

    //Metodo para agregar encabezado
    private void AgregarEncabezado() throws DocumentException, IOException {
        PdfPTable tablaEncabezado = new PdfPTable(1);
        tablaEncabezado.setWidthPercentage(100);
        tablaEncabezado.setWidths(new float[]{1});

        String urlImagen = "https://i.imgur.com/47xf7CD.png";

        try {
            URL url = new URL(urlImagen);
            InputStream logoStream = url.openStream();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = logoStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }

            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            Image imagen = Image.getInstance(imageBytes);
            imagen.scaleToFit(80, 80);
            imagen.setAlignment(Element.ALIGN_CENTER);

            PdfPCell celdaLogo = new PdfPCell(imagen);
            celdaLogo.setBorder(Rectangle.NO_BORDER);
            celdaLogo.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaLogo.setVerticalAlignment(Element.ALIGN_CENTER);
            tablaEncabezado.addCell(celdaLogo);
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen desde la URL: " + e.getMessage());
        }

        documento.add(tablaEncabezado);

        agregarSaltosDeLinea();
        agregarSaltosDeLinea();

        PdfPTable tablaInfoColegio = new PdfPTable(1);
        tablaInfoColegio.setWidthPercentage(100);

        PdfPCell celdaInfo = new PdfPCell();
        celdaInfo.setBorder(Rectangle.NO_BORDER);
        celdaInfo.setHorizontalAlignment(Element.ALIGN_CENTER);

        Paragraph infoColegio = new Paragraph();
        infoColegio.setAlignment(Element.ALIGN_CENTER);
        infoColegio.add(new Phrase("INSTITUCIÓN EDUCATIVA\n", fuenteSubtitulo));
        infoColegio.add(new Phrase("\"THE FOREST COLLEGE\"\n", fuenteSubtitulo));

        celdaInfo.addElement(infoColegio);
        tablaInfoColegio.addCell(celdaInfo);

        documento.add(tablaInfoColegio);

        tablaEncabezado.setSpacingAfter(20);

        agregarSaltosDeLinea();
        agregarSaltosDeLinea();
    }

    private void infoAdicionalDelColegio() throws DocumentException {
        PdfPTable tablaInfoColegio = new PdfPTable(1);
        tablaInfoColegio.setWidthPercentage(100);

        PdfPCell celdaInfo = new PdfPCell();
        celdaInfo.setBorder(Rectangle.NO_BORDER);
        celdaInfo.setHorizontalAlignment(Element.ALIGN_LEFT);

        Paragraph infoColegio = new Paragraph();

        infoColegio.add(new Phrase("", fuenteParrafo));
        infoColegio.add(new Phrase("RUC: 20123456789\n", fuenteParrafo));
        infoColegio.add(new Phrase("Av. Bolognesi 123 - Chiclayo\n", fuenteParrafo));
        infoColegio.add(new Phrase("Teléfono: (074) 123-456\n", fuenteParrafo));
        infoColegio.add(new Phrase("", fuenteParrafo));
        infoColegio.add(new Phrase("", fuenteParrafo));

        celdaInfo.addElement(infoColegio);

        tablaInfoColegio.addCell(celdaInfo);

        documento.add(tablaInfoColegio);

        tablaInfoColegio.setSpacingAfter(20);
    }

    //Metodo para agregar el título
    private void agregarTitulo(String titulo) throws DocumentException {
        PdfPTable tablaTitulo = new PdfPTable(1);
        tablaTitulo.setWidthPercentage(100);
        PdfPCell celda = new PdfPCell(new Phrase(titulo, fuenteTabla));
        celda.setBorder(Rectangle.BOX);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celda.setPadding(4);

        BaseColor colorFondo = new BaseColor(122, 149, 106);

        celda.setBackgroundColor(colorFondo);
        tablaTitulo.addCell(celda);
        documento.add(tablaTitulo);
    }

    //Metodo para agregar información del alumno
    private void agregarInfoEstudiante() throws DocumentException {
        DAO_Alumno daoAlumno = new DAO_Alumno();
        String[] datosAlumno = daoAlumno.obtenerDatosAlumno(codigoAlumno);

        PdfPTable tablaInfo = new PdfPTable(2);
        tablaInfo.setWidthPercentage(100);
        tablaInfo.setWidths(new float[]{1, 2});

        String[] etiquetas = {"Estudiante:", "Código:", "Grado:", "Sección:", "Año Académico:"};

        for (int i = 0; i < etiquetas.length; i++) {
            PdfPCell celdaEtiqueta = new PdfPCell(new Phrase(etiquetas[i], fuenteComponentes));
            celdaEtiqueta.setBorder(Rectangle.NO_BORDER);
            celdaEtiqueta.setHorizontalAlignment(Element.ALIGN_LEFT);
            celdaEtiqueta.setPadding(3);

            PdfPCell celdaValor = new PdfPCell(new Phrase(datosAlumno[i] != null ? datosAlumno[i] : "No disponible", fuenteParrafo));
            celdaValor.setBorder(Rectangle.NO_BORDER);
            celdaValor.setHorizontalAlignment(Element.ALIGN_LEFT);
            celdaValor.setPadding(3);

            tablaInfo.addCell(celdaEtiqueta);
            tablaInfo.addCell(celdaValor);
        }
        documento.add(tablaInfo);
    }

    //Metodo para agregar información del pago
    private void agregarInfoDelPago() throws DocumentException {
        PdfPTable tablaInfo = new PdfPTable(2);
        tablaInfo.setWidthPercentage(100);
        tablaInfo.setWidths(new float[]{1, 2});

        String[] etiquetas = {"Fecha de Pago:", "Método de Pago:", "Concepto:", "Importe:"};
        String[] valores = {
                fechaPago.toString(),
                metodoPago,
                concepto,
                String.format("S/ %.2f", importe)
        };

        for (int i = 0; i < etiquetas.length; i++) {
            PdfPCell celdaEtiqueta = new PdfPCell(new Phrase(etiquetas[i], fuenteComponentes));
            celdaEtiqueta.setBorder(Rectangle.NO_BORDER);
            celdaEtiqueta.setHorizontalAlignment(Element.ALIGN_LEFT);
            celdaEtiqueta.setPadding(3);

            PdfPCell celdaValor = new PdfPCell(new Phrase(valores[i] != null ? valores[i] : "N/A", fuenteParrafo));
            celdaValor.setBorder(Rectangle.NO_BORDER);
            celdaValor.setHorizontalAlignment(Element.ALIGN_LEFT);
            celdaValor.setPadding(3);

            tablaInfo.addCell(celdaEtiqueta);
            tablaInfo.addCell(celdaValor);
        }

        documento.add(tablaInfo);
    }

    //Metodo para agregar saltos de línea
    private void agregarSaltosDeLinea() throws DocumentException {
        documento.add(Chunk.NEWLINE);
    }

    //Metodo para agregar linea separadora
    private void agregarLineaSeparacion() throws DocumentException {
        LineSeparator linea = new LineSeparator();
        linea.setLineColor(BaseColor.GRAY);
        linea.setLineWidth(0.5f);
        documento.add(linea);
    }

    //Metodo para agregar pie de página
    private void agregarPiePagina() throws DocumentException {
        Paragraph piePagina = new Paragraph();
        piePagina.add(new Phrase("Gracias por su pago puntual.\n", fuentePiePagina));
        piePagina.add(new Phrase("Este comprobante es válido sin firma ni sello.\n", fuentePiePagina));
        piePagina.add(new Phrase("Comprobante N° " + codigoAlumno, fuentePiePagina));
        piePagina.setAlignment(Element.ALIGN_CENTER);
        documento.add(piePagina);
    }

    //Metodo para cerrar el documento
    private void cerrarDocumento() {
        documento.close();
    }

    private void agregarEspacioHorizontal() throws DocumentException {
        PdfPTable tablaEspacio = new PdfPTable(3);
        tablaEspacio.setWidthPercentage(100);

        for (int i = 0; i < 3; i++) {
            PdfPCell celdaVacia = new PdfPCell(new Phrase(""));
            celdaVacia.setBorder(Rectangle.NO_BORDER);
            celdaVacia.setFixedHeight(10f);
            tablaEspacio.addCell(celdaVacia);
        }
        documento.add(tablaEspacio);
    }

    //Metodo principal para generar el comprobante
    public void generarComprobante() {
        try {
            CrearDocumento();
            AbrirDocumento();
            AgregarEncabezado();
            infoAdicionalDelColegio();
            agregarEspacioHorizontal();
            agregarTitulo("COMPROBANTE DE PAGO N° " + codigoAlumno);
            agregarInfoEstudiante();
            agregarInfoDelPago();
            agregarSaltosDeLinea();
            agregarEspacioHorizontal();
            agregarLineaSeparacion();
            agregarPiePagina();
        } catch (Exception e) {
            System.err.println("Error al generar comprobante: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarDocumento();
        }
    }
}
