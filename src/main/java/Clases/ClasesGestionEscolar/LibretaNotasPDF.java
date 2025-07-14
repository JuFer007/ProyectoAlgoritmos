package Clases.ClasesGestionEscolar;
import Clases.ConexionBD.Entidades_CRUD.DAO_Alumno;
import Clases.ConexionBD.Entidades_CRUD.DAO_Nota;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class LibretaNotasPDF {
    private Document documento;
    private FileOutputStream fileOutputStream;
    private String codigoAlumno;
    private String nombreAlumno;
    private String grado;
    private String seccion;
    private final String añoEscolar = "2025";
    private ArrayList<String> cursos;
    private ArrayList<Object[]> notasYCursos;
    private Random random = new Random();
    private int numeroLibreta;

    public LibretaNotasPDF(String codigoAlumno, int idMatricula) {
        this.codigoAlumno = codigoAlumno;
        this.numeroLibreta = random.nextInt(1000000) + 1;
        String[] datosAlumno = obtenerDatosAlumno(codigoAlumno);
        this.nombreAlumno = datosAlumno[0];
        this.grado = datosAlumno[2];
        this.seccion = datosAlumno[3];
        DAO_Nota daoNota = new DAO_Nota();
        this.notasYCursos = daoNota.obtenerNotasDeCursos(idMatricula);
        this.cursos = listarCursos(idMatricula);
    }

    //Fuentes para el documento con tamaño de letra aumentado
    Font fuenteSubtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Font.BOLD);
    Font fuenteParrafo = FontFactory.getFont(FontFactory.HELVETICA, 9);
    Font fuenteComponentes = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Font.BOLD);
    Font fuentePiePagina = FontFactory.getFont(FontFactory.COURIER_BOLD, 8, Font.BOLD);

    //Metodo para crear el documento con tamaño fijo (1032x780 píxeles)
    private void CrearDocumento() throws FileNotFoundException, DocumentException {
        float width = 850 * 72 / 96;
        float height = 650 * 72 / 96;

        documento = new Document(new Rectangle(width, height), 85, 40, 30, 30);
        String rutaArchivo = "libretasDeNotas";
        java.io.File directorio = new java.io.File(rutaArchivo);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        fileOutputStream = new FileOutputStream(rutaArchivo + "\\LibretaDeNotas_" + codigoAlumno + "-" + añoEscolar + ".pdf");
        PdfWriter.getInstance(documento, fileOutputStream);
    }

    //Metodo para abrir el documento
    private void AbrirDocumento() {
        documento.open();
    }

    //Metodo para agregar encabezado
    private void AgregarEncabezado() throws DocumentException, IOException {
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
            documento.add(imagen);
            agregarSaltosDeLinea();
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen desde la URL: " + e.getMessage());
        }
        agregarSaltosDeLinea();
        Paragraph infoColegio = new Paragraph("INSTITUCIÓN EDUCATIVA: THE FOREST COLLEGE", fuenteSubtitulo);
        infoColegio.setAlignment(Element.ALIGN_CENTER);
        documento.add(infoColegio);
        agregarSaltosDeLinea();
        String boletaTitulo = "LIBRETA DE NOTAS N° " + numeroLibreta;
        Paragraph nombreLibreta = new Paragraph(boletaTitulo, fuenteSubtitulo);
        nombreLibreta.setAlignment(Element.ALIGN_CENTER);
        documento.add(nombreLibreta);
        agregarSaltosDeLinea();
    }

    //Metodo para obtener los datos del alumno desde el DAO
    public String[] obtenerDatosAlumno(String codigoAlumno) {
        DAO_Alumno daoAlumno = new DAO_Alumno();
        return daoAlumno.obtenerDatosAlumno(codigoAlumno);
    }

    //Metodo para obtener los cursos del alumno desde el DAO
    public ArrayList<String> listarCursos(int idMatricula) {
        DAO_Nota daoNota = new DAO_Nota();
        return daoNota.listarCursos(idMatricula);
    }

    //Metodo para agregar información del estudiante
    private void agregarInfoEstudiante() throws DocumentException {
        PdfPTable tablaInfo = new PdfPTable(2);
        tablaInfo.setWidthPercentage(100);
        tablaInfo.setWidths(new float[]{1, 2});

        String[] etiquetas = {"Código Estudiante:", "Nombres y Apellidos:", "Año Escolar:", "Grado:", "Sección:"};
        String[] valores = {codigoAlumno, nombreAlumno, añoEscolar, grado, seccion};

        for (int i = 0; i < etiquetas.length; i++) {
            PdfPCell celdaEtiqueta = new PdfPCell(new Phrase(etiquetas[i], fuenteComponentes));
            celdaEtiqueta.setBorder(Rectangle.NO_BORDER);
            celdaEtiqueta.setHorizontalAlignment(Element.ALIGN_LEFT);
            celdaEtiqueta.setPadding(3);

            PdfPCell celdaValor = new PdfPCell(new Phrase(valores[i] != null ? valores[i] : "No disponible", fuenteParrafo));
            celdaValor.setBorder(Rectangle.NO_BORDER);
            celdaValor.setHorizontalAlignment(Element.ALIGN_LEFT);
            celdaValor.setPadding(3);

            tablaInfo.addCell(celdaEtiqueta);
            tablaInfo.addCell(celdaValor);
        }
        documento.add(tablaInfo);
    }

    //Metodo para agregar la tabla de asignaturas, notas y promedio
    private void agregarTablaNotas() throws DocumentException {
        PdfPTable tablaNotas = new PdfPTable(5);
        tablaNotas.setWidthPercentage(90);
        tablaNotas.setWidths(new float[]{4, 1.5f, 1.5f, 1.5f, 1.5f});

        BaseColor colorEncabezado = new BaseColor(122, 149, 106);

        // Encabezado de la tabla con fondo de color
        PdfPCell celdaAsignatura = new PdfPCell(new Phrase("Asignatura", fuenteComponentes));
        celdaAsignatura.setBackgroundColor(colorEncabezado);
        celdaAsignatura.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaNotas.addCell(celdaAsignatura);

        PdfPCell celdaPrimeraNota = new PdfPCell(new Phrase("Primera Nota Parcial", fuenteComponentes));
        celdaPrimeraNota.setBackgroundColor(colorEncabezado);
        celdaPrimeraNota.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaNotas.addCell(celdaPrimeraNota);

        PdfPCell celdaSegundaNota = new PdfPCell(new Phrase("Segunda Nota Parcial", fuenteComponentes));
        celdaSegundaNota.setBackgroundColor(colorEncabezado);
        celdaSegundaNota.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaNotas.addCell(celdaSegundaNota);

        PdfPCell celdaTerceraNota = new PdfPCell(new Phrase("Tercera Nota Parcial", fuenteComponentes));
        celdaTerceraNota.setBackgroundColor(colorEncabezado);
        celdaTerceraNota.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaNotas.addCell(celdaTerceraNota);

        PdfPCell celdaPromedio = new PdfPCell(new Phrase("Promedio", fuenteComponentes));
        celdaPromedio.setBackgroundColor(colorEncabezado);
        celdaPromedio.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaNotas.addCell(celdaPromedio);

        for (Object[] cursoNota : notasYCursos) {
            tablaNotas.addCell(new PdfPCell(new Phrase((String) cursoNota[0], fuenteParrafo))); // Nombre del curso
            tablaNotas.addCell(new PdfPCell(new Phrase(String.valueOf(cursoNota[1]), fuenteParrafo))); // Primera Nota Parcial
            tablaNotas.addCell(new PdfPCell(new Phrase(String.valueOf(cursoNota[2]), fuenteParrafo))); // Segunda Nota Parcial
            tablaNotas.addCell(new PdfPCell(new Phrase(String.valueOf(cursoNota[3]), fuenteParrafo))); // Tercera Nota Parcial
            tablaNotas.addCell(new PdfPCell(new Phrase(String.valueOf(cursoNota[4]), fuenteParrafo))); // Promedio
        }
        tablaNotas.setHorizontalAlignment(Element.ALIGN_LEFT);
        documento.add(tablaNotas);
    }

    //Metodo para agregar pie de página con el mismo número de libreta
    private void agregarPiePagina() throws DocumentException {
        Paragraph piePagina = new Paragraph();
        piePagina.add(new Phrase("Gracias por confiar en nuestra institución educativa.\n", fuentePiePagina));
        piePagina.add(new Phrase("Este documento es un resumen de las notas del año escolar 2025.\n", fuentePiePagina));
        piePagina.add(new Phrase("Libreta de Notas N° " + numeroLibreta, fuentePiePagina)); // Número de boleta aleatorio, el mismo que en el encabezado
        piePagina.setAlignment(Element.ALIGN_LEFT);
        documento.add(piePagina);
    }

    //Metodo para agregar saltos de línea
    private void agregarSaltosDeLinea() throws DocumentException {
        documento.add(Chunk.NEWLINE);
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

    //Metodo para cerrar el documento
    private void cerrarDocumento() {
        documento.close();
    }

    //Metodo para generar PDF directamente
    public void generarLibretaPDF(String codigoAlumno, int idMatricula) {
        try {
            CrearDocumento();
            AbrirDocumento();
            AgregarEncabezado();
            agregarInfoEstudiante();
            agregarSaltosDeLinea();
            agregarTablaNotas();
            agregarSaltosDeLinea();
            agregarPiePagina();
        } catch (Exception e) {
            System.err.println("Error al generar libreta de notas: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarDocumento();
        }
    }
}
