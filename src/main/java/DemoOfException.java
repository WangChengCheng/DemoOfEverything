import java.io.IOException;

/**
 * Created by wangchengcheng on 2018-10-31
 */

/*
Exception
     |----SQLException
     |----IOException
     |----... ...
     |----RuntimeException
                |----ArithmeticException
                |----IndexOutOfBoundsException
                |----NullPointerException
                |----... ...
 */

public class DemoOfException {
    public static void main(String[] args) {
        aMethodThrowsRuntimeExp(true);
        try {
            aMethodThrowsRuntimeExp(false);
        } catch (Exception e) {
            //aMethodThrowsRuntimeExp()方法向外抛的是运行时异常，可以不进行处理（即，不try-catch），
            // 但是一旦程序出错抛了这个异常了，这时候下面的代码就不会执行了此处去掉try-catch试一下
            //想想之前的一堆空指针异常（NullPointerException，一种运行时异常），就是这个情况
            System.out.println("抛了运行时异常，但我catch了它，其实大多数情况下抛了运行时异常，说明程序有bug，必须要改正");
        }

        try {
            aMethodThrowsNonRuntimeExp(true);
        } catch (Exception e) {
            //看aMethodThrowsNonRuntimeExp()方法的实现，当b=true时，并不会抛出异常，所以也就不存在catch了，也就是catch里面的代码不会执行
            //但是！！既然aMethodThrowsNonRuntimeExp()这个方法抛了异常了，你就必须要有catch的动作，否则就会报错，不信把try-catch去掉试试
        }

        try {
            aMethodThrowsNonRuntimeExp(false);//调用该方法必须try-catch，否则编译无法通过，也就是在IDE下会报错（可以去掉try-catch看一下）
        } catch (Exception e) {
            //catch，顾名思义，调用方法都给你抛了，你当然得接住嘛！可以理解为人家向你扔了一块砖，你必须要接住，接住之后你想干什么就由你了==此处坏笑(*_^)
            //这时候其实分两种情况：
            //1.你不想处理这个异常，那就再往外抛了，让别人调用你方法时再处理这个异常，也就是此处的第2中情况
            //throw e;
            //2.一般情况你需要处理这个异常，不要再往外抛了
            System.out.println("好吧，我接住异常了，并不向外抛了");
        }
    }

    private static int aMethodThrowsRuntimeExp(boolean b) {
        if (b)
            return 0;
        else
            throw new RuntimeException("***Runtime Exp Message***");//不必在该方法后面添加throws
    }

    private static int aMethodThrowsNonRuntimeExp(boolean b) throws Exception{
        if (b) {
            return 0;
        }
        else {
            throw new Exception("Non-Runtime Exp Message");//如果该方法抛出“非运行时异常”，必须在该方法后面添加throws
        }
    }
}
