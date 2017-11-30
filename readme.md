This is a small example project to show one way to pass command line arguments to bootRun tasks via Gradle. 

It came about from some work at a client where I had to read in a list of network user ids that had been extracted to an
excel file, and match them up to ids in an user authority DB2 table. The utility would add the network ids to the table
with the same permissions as matching mainframe records. This was to support a number of programs that were being
converted from mainframe programs to web applications.


To run the project build the jar and then run:

`java -jar <jar path/name> <workbook file path> <worksheetName>`

You can run the Spring boot app against the H2 profile using:

`java -jar <jar path/name> --spring.profiles.active=H2 <workbook file path> <worksheetName>`

ex:

`java -jar ./build/libs/SpringBootArgsExample-0.1.0-SNAPSHOT.jar --spring.profiles.active=H2 ./src/test/resources/testData.xlsx sheet1`


To run the project with changes without building the jar, you can also use the Gradle bootRun and bootRunH2 tasks.

When running these tasks you have to pass the arguments slightly differently.

`gradlew bootRunH2 -Pargs="<workbook file path> <worksheetName>"`

To run against ITG you can use the following command after you put credentials in application.yml

`gradlew bootRunITG -Pargs="<workbook file path> <worksheetName>"`

To run against CAT you can use the following command after you put credentials in application.yml

`gradlew bootRunCAT -Pargs="<workbook file path> <worksheetName>"`
