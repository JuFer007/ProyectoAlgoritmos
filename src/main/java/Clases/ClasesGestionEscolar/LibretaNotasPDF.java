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

    // Variables para los datos del alumno
    private String nombreAlumno;
    private String grado;
    private String seccion;
    private String añoEscolar;

    // Almacenamiento de los cursos y notas
    private ArrayList<String> cursos;
    private ArrayList<Object[]> notasYCursos;

    private Random random = new Random();

    // Constructor que solo recibe el código del alumno y el id de matricula
    public LibretaNotasPDF(Document documento, FileOutputStream fileOutputStream, String codigoAlumno, int idMatricula) {
        this.documento = documento;
        this.fileOutputStream = fileOutputStream;
        this.codigoAlumno = codigoAlumno;

        // Llamar al DAO para obtener los datos del alumno
        String[] datosAlumno = obtenerDatosAlumno(codigoAlumno);
        this.nombreAlumno = datosAlumno[0];
        this.grado = datosAlumno[2];
        this.seccion = datosAlumno[3];
        this.añoEscolar = datosAlumno[4];

        // Llamar al DAO de Curso para obtener los cursos del alumno
        this.cursos = listarCursos(idMatricula);

        // Llamar al DAO de Nota para obtener las notas de los cursos
        this.notasYCursos = new ArrayList<>();
        for (String curso : cursos) {
            ArrayList<Object[]> notasCurso = listarNotasDeUnSoloCurso(idMatricula, curso);
            notasYCursos.addAll(notasCurso);
        }
    }

    // Fuentes para el documento con tamaño de letra aumentado
    Font fuenteSubtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Font.BOLD);
    Font fuenteParrafo = FontFactory.getFont(FontFactory.HELVETICA, 9);
    Font fuenteComponentes = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Font.BOLD);
    Font fuentePiePagina = FontFactory.getFont(FontFactory.COURIER_BOLD, 8, Font.BOLD);

    // Método para crear el documento con tamaño fijo (1032x780 píxeles)
    private void CrearDocumento() throws FileNotFoundException, DocumentException {
        float width = 900 * 72 / 96;  // 780 puntos
        float height = 750 * 72 / 96; // 585 puntos

        // Crear el documento con el tamaño fijo de 1032x780 píxeles (780x585 puntos)
        documento = new Document(new Rectangle(width, height), 85, 40, 30, 30);
        String rutaArchivo = "libretasDeNotas";
        java.io.File directorio = new java.io.File(rutaArchivo);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        fileOutputStream = new FileOutputStream(rutaArchivo + "\\LibretaDeNotas_" + codigoAlumno + ".pdf");
        PdfWriter.getInstance(documento, fileOutputStream);
    }

    // Método para abrir el documento
    private void AbrirDocumento() {
        documento.open();
    }

    // Método para agregar encabezado
    private void AgregarEncabezado() throws DocumentException, IOException {
        // Agregar el logo centrado
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
            imagen.scaleToFit(80, 80); // Ajustar el tamaño del logo
            imagen.setAlignment(Element.ALIGN_CENTER); // Alineamos el logo al centro
            documento.add(imagen);  // Agregar el logo al documento
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen desde la URL: " + e.getMessage());
        }

        // Agregar el nombre del colegio como texto centrado
        Paragraph infoColegio = new Paragraph("INSTITUCIÓN EDUCATIVA: THE FOREST COLLEGE", fuenteSubtitulo);
        infoColegio.setAlignment(Element.ALIGN_CENTER); // Centrar el texto del nombre del colegio
        documento.add(infoColegio);

        agregarSaltosDeLinea();

        // Agregar el nombre de la boleta
        String boletaTitulo = "LIBRETA DE NOTAS N° " + (random.nextInt(1000000) + 1);
        Paragraph nombreLibreta = new Paragraph(boletaTitulo, fuenteSubtitulo);
        nombreLibreta.setAlignment(Element.ALIGN_CENTER); // Centrar el texto del nombre de la boleta
        documento.add(nombreLibreta);

        // Agregar saltos de línea
        agregarSaltosDeLinea();
    }

    // Método para obtener los datos del alumno desde el DAO
    public String[] obtenerDatosAlumno(String codigoAlumno) {
        // Llamar al DAO para obtener los datos del alumno
        DAO_Alumno daoAlumno = new DAO_Alumno();
        return daoAlumno.obtenerDatosAlumno(codigoAlumno);
    }

    // Método para obtener los cursos del alumno desde el DAO
    public ArrayList<String> listarCursos(int idMatricula) {
        // Llamar al DAO para obtener los cursos
        DAO_Nota daoNota = new DAO_Nota();
        return daoNota.listarCursos(idMatricula);
    }

    // Método para obtener las notas de un solo curso desde el DAO
    public ArrayList<Object[]> listarNotasDeUnSoloCurso(int idMatricula, String nombreCurso) {
        // Llamar al DAO para obtener las notas de un curso específico
        DAO_Nota daoNota = new DAO_Nota();
        return daoNota.listarNotasDeUnSoloCurso(idMatricula, nombreCurso);
    }

    // Método para agregar información del estudiante
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

    // Método para agregar la tabla de asignaturas, notas y promedio
    private void agregarTablaNotas() throws DocumentException {
        PdfPTable tablaNotas = new PdfPTable(3); // 3 columnas: Asignatura, Nota, Tipo Nota
        tablaNotas.setWidthPercentage(90); // Ajustar el ancho de la tabla
        tablaNotas.setWidths(new float[]{4, 1.5f, 1.5f}); // Ajustar proporción de las columnas

        // Encabezado de la tabla
        tablaNotas.addCell(new PdfPCell(new Phrase("Asignatura", fuenteComponentes)));
        tablaNotas.addCell(new PdfPCell(new Phrase("Nota", fuenteComponentes)));
        tablaNotas.addCell(new PdfPCell(new Phrase("Tipo Nota", fuenteComponentes)));

        // Añadir los cursos y notas
        for (Object[] cursoNota : notasYCursos) {
            tablaNotas.addCell(new PdfPCell(new Phrase((String) cursoNota[0], fuenteParrafo))); // Nombre del curso
            tablaNotas.addCell(new PdfPCell(new Phrase(String.valueOf(cursoNota[1]), fuenteParrafo))); // Nota
            tablaNotas.addCell(new PdfPCell(new Phrase((String) cursoNota[2], fuenteParrafo))); // Tipo de nota
        }

        documento.add(tablaNotas);
    }

    // Método para agregar conclusión descriptiva
    private void agregarConclusión() throws DocumentException {
        Paragraph conclusion = new Paragraph("Conclusión descriptiva: Este es el rendimiento general del estudiante en sus asignaturas matriculadas.", fuenteParrafo);
        conclusion.setAlignment(Element.ALIGN_CENTER);
        documento.add(conclusion);
    }

    // Método para agregar pie de página
    private void agregarPiePagina() throws DocumentException {
        Paragraph piePagina = new Paragraph();
        piePagina.add(new Phrase("Gracias por confiar en nuestra institución.\n", fuentePiePagina));
        piePagina.add(new Phrase("Este documento es un resumen de las notas del semestre.\n", fuentePiePagina));
        piePagina.add(new Phrase("Libreta de Notas N° " + (random.nextInt(1000000) + 1), fuentePiePagina)); // Número de boleta aleatorio
        piePagina.setAlignment(Element.ALIGN_LEFT);
        documento.add(piePagina);
    }

    // Método para agregar saltos de línea
    private void agregarSaltosDeLinea() throws DocumentException {
        documento.add(Chunk.NEWLINE);
    }

    // Método para cerrar el documento
    private void cerrarDocumento() {
        documento.close();
    }

    // Método principal para generar la libreta
    public void generarLibreta() {
        try {
            CrearDocumento();
            AbrirDocumento();
            AgregarEncabezado();
            agregarInfoEstudiante();
            agregarTablaNotas();
            agregarSaltosDeLinea();
            agregarConclusión();
            agregarPiePagina();
        } catch (Exception e) {
            System.err.println("Error al generar libreta de notas: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarDocumento();
        }
    }
}
