package com.example.cookiekai.util;

import com.example.cookiekai.entity.Users;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private DateFormat format;

    public UserExcelExporter() {
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");
        XSSFRow row = sheet.createRow(0);

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        cellStyle.setFont(font);

        createCell(row, 0, "User ID", cellStyle);
        createCell(row, 1, "Email", cellStyle);
        createCell(row, 2, "Fullname", cellStyle);
        createCell(row, 3, "Address", cellStyle);
        createCell(row, 4, "PhoneNumber", cellStyle);
        createCell(row, 5, "Gender", cellStyle);
        createCell(row, 6, "Roles", cellStyle);
        createCell(row, 7, "Enabled", cellStyle);
    }

    private void createCell(XSSFRow row, int columnIndex, Object value, CellStyle style) {
        XSSFCell cell = row.createCell(columnIndex);
        sheet.autoSizeColumn(columnIndex);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLine(List<Users> listUser) {
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(false);
        font.setFontHeight(14);
        cellStyle.setFont(font);
        format = new SimpleDateFormat("yyyy-MM-dd");
        int rowIndex = 1;
        for (Users user : listUser) {
            XSSFRow row = sheet.createRow(rowIndex++);
            int columnIndex = 0;
            createCell(row, columnIndex++, user.getId(), cellStyle);
            createCell(row, columnIndex++, user.getEmail(), cellStyle);
            createCell(row, columnIndex++, user.getFullname(), cellStyle);
            createCell(row, columnIndex++, user.getAddress(), cellStyle);
            createCell(row, columnIndex++, user.getPhoneNumber(), cellStyle);
            createCell(row, columnIndex++, user.getGender() == true ? "Male" : "Female", cellStyle);
            createCell(row, columnIndex++, user.getRoles().getName(), cellStyle);
            createCell(row, columnIndex++, user.getEnabled(), cellStyle);
        }
    }

    public void exportExcel(List<Users> listUser, HttpServletResponse response) throws IOException {
        format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timeStamp = format.format(new Date());
        String fileName = "users_" + timeStamp + ".xlsx";
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey, headerValue);

        writeHeaderLine();
        writeDataLine(listUser);

        ServletOutputStream stream = response.getOutputStream();
        workbook.write(stream);
        workbook.close();
        stream.close();
    }
}
