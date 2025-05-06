package ru.hometask.services;

import jakarta.persistence.EntityNotFoundException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.hometask.entities.Contract;
import ru.hometask.exception.ExcelExportException;
import ru.hometask.repositories.ContractRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * Сервис для экспорта данных в Excel.
 */
@Service
public class ExcelExportService {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static final String[] HEADERS = {
            "Номер", "Клиент", "Стоимость", "Дата заключения",
            "Дата окончания", "Статус", "Точка выдачи", "Сотрудник"
    };

    private final ContractRepository contractRepository;

    public ExcelExportService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    /**
     * Экспортирует договор в Excel файл.
     * @param contractId ID договора для экспорта
     * @return Ресурс с содержимым Excel файла
     * @throws RuntimeException если произошла ошибка при генерации файла
     * @throws EntityNotFoundException если договор не найден
     */
    public Resource exportContractToExcel(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new EntityNotFoundException("Договор не найден"));

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Договор");
            createHeaderRow(workbook, sheet);
            fillContractData(sheet, contract);
            autoSizeColumns(sheet);

            workbook.write(outputStream);
            return new ByteArrayResource(outputStream.toByteArray());
        } catch (IOException e) {
            throw new ExcelExportException("Ошибка при генерации Excel файла", e);
        }
    }

    /**
     * Создаём заголовки
     */
    private void createHeaderRow(Workbook workbook, Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = createHeaderStyle(workbook);

        for (int i = 0; i < HEADERS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(HEADERS[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    /**
     * Наполняем данными таблицу
     */
    private void fillContractData(Sheet sheet, Contract contract) {
        Row dataRow = sheet.createRow(1);

        dataRow.createCell(0).setCellValue(contract.getId());
        dataRow.createCell(1).setCellValue(contract.getClient().getFullName());
        dataRow.createCell(2).setCellValue(contract.getAmount().doubleValue());
        dataRow.createCell(3).setCellValue(contract.getDateOfIssue().format(DATE_FORMATTER));
        dataRow.createCell(4).setCellValue(contract.getDateTerm().format(DATE_FORMATTER));
        dataRow.createCell(5).setCellValue(contract.getStatus().getName());
        dataRow.createCell(6).setCellValue(contract.getIssuePoint().getName());
        dataRow.createCell(7).setCellValue(contract.getEmployee().getFullName());
    }

    /**
     * Задаём стиль
     */
    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private void autoSizeColumns(Sheet sheet) {
        for (int i = 0; i < HEADERS.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }
}