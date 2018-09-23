package com.rain.test;

import com.rain.mqtt.PayMqtt;
import org.junit.Test;

/**
 * @author sourc
 * @date 2018/7/22
 */
public class PayMqttTest {

    @Test
    public void testStart() throws Exception {
        PayMqtt mqtt = new PayMqtt();
        mqtt.start();
    }
}