package ru.hometask.exception;

import java.io.IOException;

public class ExcelExportException extends RuntimeException {
    public ExcelExportException (String message, IOException e) {
        super (message, e);
    }

}
