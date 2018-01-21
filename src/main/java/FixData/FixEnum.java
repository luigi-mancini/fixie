package FixData;

/**
 * Created by lmancini on 12/14/17.
 */
public class FixEnum {

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "FixEnum{" +
                "tag='" + tag + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    String tag;
    String name;
    String value;
}
