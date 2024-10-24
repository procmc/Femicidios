package com.if7100.util;  // Asegúrate de usar el nombre correcto del paquete

import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class ExportExcelUtil {

    public static void exportToExcel(String entityName, @SuppressWarnings("rawtypes") List entityList, jakarta.servlet.http.HttpServletResponse response) throws IOException, IllegalAccessException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + entityName.toLowerCase() + "_reporte.xlsx";
        response.setHeader(headerKey, headerValue);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(entityName + " Data");

        XSSFCellStyle headerStyle = createHeaderStyle(workbook);
        XSSFCellStyle cellStyle = createCellStyle(workbook);

        // Reflexión para obtener los campos de la entidad
        Object firstEntity = entityList.get(0);
        Field[] fields = firstEntity.getClass().getDeclaredFields();

        // Crear fila de encabezado
        XSSFRow headerRow = sheet.createRow(0);
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            XSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(fields[i].getName());
            cell.setCellStyle(headerStyle);
        }

        // Rellenar filas con los datos
        int rowNum = 1;
        for (Object entity : entityList) {
            XSSFRow row = sheet.createRow(rowNum++);
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                XSSFCell cell = row.createCell(i);
                Object value = fields[i].get(entity);
                cell.setCellValue(value != null ? value.toString() : "N/A");
                cell.setCellStyle(cellStyle);
            }
        }

        // Ajustar ancho de columnas
        for (int i = 0; i < fields.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Escribir archivo Excel en la respuesta
        try (jakarta.servlet.ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        }
        workbook.close();
    }

    private static XSSFCellStyle createHeaderStyle(XSSFWorkbook workbook) {
        XSSFCellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        return headerStyle;
    }

    private static XSSFCellStyle createCellStyle(XSSFWorkbook workbook) {
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }
}
