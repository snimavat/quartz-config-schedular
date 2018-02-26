package qkiss

import grails.transaction.Rollback

@grails.test.mixin.integration.Integration
@Rollback
class JobSetupSpec extends spock.lang.Specification {

    void "verify jobs run from config"() {
        expect:
        Org.countByName("springBeanJobFromConfig") == 1
        Org.countByName("ClosureJob") == 1
    }
}
