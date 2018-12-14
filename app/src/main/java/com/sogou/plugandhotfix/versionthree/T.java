

//ClipboardManager
public ClipboardManager(Context context, Handler handler) throws ServiceNotFoundException {
	mContext = context;
	mHandler = handler;
        //获取一个服务分两步，获取远程Binder;将远程Binder通过asInterface生成ProxyBinder
	mService = IClipboard.Stub.asInterface(
		ServiceManager.getServiceOrThrow(Context.CLIPBOARD_SERVICE));
}
//IClipboard.Stub.asInterface
public static android.content.IClipboard asInterface(android.os.IBinder obj){
	if ((obj==null)) {
		return null;
	}
		//查询本地Binder，这里为Hook点，如何Hook，通过生成一个代理的obj对象，如何生成看下面获取服务的代码getService(name)
	android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR); 
	if (((iin!=null)&&(iin instanceof android.content.IClipboard))) {
		return ((android.content.IClipboard)iin);
	}
		return new android.content.IClipboard.Stub.Proxy(obj);//封装BinderProxy
}
	//ServiceManager,获取真是的Servic服务
public static IBinder getService(String name) {
	try {
        IBinder service = sCache.get(name); //首先获取缓存
        if (service != null) {
        	return service;
        } else {
            return getIServiceManager().getService(name);//否则跨进程请求裸的Binder,还没转化成对应的BinderProxy
        }
    } catch (RemoteException e) {
    	Log.e(TAG, "error in getService", e);
    }
    return null;
}	
