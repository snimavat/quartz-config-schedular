[![Build Status](https://travis-ci.org/yakworks/quartz-config-scheduler.svg?branch=master)](https://travis-ci.org/yakworks/quartz-config-scheduler)

Quartz config scheduler
---
The plugin builds on top of quartz grails plugin and makes it possible to schedule quartz job on the fly from configuration.


Quickstart
--
Enable ```grails.plugin.quartz.autoStartup = true```

And then schedule a closure job as shown below.

File ```application.groovy``` or an external configuration file.

```groovy
import org.quartz.Trigger
import org.quartz.Scheduler
import org.springframework.context.ApplicationContext
import grails.plugin.quartzconfigscheduler.ClosureJob
import org.quartz.JobExecutionContext
import org.quartz.TriggerBuilder

grails.plugin.quartz.jobSetup.testJob = { Scheduler scheduler, ApplicationContext context ->
    
    Trigger trigger = TriggerBuilder.newTrigger().withIdentity("closureJobTrigger")
        .withSchedule(
            simpleSchedule()
            .withIntervalInMilliseconds(10)
            .withRepeatCount(2)
        ).startNow().build()
        
    Map jobParams = [param1:value1]
    ClosureJob.schedule(trigger, jobParams) { JobExecutionContext jobCtx ->
        println "Job executed"
    }    

}

```

Configuration
---
- ```grails.plugin.quartz.autoStartup``` - set to true enable scheduling jobs from configuration on application startup.
- ```grails.plugin.quartz.jobSetup```    - All jobs are configured under this key.


Scheduling jobs
---
Plugin provides two job classes to setup jobs from configuration. ```ClosureJob``` and ```SpringBeanJob```
Plugin looks for configuration key ```grails.plugin.quartz.jobSetup``` and each child key of it is considered as a job setup 
It must be a closure, the closure gets executed on application startup and is passed two parameters ````Scheduler```` and  ```ApplicationContext```


ClosureJob
----
```ClosureJob``` provides one static method ```schedule``` which takes a trigger, Map of job params and a closure and schedules the quartz job.
The closure is executed each time the job is triggered. The ```JobExecutionContext``` is passed to the closure as argument.

***Example***

```groovy

Trigger trigger //build trigger as per the need
ClosureJob.schedule(trigger, jobParams) { JobExecutionContext jobCtx ->
        println "Job executed"

}   
```

SpringBeanJob
----
```SpringBeanJob``` can be used to schedule a job which will invoke a specified method on a configured spring bean.
The ```SpringBeanJob``` provides a static method which takes ```Trigger```, spring bean name, method name and arguments to pass to the method as parameters 
and calls the method on the spring bean with specified arguments every time the job is triggered.

**Example**
import grails.plugin.quartzconfigscheduler.SpringBeanJob

File: ```application.groovy```

```groovy

Trigger trigger //build trigger as per the need
SpringBeanJob.schedule(trigger, "myService", "testMethod", "testArg")


class MyService {

    void testMethod(String arg) {
        println "Method is executed with arg $arg"    
    }

}

```
