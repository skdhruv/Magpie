# Magpie
 A powerful Android Develop Framework.


### How to use


```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}
```

> Magpie

```
    implementation 'com.github.zhouzhuo810.Magpie:magpie:1.0.0'
```

If you use this. That means you added dependencies below:
```
    //v7
    api 'com.android.support:appcompat-v7:27.1.1'
    //RecyclerView
    api 'com.android.support:recyclerview-v7:27.1.1'
    //BaseRecyclerViewAdapterHelper
    api 'com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.40'
    //EventBus
    api 'org.greenrobot:eventbus:3.1.1'
    //Glide
    api 'com.github.bumptech.glide:glide:4.8.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.8.0'
    //RxJava2
    api "io.reactivex.rxjava2:rxjava:2.2.0"
    //RxAndroid2
    api 'io.reactivex.rxjava2:rxandroid:2.1.0'
    //Retrofit2
    api 'com.squareup.retrofit2:retrofit:2.4.0'
    //Retrofit2+RxJava2
    api 'com.squareup.retrofit2:adapter-rxjava2:2.4.0'
    //Retrofit2+Gson
    api 'com.squareup.retrofit2:converter-gson:2.4.0'
    //OkHttp打印请求数据
    api 'com.squareup.okhttp3:logging-interceptor:3.11.0'
    //OkHttp进度监听progressmanager
    api 'me.jessyan:progressmanager:1.5.0'
```

> Magpie-TV

```
    implementation 'com.github.zhouzhuo810.Magpie:magpie-tv:1.0.0'
```

If you use this. That means you added dependencies below:
```
    //v7
    api 'com.android.support:appcompat-v7:27.1.1'
    //RecyclerView
    api 'com.android.support:recyclerview-v7:27.1.1'
    //EventBus
    api 'org.greenrobot:eventbus:3.1.1'
    //RxAndroid2
    api 'io.reactivex.rxjava2:rxandroid:2.1.0'
    //Retrofit2
    api 'com.squareup.retrofit2:retrofit:2.4.0'
    //zz-horizontal-progressbar
    api 'me.zhouzhuo.zzhorizontalprogressbar:zz-horizontal-progressbar:1.0.7'
```

#### Screen Adapter

- Add UI Design size in your AndroidManifest.xml.

```xml
        <!--设计图的宽,单位是像素,推荐用markman测量,量出来如果是750px那么请尽量去找ui设计师要一份android的设计图.-->
        <meta-data
            android:name="design_width"
            android:value="1080" />
        <!--设计图对应的标准dpi,根据下面的那张图找到对应的dpi,比如1080就对应480dpi,如果拿到的是其他宽度的设计图,那么选择一个相近的dpi就好了-->
        <!--
            宽         	    240 	320 	480 	720     1080 	1440
            DPI等级	        LDPI	MDPI	HDPI	XHDPI	XXHDPI	XXXHDPI
            DPI数值	        120	    160	    240	    320	    480	    640
        -->
        <meta-data
            android:name="design_dpi"
            android:value="480" />

        <!--全局字体的大小倍数,有时候老板会觉得你的所有的字小了或者大了,你总不能一个一个去改吧-->
        <meta-data
            android:name="font_size"
            android:value="1.0" />

        <!--你的布局里面用的是px这就写px,你的布局里面用的是dp这就写dp,要统一,不要一会儿px一会儿dp,字体也用px或者dp,不要用sp,微信qq用的肯定不是sp.-->
        <meta-data
            android:name="unit"
            android:value="px" />
```

- init BaseUtil in your custom Application#onCreate().

```java
public class MyApplication extends Application {
  
    @Override
    public void onCreate() {
        super.onCreate();
  
        BaseUtil.init(this);
    }
}
```

- ok, Just use `px` unit in your layout.

- If you do not use BaseActivity, Then you should invoke the method
 `   ScreenAdapterUtil.getInstance().loadView(getWindow().getDecorView());` in `Activity#onCreate()` method after `setContentView()`.

- If you do not use BaseFragment, Then you should invoke the method
 `   ScreenAdapterUtil.getInstance().loadView(view);` in `Fragment#onCreateView()` method after `inflate()`.

- If you do not use LvBaseAdapter or RvBaseAdapter, Then you should invoke the method
 `ScreenAdapterUtil.getInstance().loadView(view);` when you create ViewHolder.

#### BaseActivity

it supports:
- Multi-Style Dialog.
- Multi-Language support.
- Replace Fragment easily.


#### BaseFragment

it supports:
- Multi-Style Dialog.
- Load data lazily.

#### Utils

- BaseUtil
> This is the key util.

- ScreenAdapterUtil
> it's use for screen Adapter.

- ApiUtil
> it's for RxJava2+Retrofit2+OkHttp3 Api Creation.

- LanguageUtil
> it's for Language setting.

- KeyboardUtil
> it's for Software Keyboard Operation.

- ApkUtil
> it's for Apk Installation.

- BarUtils
> it's for StatusBar Setting.

- SpUtil
> it's use for SharedPreferences.

- StrUtil
> it's use for String Operation.

- FileUtil
> it's use for File Operation.

- DateUtil
> it's use for Date Operation.

- RxHelper
> it's use for RxJava.

- CrashUtils
> it's for UncaughtExceptionHandler.

- CopyUtil
> it's for ClipboardManager.

- ImageUtil
> it's for bitmap Operation.

- ToastUtil
> it's for simple Toast.

- NoticeUtil
> it's for Notification.

#### Widgets

- Indicator
> it's for viewpager indicator.

- TabBar
> it's for bottom navation.

- TitleBar
> it's same as ToolBar.


### Thanks

[AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode)

[AndroidScreenAdaptation](https://github.com/yatoooon/AndroidScreenAdaptation)


### License

```
Copyright © zhouzhuo810

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```




