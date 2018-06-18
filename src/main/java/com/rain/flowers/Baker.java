package com.rain.flowers;

/**
 * 水培吊兰
 *
 * @author sourc
 * @date 2018/6/10
 */
public class Baker extends Plant {
    private String value;
    private String extension;

    public Baker(String name, String looks, String habits, String origin, String grow) {
        super(name, looks, habits, origin, grow);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public static void main(String[] args) {
        Baker baker = new Baker("水培吊兰", "叶剑形, 两边稍渐狭, 根壮茎短",
                "温暖, 湿润, 半阴, 耐寒不耐寒, 光照过强时, 叶色变黄, 宜放置在半阴的地方",
                "非洲南部", "1.换水, 夏季5-7天换一次水, 水可以是自来水, 但是需要静置2天左右");
        baker.setValue("1.观赏价值, 2.空气净化器");
        baker.setExtension("1.可适当与金鱼一块养, 2.可栽种不同品种的吊兰, 增加观赏性");

        System.out.print(baker);
    }
}
