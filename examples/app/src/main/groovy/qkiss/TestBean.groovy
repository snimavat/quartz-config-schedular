package qkiss

import grails.gorm.transactions.Transactional

@Transactional
class TestBean {

    void test(String arg) {
        Org org = new Org()
        org.name = "springBeanJob$arg"
        org.save()
    }
}
