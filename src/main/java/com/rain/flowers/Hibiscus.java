package com.rain.flowers;

/**
 * 木槿
 *
 * @author sourc
 * @date 2018/6/10
 */
public class Hibiscus extends Plant {
    public Hibiscus(String name, String looks, String habits, String origin, String grow) {
        super(name, looks, habits, origin, grow);
    }

    public static void main(String[] args) {
        Hibiscus hibiscus = new Hibiscus("木槿",
                "小枝被黄色星状绒毛, 花形呈现钟状, 花色呈粉色, 紫色, 淡紫, 紫红",
                "适应力很强, 耐干燥贫瘠, 尤其喜光, 温暖, 潮润的气候, 耐热也耐寒",
                "非洲", "1.施肥, 施肥这块主要在有花蕾以及开花时追加速效肥, 以农家肥为主, " +
                "2.修剪, 前1-2年以自然生长为主, 秋冬季修改老弱病残枝, 太复杂");

        System.out.print(hibiscus);
    }
}