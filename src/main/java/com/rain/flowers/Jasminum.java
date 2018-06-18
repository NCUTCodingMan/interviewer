package com.rain.flowers;

/**
 * 茉莉花
 *  主要从形态, 生长习性, 产地记载以及如何栽培四个方面来描述
 *
 * @author sourc
 * @date 2018/6/10
 */
public class Jasminum extends Plant {
    public Jasminum(String name, String looks, String habits, String origin, String grow) {
        super(name, looks, habits, origin, grow);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static void main(String[] args) {
        Plant plant = new Jasminum("茉莉", "对叶双生,页白,清香", "喜阳, 通风, 炎热",
                "印度,佛国", "1.栽植, 盛夏每早晚浇水, 冬季需控制水量, 防止烂根." +
                "2.光照与温度, 霜降前移入温室, 5℃容易受伤, 0℃下容易死亡, 最适宜的温度是20-25℃, 逐渐放开到室外" +
                "3.浇水与施肥, 不干不浇, 浇必浇透, 夏季上午晚上各一次" +
                "4.整枝修剪, 减去密枝, 干枯枝, 病弱枝...");

        System.out.print(plant);
    }
}