package grails.plugin.quartzconfigscheduler

import groovy.transform.CompileStatic
import org.quartz.JobDataMap
import org.quartz.JobExecutionContext
import org.quartz.Trigger

@CompileStatic
@SuppressWarnings(['NoDef'])
class ClosureJob {

    def execute(JobExecutionContext context) {
        JobDataMap jobDataMap = context.mergedJobDataMap
        def closure = jobDataMap.get('groovyClosure')
        if (closure && closure instanceof Closure) {
            closure.call(context)
        }
    }

    public static schedule(Trigger trigger, Map params, Closure closure) {
        if (!params) params = [:]
        params.groovyClosure = closure
        schedule(trigger, params)
    }
}