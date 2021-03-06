package me.zhouzhuo810.magpietv.act;

import android.app.Activity;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

import io.reactivex.disposables.Disposable;
import me.zhouzhuo810.magpietv.dialog.ListDialog;
import me.zhouzhuo810.magpietv.dialog.OneBtnProgressDialog;
import me.zhouzhuo810.magpietv.dialog.TwoBtnTextDialog;
import me.zhouzhuo810.magpietv.fgm.BaseFragment;
import me.zhouzhuo810.magpietv.utils.LanguageUtil;


public interface IBaseActivity {
    
    /**
     * 是否使用系统默认的finish动画
     *
     * @return 默认false，即使用框架提供的动画
     */
    public boolean useSysFinishAnim();
    
    /**
     * 获取布局的id
     */
    public int getLayoutId();
    
    /**
     * 是否支持多语言
     *
     * @return true/false, 默认返回false
     * <p>
     * 如果返回false，表示您的app不需要支持多语言；
     * <p>
     * 如果返回true，表示您的app需要支持多语言
     * <p>
     * 可以使用{@link Application#onCreate()}中调用
     * {@link me.zhouzhuo810.magpietv.utils.LanguageUtil#setGlobalLanguage(int)}
     * 方法设置默认语言
     * <p>
     * 参数值请参考：
     * <ul>
     * <li>{@link LanguageUtil#SIMPLE_CHINESE}</li>
     * <li>{@link LanguageUtil#TRADITIONAL_CHINESE }</li>
     * <li>{@link LanguageUtil#ENGLISH }</li>
     * </ul>
     */
    public boolean shouldSupportMultiLanguage();
    
    /**
     * 屏幕适配需要返回getWindow().getDecorView();
     *
     * @return {@link Activity#getWindow()#getDecorView()}
     */
    public View getDecorView();
    
    public void initView(@Nullable Bundle savedInstanceState);
    
    public void initData();
    
    public void initEvent();
    
    /**
     * 判断是否调用initView、initData、initEvent方法。
     *
     * @return 是否
     */
    public boolean shouldNotInvokeInitMethods(Bundle savedInstanceState);
    
    public void startAct(Class<? extends Activity> clazz);
    
    public void startActShared(Class<? extends Activity> clazz, final View... sharedElements);
    
    public void startActWithIntent(Intent intent);
    
    public void startActWithIntent(Intent intent, boolean defaultAnim);
    
    public void startActWithIntentShared(Intent intent, final View... sharedElements);
    
    public void startActWithIntentForResult(Intent intent, int requestCode);
    
    public void startActWithIntentForResult(Intent intent, int requestCode, boolean defaultAnim);
    
    public void restart();
    
    public void closeAct();
    
    public void closeActWithOutAnim();
    
    public void closeAct(boolean defaultBack);
    
    public void closeAllAct();
    
    public void closeAllAct(boolean defaultAnimation);
    
    public void overridePendingTransition(int enterAnim, int exitAnim);
    
    /**
     * 启动Activity进入动画
     *
     * @return resId
     */
    int openInAnimation();
    
    /**
     * 启动Activity退出动画
     *
     * @return resId
     */
    int openOutAnimation();
    
    /**
     * 关闭Activity进入动画
     *
     * @return resId
     */
    int closeInAnimation();
    
    /**
     * 关闭Activity退出动画
     *
     * @return resId
     */
    int closeOutAnimation();
    
    public void showLoadingDialog(String msg);
    
    public void showLoadingDialog(String title, String msg);
    
    public void showLoadingDialog(String title, String msg, boolean cancelable);
    
    public void showLoadingDialog(String title, String msg, boolean cancelable, boolean iosStyle);
    
    public void showLoadingDialog(String title, String msg, boolean cancelable, DialogInterface.OnDismissListener listener);
    
    public void showLoadingDialog(String title, String msg, boolean cancelable, boolean iosStyle, DialogInterface.OnDismissListener onDismissListener);
    
    public void hideLoadingDialog();
    
    public void showOneBtnProgressDialog(String title, String msg, OneBtnProgressDialog.OnProgressListener onProgressListener);
    
    public void showOneBtnProgressDialog(String title, String msg, DialogInterface.OnDismissListener onDismissListener, OneBtnProgressDialog.OnProgressListener onProgressListener);
    
    public void showOneBtnProgressDialog(String title, String msg, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, OneBtnProgressDialog.OnProgressListener onProgressListener);
    
    public void showOneBtnProgressDialog(String title, String msg, String btnString, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, OneBtnProgressDialog.OnProgressListener onProgressListener);
    
    public void hideOneBtnProgressDialog();
    
    public void showTwoBtnTextDialog(String title, String msg, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick);
    
    public void showTwoBtnTextDialog(String title, String msg, boolean cancelable, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick);
    
    public void showTwoBtnTextDialog(String title, String msg, boolean msgCenter, boolean cancelable, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick);
    
    public void showTwoBtnTextDialog(String title, String msg, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick);
    
    public void showTwoBtnTextDialog(String title, String msg, String leftBtnString, String rightBtnString, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick);
    
    public void showTwoBtnTextDialog(String title, String msg, boolean msgCenter, String leftBtnString, String rightBtnString, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick);
    
    public void hideTwoBtnTextDialog();
    
    public void showListDialog(String[] items, boolean cancelable, ListDialog.OnItemClick onItemClick);
    
    public void showListDialog(String title, String[] items, boolean cancelable, ListDialog.OnItemClick onItemClick);
    
    public void showListDialog(String title, String[] items, boolean alignLeft, boolean cancelable, ListDialog.OnItemClick onItemClick);
    
    public void showListDialog(String title, List<String> items, boolean alignLeft, boolean cancelable, ListDialog.OnItemClick onItemClick);
    
    public void showListDialog(String title, List<String> items, boolean cancelable, ListDialog.OnItemClick onItemClick);
    
    public void showListDialog(String title, List<String> items, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, ListDialog.OnItemClick onItemClick);
    
    /**
     * 显示列表对话框
     *
     * @param title             对话框标题，传null表示不显示
     * @param items             列表数据集合
     * @param alignLeft         是否左对齐，否则居中对齐
     * @param cancelable        是否可以点击空白处或点返回键取消
     * @param onDismissListener 对话框消失回调
     * @param onItemClick       对话框点击回调
     */
    public void showListDialog(String title, List<String> items, boolean alignLeft, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, ListDialog.OnItemClick onItemClick);
    
    public void hideListDialog();
    
    public void refreshData(String... params);
    
    public void loadMoreData(String... params);
    
    public TextWatcher setEditImageListener(EditText et, ImageView iv);
    
    public interface OnOneBtnClick {
        void onClick();
    }
    
    public <T extends BaseFragment> T findFragmentByTag(String tag);
    
    public <T extends BaseFragment> T findFragmentByClazzAsTag(Class<T> clazz);
    
    public <T extends BaseFragment> void replaceFragment(@IdRes int containerId, Class<T> clazz, T fragment, Bundle bundle);
    
    /**
     * 添加或显示Fragment
     *
     * 使用clazz.setSimpleName()作为tag，必须保证同一个Activity中使用addOrShowFragment的Fragment的的类名不相同；
     * 如果存在相同的类名，请使用{@link BaseActivity#addOrShowFragmentCustomTag}
     *
     * @param containerId 容器id
     * @param clazz       Fragment类
     * @param bundle      参数
     * @param <T>         Fragment
     * @return Fragment实例
     */
    public <T extends BaseFragment> T addOrShowFragment(@IdRes int containerId, Class<T> clazz, Bundle bundle);
    
    /**
     * 添加或显示Fragment
     *
     * 使用自定义Tag，可以使用{@link BaseActivity#findFragmentByTag(String)}查找Fragment实例。
     *
     * @param containerId 容器id
     * @param clazz       Fragment类
     * @param tag         自定义Tag
     * @param bundle      参数
     * @param <T>         Fragment
     * @return Fragment实例
     */
    public <T extends BaseFragment> T addOrShowFragmentCustomTag(@IdRes int containerId, Class<T> clazz, String tag, Bundle bundle);
    
    public <T extends BaseFragment> void hideFragment(T fragment);
    
    public <T extends BaseFragment> void hideFragmentByClass(Class<T> clazz);
    
    public void hideFragmentByTag(String tag);
    
    public void cancelDisposable(Disposable disposable);
}
