package com.sogou.plugandhotfix.versionthree;

import com.sogou.plugandhotfix.AppUtils.Logger;

import android.os.IBinder;
import android.os.IInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by linchen on 2018/12/14.
 * mail: linchen@sogou-inc.com
 */
public class BinderProxyHookHandler implements InvocationHandler {
    private Class mStub;
    private Class mInterface;

    private IBinder mIBinder; //原始的Clipboard对象

    public BinderProxyHookHandler(IBinder IBinder) {
        try {
            mIBinder = IBinder;
            mStub = Class.forName("android.content.IClipboard$Stub");
            mInterface = Class.forName("android.content.IClipboard");
        } catch (Exception e) {

        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("queryLocalInterface".equals(method.getName())) {

            Logger.println("hook queryLocalInterface");

            // 这里直接返回真正被Hook掉的Service接口
            // 这里的 queryLocalInterface 就不是原本的意思了
            // 我们肯定不会真的返回一个本地接口, 因为我们接管了 asInterface方法的作用
            // 因此必须是一个完整的 asInterface 过的 IInterface对象, 既要处理本地对象,也要处理代理对象
            // 这只是一个Hook点而已, 它原始的含义已经被我们重定义了; 因为我们会永远确保这个方法不返回null
            // 让 IClipboard.Stub.asInterface 永远走到if语句的else分支里面
            return Proxy.newProxyInstance(proxy.getClass().getClassLoader(),

                    // asInterface 的时候会检测是否是特定类型的接口然后进行强制转换
                    // 因此这里的动态代理生成的类型信息的类型必须是正确的
                    new Class[]{IBinder.class, IInterface.class, this.mInterface},
                    new BinderHookServiceHandler(mIBinder, mStub));
        }

        Logger.println("method:" + method.getName());
        return method.invoke(mIBinder, args);
    }
}
