package org.example.spring.boot.args.utility

import groovy.util.logging.Slf4j
import org.apache.poi.ss.usermodel.*

@Slf4j
class ExcelWorkBookReader {

    /**
     * Helper function that opens the specified Excel workbook and returns the specified worksheet
     * @param excelFilePath - file path & name
     * @param workSheetName - name of worksheet (tab) in the Excel workbook
     * @return Sheet
     */
    Sheet getWorkSheet(String excelFilePath, String workSheetName) {
        log.info("Attempting to read file:$excelFilePath worksheet:'$workSheetName'")
        if (!excelFilePath || !workSheetName) {
            log.error("You must specify the file path to the user excel file to read and which worksheet in the file contains the program details.")
        }
        Workbook workbook = WorkbookFactory.create(new File(excelFilePath))

        return workbook.getSheet(workSheetName)
    }

    /**
     * Helper function that wraps around the Closure used to read the values out of the excel row and reports the
     * values of the row in the case that an error is generated by the Closure.
     * @param row - org.apache.poi.ss.usermodel.Row
     * @param closure - block of code responsible for reading values out of Row and returning the constructed domain object
     * @return domain object representing relevant values of the Row
     */
    def readRow(Row row, Closure closure) {
        try {
            return closure.call(row)
        } catch (Exception e) {
            log.error("readRow - NullPointer: cell not found in row[$row.rowNum]")
            log.error("row.physicalNumberOfCells:${row.physicalNumberOfCells}")
            log.error("row.firstCellNum: ${row.firstCellNum}")
            log.error("row.lastCellNum: ${row.lastCellNum}")
            log.error("row.cellIterator().size(): ${row.cellIterator().size()}")

            row.cellIterator().each { Cell cell ->
                def value = getCellValue(cell)
                log.error("cell values: ${value}")
            }
            throw e
        }
    }

    /**
     * Helper function that attempts to return the value of a cell without the caller having to know the
     * exact contents / cell Type.
     * @param cell - Cell
     * @return String / Number / Boolean / null
     */
    def getCellValue(Cell cell) {
        if (cell == null) {
            return null
        }
        def value = null
        int cellType = cell.getCellType()
        if (cellType == Cell.CELL_TYPE_FORMULA) {
            cellType = cell.cachedFormulaResultType
        }

        switch (cellType) {
            case Cell.CELL_TYPE_STRING:
                value = cell.stringCellValue
                break
            case Cell.CELL_TYPE_NUMERIC:
                value = cell.numericCellValue
                break
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.booleanCellValue
                break
            case Cell.CELL_TYPE_BLANK:
                value = ''
                break
        }
        if (value instanceof String) {
            value = value?.trim()
        }
        return value
    }

    /**
     * Overrides the read cell type and returns the value of the cell for
     * the overridden cell type
     * @param cell
     * @param overrideCellType
     * @return String / Number / Boolean / null
     */
    def getCellValueForOverrideCellType(Cell cell, int overrideCellType) {
        if (cell == null) {
            return null
        }
        cell.setCellType(overrideCellType)
        return getCellValue(cell)
    }
}