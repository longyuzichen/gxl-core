/**
 * PACKAGE_NAME
 *
 * @author longyuzichen@126.com
 * @version V1.0
 * @desc
 * @date 2017-03-26 10:52
 */
public abstract class Test {
    public void testName(){
       // new Test2Demo().test2Name2();
        System.out.println("我是抽象类testName方法实现案例");
    }
    public abstract int testName1();

   abstract class Test3 extends Test2{
        @Override
        public int test2Name2() {
            test2Name();
            System.out.println("我是抽象类Test实现的Test2的接口test2Name2方法");
            return 0;
        }
    }
}
