To run the project build the jar and then run:
java -jar <jar path/name> <workbook file path> <worksheetName>

You can run the Spring boot app against the H2 profile using:
java -jar <jar path/name> --spring.profiles.active=H2 <workbook file path> <worksheetName>
ex:
java -jar ./build/libs/SpringBootArgsExample-0.1.0-SNAPSHOT.jar --spring.profiles.active=H2 ./src/test/resources/testData.xlsx sheet1

To run the project with changes without building the jar, you can also use the Gradle bootRun and bootRunH2 tasks.
When running these tasks you have to pass the arguments slightly differently.

gradlew bootRunH2 -Pargs="<workbook file path> <worksheetName>"

To run against ITG you can use the following command after you put credentials in application.yml

gradlew bootRunITG -Pargs="<workbook file path> <worksheetName>"

To run against CAT you can use the following command after you put credentials in application.yml

gradlew bootRunCAT -Pargs="<workbook file path> <worksheetName>"
