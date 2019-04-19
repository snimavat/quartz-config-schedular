package grails.plugin.quartzconfigscheduler

import org.grails.spring.beans.factory.InstanceFactoryBean
import org.grails.testing.GrailsUnitTest
import org.quartz.JobDataMap
import org.quartz.JobExecutionContext
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification

class SpringBeanJobSpec extends Specification implements GrailsUnitTest {

    @Shared
    TestBean testBean = Mock()

    void setupSpec() {
         defineBeans {
            testBean(InstanceFactoryBean, testBean, TestBean)
        }
    }

    @Ignore("Started failing since 3.3.9 fix")//TODO
    void "test execute calls method on spring bean"() {
        setup:
        SpringBeanJob job = new SpringBeanJob()
        job.grailsApplication = grailsApplication

        JobExecutionContext context = Mock()
        JobDataMap jobDataMap = Mock()

        when:
        job.execute(context)

        then:
        noExceptionThrown()
        1 * context.getMergedJobDataMap() >> jobDataMap
        1 * jobDataMap.get("bean") >> "testBean"
        1 * jobDataMap.get("method") >> "testMethod"
        1 * jobDataMap.get("arguments") >> 1
        1 * testBean.invokeMethod('testMethod', new Object[1]) >> 1
        1 * context.setResult(1)
    }
}

class TestBean {

    def testMethod(int arg) {
        return arg + 1
    }
}
