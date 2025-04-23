package ru.hometask.services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.hometask.entities.Contract;
import ru.hometask.repositories.ContractRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service

public class ExcelExportService {

    private final ContractRepository contractRepository;

    public ExcelExportService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public Resource exportToExcel(Long id) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Contracts");

            // Стиль для заголовков
            CellStyle headerStyle = createHeaderStyle(workbook);

            // Создаем строку с заголовками
            Row headerRow = sheet.createRow(0);
            createHeaderCell(headerRow, 0, "Номер", headerStyle);
            createHeaderCell(headerRow, 1, "Клиент", headerStyle);
            createHeaderCell(headerRow, 2, "Стоимость", headerStyle);
            createHeaderCell(headerRow, 3, "Дата заключения", headerStyle);
            createHeaderCell(headerRow, 4, "Дата последнего платежа", headerStyle);
            createHeaderCell(headerRow, 5, "Статус", headerStyle);
            createHeaderCell(headerRow, 6, "Сотрудник", headerStyle);

            // Получаем данные из БД
            Contract contracts = contractRepository.getReferenceById(id);

            // Заполняем данные
            int rowNum = 1;

                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(contracts.getId());
                row.createCell(1).setCellValue(contracts.getClient().getFullName());
                row.createCell(2).setCellValue(contracts.getAmount());
                row.createCell(3).setCellValue(contracts.getDateOfIssue().toString());
                row.createCell(4).setCellValue(contracts.getDateTerm().toString());
                row.createCell(5).setCellValue(contracts.getStatus().getName());
                row.createCell(6).setCellValue(contracts.getEmployee().getFullName());

            // Размер колонок
            for (int i = 0; i < 7; i++) {
                sheet.autoSizeColumn(i);
            }

            // Конвертируем в массив байтов
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            return new ByteArrayResource(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка генерации Excel файла", e);
        }
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private void createHeaderCell(Row row, int column, String value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }
}
