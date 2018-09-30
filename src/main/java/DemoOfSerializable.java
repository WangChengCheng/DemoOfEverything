import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 1.序列化与反序列化demo
 * 2.为什么序列化对象通常重写hashCode和equals方法
 *
 * 参考网址：
 *   https://blog.csdn.net/gavin5033/article/details/80435811
 *   https://blog.csdn.net/tengdazhang770960436/article/details/53436334
 *   https://blog.csdn.net/qq_33642117/article/details/52040345
 *
 * Created by wangchengcheng on 2018-09-28
 */

public class DemoOfSerializable {

    public static void main(String[] args) {
        Phone phone = new Phone();
        phone.setBand("APPLE");
        phone.setMemory("128G");
        phone.setColor("Silver");
        phone.setBatteryUsage(86);

        //序列化
        try {
            serializePhone(phone);
        } catch (IOException e) {
            System.out.println("序列化对象Phone失败！");
        }

        //反序列化
        Phone phone2 = null;
        try {
             phone2 = deserializePhone();
            System.out.println(phone2.toString());
        } catch (IOException e) {
            System.out.println("序列化对象Phone失败");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("序列化对象Phone失败");
            e.printStackTrace();
        }

        //测试重写equals和hashCode方法与否的区别
        Set<Phone> phoneSet = new HashSet<Phone>();
        phoneSet.add(phone);
        phoneSet.add(phone2);
        //重写equals和hashCode方法，结果为1；否则为2
        System.out.println(phoneSet.size());
    }

    /**
     * 序列化对象Phone
     * @throws IOException
     */
    public static void serializePhone(Phone phone) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("phone.txt")));
        oos.writeObject(phone);
        System.out.println("Phone 对象序列化成功！");
        oos.close();
    }

    public static Phone deserializePhone() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("phone.txt")));
        Phone phone = (Phone) ois.readObject();
        System.out.println("Phone 对象反序列化成功！");
        return phone;
    }

    static class Phone implements Serializable {
        //带transient的属性不能序列化
        private String band;
        private String memory;
        private String color;
        private float batteryUsage;

        public String getBand() {
            return band;
        }

        public void setBand(String band) {
            this.band = band;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getMemory() {
            return memory;
        }

        public void setMemory(String memory) {
            this.memory = memory;
        }

        public float getBatteryUsage() {
            return batteryUsage;
        }

        public void setBatteryUsage(float batteryUsage) {
            this.batteryUsage = batteryUsage;
        }

        @Override
        public String toString() {
            return "Phone{" +
                    "band='" + band + '\'' +
                    ", memory='" + memory + '\'' +
                    ", color='" + color + '\'' +
                    ", batteryUsage=" + batteryUsage +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Phone phone = (Phone) o;

            if (Float.compare(phone.batteryUsage, batteryUsage) != 0) return false;
            if (band != null ? !band.equals(phone.band) : phone.band != null) return false;
            if (memory != null ? !memory.equals(phone.memory) : phone.memory != null) return false;
            return color != null ? color.equals(phone.color) : phone.color == null;
        }

        @Override
        public int hashCode() {
            int result = band != null ? band.hashCode() : 0;
            result = 31 * result + (memory != null ? memory.hashCode() : 0);
            result = 31 * result + (color != null ? color.hashCode() : 0);
            result = 31 * result + (batteryUsage != +0.0f ? Float.floatToIntBits(batteryUsage) : 0);
            return result;
        }
    }
}
