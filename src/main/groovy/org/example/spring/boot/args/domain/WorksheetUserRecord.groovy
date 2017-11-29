package org.example.spring.boot.args.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode()
@ToString(includeNames = true, includePackage = false)
class WorksheetUserRecord {

    //Employee number
    Integer identityName

    String firstName
    String lastName
    String email

    //0 - 1 boolean
    Integer inactive
}
