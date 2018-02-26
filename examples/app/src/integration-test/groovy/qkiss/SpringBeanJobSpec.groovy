package qkiss

import grails.plugin.quartzconfigscheduler.SpringBeanJob
import grails.transaction.Rollback
import org.quartz.Trigger

import static org.quartz.SimpleScheduleBuilder.simpleSchedule
import static org.quartz.TriggerBuilder.newTrigger

@grails.test.mixin.integration.Integration
@Rollback
class SpringBeanJobSpec extends spock.lang.Specification {

	void testScheduleJob() {
		setup:
		Trigger trigger = newTrigger().withIdentity("sbjobTrigger")
			.withSchedule(
				simpleSchedule().withIntervalInMilliseconds(10).withRepeatCount(4) //it does one more than this
			)
			.startNow().build()


		when:
		SpringBeanJob.schedule(trigger, "testBean", "test", "FromSpec")

		sleep(1000 * 3) //sleep a 1/2 sec to let it finish

		then:
		Org.findAllByName('springBeanJobFromSpec').size() == 5
    }
}
