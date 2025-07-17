package Clases.ClasesFinanzas;
import Clases.ConexionBD.Entidades_CRUD.DAO_Reportes;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.*;
import java.io.File;
import java.io.IOException;
import java.lang.Number;
import java.util.ArrayList;

public class ExportarEXCEL {
    public void exportarExcel(int numeroMes) {
        // Crear un arreglo con los nombres de los meses
        String[] meses = {
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        };

        // Validar el parámetro numeroMes
        if (numeroMes < 1 || numeroMes > 12) {
            return;
        }

        String nombreMes = meses[numeroMes - 1];

        String rutaDestino = "reporteExcel";

        File folder = new File(rutaDestino);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        DAO_Reportes dao = new DAO_Reportes();

        try {
            dao.listarCuotas(numeroMes);
            ArrayList<Object[]> lista = dao.getListapagos();

            if (lista == null || lista.isEmpty()) {
                System.out.println("No hay pagos registrados para el mes de " + nombreMes + ".");
                return;
            }

            String fileName = "ReporteDelMesDe_" + nombreMes + "_" +
                    java.time.LocalDate.now().getYear() + ".xls";

            File destino = new File(folder, fileName);
            WritableWorkbook workbook = Workbook.createWorkbook(destino);
            WritableSheet sheet = workbook.createSheet("Pagos_" + nombreMes, 0);

            WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
            headerFormat.setBackground(Colour.GRAY_25);
            headerFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

            WritableCellFormat dataFormat = new WritableCellFormat();
            dataFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

            WritableCellFormat numberFormat = new WritableCellFormat(NumberFormats.FLOAT);
            numberFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

            sheet.addCell(new Label(0, 0, "Código", headerFormat));
            sheet.addCell(new Label(1, 0, "Alumno", headerFormat));
            sheet.addCell(new Label(2, 0, "Fecha", headerFormat));
            sheet.addCell(new Label(3, 0, "Monto", headerFormat));
            sheet.addCell(new Label(4, 0, "Método", headerFormat));
            sheet.addCell(new Label(5, 0, "Estado", headerFormat));

            sheet.setColumnView(0, 10);
            sheet.setColumnView(1, 35);
            sheet.setColumnView(2, 12);
            sheet.setColumnView(3, 12);
            sheet.setColumnView(4, 15);
            sheet.setColumnView(5, 12);

            int rowIndex = 1;
            double totalMonto = 0.0;

            for (Object[] rowData : lista) {
                try {
                    sheet.addCell(new Label(0, rowIndex,
                            rowData[1] != null ? rowData[1].toString() : "", dataFormat));

                    String nombreCompleto = "";
                    if (rowData[2] != null) nombreCompleto += rowData[2].toString();
                    if (rowData[3] != null) nombreCompleto += " " + rowData[3].toString();
                    if (rowData[4] != null) nombreCompleto += " " + rowData[4].toString();
                    if (rowData[5] != null) nombreCompleto += " " + rowData[5].toString();
                    sheet.addCell(new Label(1, rowIndex, nombreCompleto.trim(), dataFormat));

                    sheet.addCell(new Label(2, rowIndex,
                            rowData[6] != null ? rowData[6].toString() : "", dataFormat));

                    if (rowData[8] != null) {
                        double monto = 0.0;
                        if (rowData[8] instanceof Double) {
                            monto = (Double) rowData[8];
                        } else if (rowData[8] instanceof Number) {
                            monto = ((Number) rowData[8]).doubleValue();
                        }
                        sheet.addCell(new jxl.write.Number(3, rowIndex, monto, numberFormat));
                        totalMonto += monto;
                    } else {
                        sheet.addCell(new jxl.write.Number(3, rowIndex, 0.0, numberFormat));
                    }

                    sheet.addCell(new Label(4, rowIndex,
                            rowData[9] != null ? rowData[9].toString() : "", dataFormat));

                    sheet.addCell(new Label(5, rowIndex,
                            rowData[7] != null ? rowData[7].toString() : "", dataFormat));

                    rowIndex++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (rowIndex > 1) {
                sheet.addCell(new Label(2, rowIndex, "TOTAL:", headerFormat));
                sheet.addCell(new jxl.write.Number(3, rowIndex, totalMonto, numberFormat));
            }

            workbook.write();
            workbook.close();
        } catch (IOException | WriteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}