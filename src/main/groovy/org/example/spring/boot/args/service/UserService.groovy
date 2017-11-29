package org.example.spring.boot.args.service

import groovy.util.logging.Slf4j
import org.example.spring.boot.args.domain.WorksheetUserRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Slf4j
@Service
class UserService {

    @Autowired
    UserWorkbookReader workbookReader

    /**
     * Reads specified excel file and returns parsed user list.
     * @param excelFilePath - file path
     * @param workSheetName - name of worksheet to read
     */
    Collection<WorksheetUserRecord> readInUsers(String excelFilePath, String workSheetName) {
        return workbookReader.readWorkbook(excelFilePath, workSheetName)
    }
}
