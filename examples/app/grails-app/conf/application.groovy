
import grails.plugin.quartzconfigscheduler.ClosureJob
import grails.plugin.quartzconfigscheduler.SpringBeanJob
import org.quartz.Trigger
import qkiss.Org

import static org.quartz.SimpleScheduleBuilder.simpleSchedule
import static org.quartz.TriggerBuilder.newTrigger


grails.plugin.quartz.autoStartup = true

grails.plugin.quartz.jobSetup.job1 = { quartzScheduler, ctx ->
    // Trigger the job to run now, and then every 40 seconds
    Trigger trigger = newTrigger()
            .withIdentity("closureJobTrigger")
            .withSchedule(simpleSchedule()
            .withIntervalInSeconds(5)
            .withRepeatCount(1))
            .startNow()
            .build()


    ClosureJob.schedule(trigger, [:]) {
        Org org = new Org(name: 'ClosureJob')
        Org.withTransaction {
            org.save()
        }
    }

}

grails.plugin.quartz.jobSetup.job2 = { quartzScheduler, ctx ->
    // Trigger the job to run now, and then every 40 seconds
    Trigger trigger = newTrigger()
            .withIdentity("springBeanJobTrigger")
            .withSchedule(simpleSchedule()
            .withIntervalInSeconds(5)
            .withRepeatCount(1))
            .startNow()
            .build()

    SpringBeanJob.schedule(trigger, "testBean", "test", "FromConfig")
}
