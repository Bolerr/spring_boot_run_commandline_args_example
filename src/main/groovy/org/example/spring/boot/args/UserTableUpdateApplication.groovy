package org.example.spring.boot.args

import groovy.util.logging.Slf4j
import org.example.spring.boot.args.domain.WorksheetUserRecord
import org.example.spring.boot.args.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.PropertySource
import org.springframework.core.env.SimpleCommandLinePropertySource

@Slf4j
@SpringBootApplication
class UserTableUpdateApplication {

    @Autowired
    UserService service

    static void main(String[] args) {
        log.info("args: ${args}")

        SpringApplication app = new SpringApplication(UserTableUpdateApplication.class)
        app.setWebEnvironment(false)
        ConfigurableApplicationContext applicationContext = app.run(args)

        PropertySource propertySource = new SimpleCommandLinePropertySource(args)
        log.info("propertySource.properties: ${propertySource.properties}")

        List<String> commandLineArgs = propertySource.getNonOptionArgs()
        String excelFilePath = commandLineArgs?.first()
        String workSheetName = commandLineArgs?.last()

        log.info("Running with excelFilePath: ${excelFilePath} workSheetName: ${workSheetName}")

        UserService service = applicationContext.getBean(UserService)

        Collection<WorksheetUserRecord> users = service.readInUsers(excelFilePath, workSheetName)

        users.eachWithIndex { WorksheetUserRecord user, Integer index ->
            log.info("Found User: ${user} on Row: ${index}")
        }
        //The updating of records and logic has been removed as it wasn't necessary for showing the functionality
    }


}
