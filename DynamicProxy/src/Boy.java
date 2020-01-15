import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Girl {

    void date();

    void watchMovie();
}

class WangMeiLi implements Girl {

    @Override
    public void date() {
        System.out.println("王美丽说：跟你约会好开心。");
    }

    @Override
    public void watchMovie() {
        System.out.println("王美丽说：这个电影我不喜欢看。");
    }
}

class WangMeiLiProxy implements InvocationHandler {

    private Girl girl;

    public WangMeiLiProxy(Girl girl) {
        super();
        this.girl = girl;
    }

    private void doSomethingBefore() {
        System.out.println("王美丽父母说：我得先调查下这个男孩的背景");
    }

    private void doSomethingAfter() {
        System.out.println("王美丽父母说：约会怎么样啊？");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        doSomethingBefore();
        Object ret = method.invoke(girl, args);
        doSomethingAfter();
        return ret;
    }

    public Object getProxyInstance() {
        return Proxy.newProxyInstance(girl.getClass().getClassLoader(), girl.getClass().getInterfaces(), this);
    }
}

public class Boy {

    public static void main(String[] args) {
        // 有个女孩叫王美丽
        Girl girl = new WangMeiLi();

        // 她有一个庞大的家庭，想要跟她约会必要征得她家人的同意
        WangMeiLiProxy family = new WangMeiLiProxy(girl);

        // 她家人不一定是谁有空，这次是她妈妈有空，然后她妈妈代理她
        Girl mother = (Girl) family.getProxyInstance();

        // 通过她妈妈这个代理才能与王美丽约会
        mother.date();

        // 通过她妈妈这个代理才能与王美丽约会
        System.out.println("--------------------------");
        mother.watchMovie();
    }
}
