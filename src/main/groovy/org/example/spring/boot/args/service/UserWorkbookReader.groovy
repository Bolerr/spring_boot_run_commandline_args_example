package org.example.spring.boot.args.service

import groovy.util.logging.Slf4j
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.example.spring.boot.args.domain.WorksheetUserRecord
import org.example.spring.boot.args.utility.ExcelWorkBookReader
import org.springframework.stereotype.Component

import static org.example.spring.boot.args.domain.SpreadsheetColumnIndices.*

@Component
@Slf4j
class UserWorkbookReader {

    ExcelWorkBookReader excelWorkBookReader = new ExcelWorkBookReader()

    Collection<WorksheetUserRecord> readWorkbook(String excelFilePath, String workSheetName) {
        Collection<WorksheetUserRecord> userRecords = []
        XSSFSheet userRecordSheet = (XSSFSheet) excelWorkBookReader.getWorkSheet(excelFilePath, workSheetName)
        userRecordSheet.rowIterator().eachWithIndex { Row row, int rowIndex ->
            //Skip first header row
            if (rowIndex >= 1 && row.firstCellNum.intValue() == 0) {
                WorksheetUserRecord userRecord = (WorksheetUserRecord) excelWorkBookReader.readRow(row, { Row excelRow ->
                    WorksheetUserRecord userRecord = new WorksheetUserRecord()
                    userRecord.identityName = (Integer) excelWorkBookReader.getCellValueForOverrideCellType(
                            excelRow.getCell(Identity_Name), Cell.CELL_TYPE_NUMERIC)

                    userRecord.firstName = excelWorkBookReader.getCellValue(excelRow.getCell(firstname))
                    userRecord.lastName = excelWorkBookReader.getCellValue(excelRow.getCell(lastname))
                    userRecord.email = excelWorkBookReader.getCellValue(excelRow.getCell(email))

                    userRecord.inactive = (Integer) excelWorkBookReader.getCellValueForOverrideCellType(
                            excelRow.getCell(inactive), Cell.CELL_TYPE_NUMERIC)

                    return userRecord
                })
                userRecords.add(userRecord)
            }
        }
        log.info("userRecords.size:${userRecords.size()} + 1: ${userRecords.size() + 1} == rows.size: ${userRecordSheet.getLastRowNum()}")

        return userRecords
    }
}
