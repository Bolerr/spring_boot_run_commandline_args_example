package org.example.spring.boot.args.utility

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import spock.lang.Specification

class ExcelWorkBookReaderSpec extends Specification {

    ExcelWorkBookReader reader = new ExcelWorkBookReader()

    void 'getWorkSheet(String excelFilePath, String workSheetName) should return the specified worksheet'() {
        given:
        String excelFilePath = 'src/test/resources/test_data/testData.xlsx'
        String workSheetName = 'SheetA'

        when:
        Sheet sheetA = reader.getWorkSheet(excelFilePath, workSheetName)

        then:
        sheetA.sheetName == workSheetName
        sheetA.lastRowNum == 1
        sheetA.rowIterator().size() == 2
        Row headerRow = sheetA.rowIterator().next()
        headerRow.cellIterator().size() == 1
    }

    void 'getCellValue(Cell cell) should return the appropriate value type for the cell'() {
        given:
        String excelFilePath = 'src/test/resources/test_data/testData.xlsx'
        String workSheetName = 'getCellValue'
        Sheet sheet = reader.getWorkSheet(excelFilePath, workSheetName)
        Row firstRow = sheet.rowIterator().next()

        when: 'the cell contains text'
        String textValue = reader.getCellValue(firstRow.getCell(0))

        then:
        'ABCDE' == textValue

        when: 'the cell contains 100 in a number formatted cell'
        BigDecimal numericValue = (BigDecimal) reader.getCellValue(firstRow.getCell(1))

        then:
        100.0 == numericValue

        when: 'the cell contains = 1 + 1'
        BigDecimal formulaResult = (BigDecimal) reader.getCellValue(firstRow.getCell(2))

        then:
        2 == formulaResult

        when: 'the cell true'
        Boolean booleanResult = reader.getCellValue(firstRow.getCell(3))

        then:
        booleanResult

        when: 'the cell contains text with leading and trailing whitespace'
        textValue = reader.getCellValue(firstRow.getCell(4))

        then: 'that whitespace should be trimmed'
        'ZZZZZZ' == textValue
    }

    void 'getCellValueForOverrideCellType should set the cell type value and then return the value for that cell type'() {
        given:
        String excelFilePath = 'src/test/resources/test_data/testData.xlsx'
        String workSheetName = 'getCellValue'
        Sheet sheet = reader.getWorkSheet(excelFilePath, workSheetName)
        Row firstRow = sheet.rowIterator().next()
        Cell numericCell = firstRow.getCell(1)

        when:
        String result = reader.getCellValueForOverrideCellType(numericCell, Cell.CELL_TYPE_STRING)

        then:
        result == '100'
    }
}