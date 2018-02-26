package qkiss

import grails.plugin.quartzconfigscheduler.ClosureJob
import grails.transaction.Rollback
import org.quartz.Trigger
import org.quartz.impl.triggers.SimpleTriggerImpl

@grails.test.mixin.integration.Integration
@Rollback
class ClosureJobSpec extends spock.lang.Specification {

    void testBasic() {

        setup:
        int latch = 0

        Trigger trig = new SimpleTriggerImpl(name: "closureJobTrig", startTime: new Date(), repeatInterval: 100, repeatCount: 2)

        when:
        ClosureJob.schedule(trig, [:]) {
            latch++
        }

        sleep(1000) //sleep for 3 seconds

        then:
        latch == 3

    }

}
