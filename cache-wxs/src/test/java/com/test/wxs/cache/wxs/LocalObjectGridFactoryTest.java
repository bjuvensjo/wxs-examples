package com.test.wxs.cache.wxs;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class LocalObjectGridFactoryTest {

    @Autowired
    private ObjectGridFactory objectGridFactory;

    @Test
    public void testGetObjectGrid() {
        try {
            assertNotNull(objectGridFactory.getObjectGrid());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSameObjectGridWithinOneContext() {
        try {
            assertSame(objectGridFactory.getObjectGrid(), objectGridFactory.getObjectGrid());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSameObjectGridWithinMultipleContexts() {
        try {
            ClassPathXmlApplicationContext ctx1 = new ClassPathXmlApplicationContext(
                    "/com/test/wxs/cache/wxs/LocalObjectGridFactoryTest-context.xml");
            ClassPathXmlApplicationContext ctx2 = new ClassPathXmlApplicationContext(
                    "/com/test/wxs/cache/wxs/LocalObjectGridFactoryTest-context.xml");
            LocalObjectGridFactory localObjectGridFactory1 = ctx1.getBean(LocalObjectGridFactory.class);
            LocalObjectGridFactory localObjectGridFactory2 = ctx2.getBean(LocalObjectGridFactory.class);
            ctx1.close();
            ctx2.close();
            assertNotSame(localObjectGridFactory1, localObjectGridFactory2);
            assertSame(localObjectGridFactory1.getObjectGrid(), localObjectGridFactory2.getObjectGrid());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
