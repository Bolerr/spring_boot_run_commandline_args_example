package org.example.spring.boot.args.service

import org.example.spring.boot.args.domain.WorksheetUserRecord
import spock.lang.Specification

class UserWorkbookReaderSpec extends Specification {

    UserWorkbookReader workbookReader = new UserWorkbookReader()

    void "verify that readWorkbook(String excelFilePath, String workSheetName)"() {
        given:
        String excelFilePath = './src/test/resources/testData.xlsx'
        String worksheetName = 'sheet1'

        when: 'we read in the workbook'
        Collection<WorksheetUserRecord> worksheetUserRecords = workbookReader.readWorkbook(excelFilePath, worksheetName)

        then:
        worksheetUserRecords.size() == 3

        WorksheetUserRecord firstUserRecord = worksheetUserRecords.first()

        firstUserRecord.identityName == 123456

        firstUserRecord.firstName == 'firstName'
        firstUserRecord.lastName == 'lastName'
        firstUserRecord.email == 'firstName.lastName@example.org'

        firstUserRecord.inactive == 0
    }
}
