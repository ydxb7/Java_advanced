import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

// https://juejin.im/post/598ea9116fb9a03c335a99a4
class FatherClass {
    public String mFatherName;
    public int mFatherAge;

    public void printFatherMsg() {
    }
}

class SonClass extends FatherClass {

    private String mSonName;
    protected int mSonAge;
    public String mSonBirthday;

    public void printSonMsg() {
        System.out.println("Son Msg - name : "
                + mSonName + "; age : " + mSonAge);
    }

    private void setSonName(String name) {
        mSonName = name;
    }

    private void setSonAge(int age) {
        mSonAge = age;
    }

    private int getSonAge() {
        return mSonAge;
    }

    private String getSonName() {
        return mSonName;
    }
}

public class GetFieldsAndMethods {

    public static void main(String[] args) {
        getFields();
        getMethods();
    }


    /**
     * 通过反射获取类的所有变量
     */
    private static void getFields() {

        System.out.println("--------- getFields：--------");
        //1.获取并输出类的名称
        Class mClass = SonClass.class;
        System.out.println("类的名称：" + mClass.getName());

        System.out.println("获取所有 public 访问权限的变量, 包括本类声明的和从父类继承的: ");
        Field[] fields = mClass.getFields();
        printFields(fields);

        System.out.println("\n获取所有本类(SonClass)声明的变量（不问访问权限）: ");
        Field[] declaredFields = mClass.getDeclaredFields();
        printFields(declaredFields);
    }

    /**
     * 通过反射获取类的所有方法
     */
    private static void getMethods() {

        System.out.println("\n--------- getMethods：--------");
        //1.获取并输出 类的名称
        Class mClass = SonClass.class;
        System.out.println("类的名称：" + mClass.getName());

        //2.1 获取所有 public 访问权限的方法
        //包括自己声明和从父类继承的
        System.out.println("\n获取所有 public 访问权限的方法:");
        Method[] allPublicMethods = mClass.getMethods();
        printMethods(allPublicMethods);

        //2.2 获取所有本类的的方法（不问访问权限）
        System.out.println("\n获取所有本类的的方法（不问访问权限）:");
        Method[] thisClassMethods = mClass.getDeclaredMethods();
        printMethods(thisClassMethods);
    }

    private static void printMethods(Method[] mMethods) {
        for (Method method : mMethods) {
            //获取并输出方法的 访问权限（Modifiers：修饰符）
            int modifiers = method.getModifiers();
            System.out.print(Modifier.toString(modifiers) + " ");
            //获取并输出方法的 返回值类型
            Class returnType = method.getReturnType();
            System.out.print(returnType.getName() + " " + method.getName() + "( ");
            //获取并输出方法的 所有参数
            Parameter[] parameters = method.getParameters();
            for (Parameter parameter : parameters) {
                System.out.print(parameter.getType().getName() + " " + parameter.getName() + ",");
            }
            //获取并输出方法抛出的异常
            Class[] exceptionTypes = method.getExceptionTypes();
            if (exceptionTypes.length == 0) {
                System.out.println(" )");
            } else {
                for (Class c : exceptionTypes) {
                    System.out.println(" ) throws " + c.getName());
                }
            }
        }
    }

    private static void printFields(Field[] fields) {
        for (Field field : fields) {
            //获取 访问权限
            int modifiers = field.getModifiers();
            System.out.print(Modifier.toString(modifiers) + " ");
            //输出变量的 类型 及 变量名
            System.out.println(field.getType().getName() + " " + field.getName());
        }
    }

}
