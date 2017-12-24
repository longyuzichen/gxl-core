import java.util.HashMap;
import java.util.Map;

/**
 * PACKAGE_NAME
 *
 * @author longyuzichen@126.com
 * @version V1.0
 * @desc
 * @date 2017-03-26 10:57
 */
public class TestDemo extends Test {

    @Override
    public int testName1() {
        testName();
        new Test2Demo1().test2Name3();
        System.out.println("我是Test-testName1实现体");
        return 0;
    }

    class Test2Demo1 extends Test.Test3 {
        @Override
        public int test2Name3() {
            test2Name2();
            System.out.println("我是抽象类Test.Test3类的test2Name3的实现");
            return 0;
        }
    }

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("li", 9);
        Object im = map.replace("dd", 8);

        System.out.println(im);
        System.out.print("我是的人的");
    }
}
