package qkiss

import grails.transaction.Transactional

@Transactional
class TestBean {

    void test(String arg) {
        Org org = new Org()
        org.name = "springBeanJob$arg"
        org.save()
    }
}
