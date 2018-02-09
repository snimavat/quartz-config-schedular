package grails.plugin.quartzconfigscheduler

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import org.quartz.JobDataMap
import org.quartz.JobExecutionContext
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import spock.lang.Ignore
import spock.lang.Specification

@TestMixin(GrailsUnitTestMixin)
class ClosureJobSpec extends Specification {

    void "test execute calls closure with params"() {
        setup:
        ClosureJob job = new ClosureJob()
        JobExecutionContext context = Mock()
        JobDataMap jobDataMap = Mock()
        int count = 0
        Closure closure = { count++ }

        when:
        job.execute(context)

        then:
        1 * context.getMergedJobDataMap() >> jobDataMap
        1 * jobDataMap.get("groovyClosure") >> closure
        count == 1
    }

    @Ignore
    void "test schedule job"() {
        setup:
        ClosureJob job = GroovySpy(ClosureJob, global: true)
        Closure cls = {}
        Trigger trigger = TriggerBuilder.newTrigger().startNow().build()
        Map params = [:]
        when:
        ClosureJob.schedule(trigger, params, cls)

        then:
        1 * ClosureJob.schedule(trigger, params) >> null
    }
}
