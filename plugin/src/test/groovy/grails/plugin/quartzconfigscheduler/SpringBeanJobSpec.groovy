package grails.plugin.quartzconfigscheduler

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import org.grails.spring.beans.factory.InstanceFactoryBean
import org.quartz.JobDataMap
import org.quartz.JobExecutionContext
import spock.lang.Specification

@TestMixin(GrailsUnitTestMixin)
class SpringBeanJobSpec extends Specification {
    TestBean testBean = Mock()

    def doWithSpring = {
        testBean(InstanceFactoryBean, testBean, TestBean)
    }

    void "test execute calls method on spring bean"() {
        setup:
        SpringBeanJob job = new SpringBeanJob()
        job.grailsApplication = grailsApplication

        JobExecutionContext context = Mock()
        JobDataMap jobDataMap = Mock()

        when:
        job.execute(context)

        then:
        1 * context.getMergedJobDataMap() >> jobDataMap
        1 * jobDataMap.get("bean") >> "testBean"
        1 * jobDataMap.get("method") >> "testMethod"
        1 * jobDataMap.get("arguments") >> 1
        1 * testBean.invokeMethod('testMethod', [1]) >> 1
        1 * context.setResult(1)

    }

}

class TestBean {

    def testMethod(int arg) {
        return arg + 1
    }
}