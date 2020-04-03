package me.zhouzhuo810.magpietv.act;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.disposables.Disposable;
import me.zhouzhuo810.magpietv.R;
import me.zhouzhuo810.magpietv.cons.Cons;
import me.zhouzhuo810.magpietv.dialog.ListDialog;
import me.zhouzhuo810.magpietv.dialog.LoadingDialog;
import me.zhouzhuo810.magpietv.dialog.OneBtnProgressDialog;
import me.zhouzhuo810.magpietv.dialog.TwoBtnTextDialog;
import me.zhouzhuo810.magpietv.event.CloseAllActEvent;
import me.zhouzhuo810.magpietv.fgm.BaseFragment;
import me.zhouzhuo810.magpietv.utils.ActivityUtil;
import me.zhouzhuo810.magpietv.utils.CollectionUtil;
import me.zhouzhuo810.magpietv.utils.LanguageUtil;
import me.zhouzhuo810.magpietv.utils.ScreenAdapterUtil;
import me.zhouzhuo810.magpietv.utils.SpUtil;


public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {

    private ListDialog listDialog;
    private LoadingDialog loadingDialog;
    private TwoBtnTextDialog twoBtnTextDialog;
    private OneBtnProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);

        setContentView(getLayoutId());

        ScreenAdapterUtil.getInstance().loadView(getDecorView());

        if (!shouldNotInvokeInitMethods(savedInstanceState)) {
            initView(savedInstanceState);
            initData();
            initEvent();
        }
    }

    @Override
    public boolean shouldNotInvokeInitMethods(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public boolean useSysFinishAnim() {
        return false;
    }

    @Override
    public View getDecorView() {
        return getWindow().getDecorView();
    }

    /**
     * 跳转到目标Activity
     *
     * @param clazz 目标界面
     */
    @Override
    public void startAct(Class<? extends Activity> clazz) {
        startActWithIntent(new Intent(this, clazz));
    }

    public void startActShared(Class<? extends Activity> clazz, final View... sharedElements) {
        startActWithIntentShared(new Intent(this, clazz), sharedElements);
    }

    @Override
    public void startActWithIntent(Intent intent) {
        startActWithIntent(intent, false);
    }

    @Override
    public void startActWithIntentShared(Intent intent, final View... sharedElements) {
        startActivity(intent, ActivityUtil.getOptionsBundle(this, sharedElements));
    }

    @Override
    public void startActWithIntent(Intent intent, boolean defaultAnim) {
        if (defaultAnim) {
            startActivity(intent);
        } else {
            startActivity(intent, ActivityUtil.getOptionsBundle(this, openInAnimation(), openOutAnimation()));
        }
    }

    @Override
    public void startActWithIntentForResult(Intent intent, int requestCode) {
        startActWithIntentForResult(intent, requestCode, false);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void startActWithIntentForResult(Intent intent, int requestCode, boolean defaultAnim) {
        if (defaultAnim) {
            startActivityForResult(intent, requestCode);
        } else {
            startActivityForResult(intent, requestCode, ActivityUtil.getOptionsBundle(this, openInAnimation(), openOutAnimation()));
        }
    }

    @Override
    public void restart() {
        recreate();
    }

    @Override
    public void closeAct() {
        closeAct(false);
    }

    @Override
    public void closeActWithOutAnim() {
        finish();
        overridePendingTransition(0, 0);
    }


    @Override
    public void onBackPressed() {
        if (useSysFinishAnim()) {
            super.onBackPressed();
        } else {
            closeAct();
        }
    }

    @Override
    public void closeAct(boolean defaultAnimation) {
        if (defaultAnimation) {
            finish();
        } else {
            finish();
            overridePendingTransition(closeInAnimation(), closeOutAnimation());
        }
    }

    @Override
    public void closeAllAct() {
        closeAllAct(false);
    }

    @Override
    public void closeAllAct(boolean defaultAnimation) {
        EventBus.getDefault().post(new CloseAllActEvent(defaultAnimation));
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);
    }

    @Override
    public int closeInAnimation() {
        return R.anim.mp_slide_in_left;
    }

    @Override
    public int closeOutAnimation() {
        return R.anim.mp_side_out_right;
    }

    @Override
    public void showLoadingDialog(String msg) {
        showLoadingDialog(null, msg);
    }

    @Override
    public void showLoadingDialog(String title, String msg) {
        showLoadingDialog(title, msg, false, null);
    }

    @Override
    public void showLoadingDialog(String title, String msg, boolean cancelable) {
        showLoadingDialog(title, msg, cancelable, false, null);
    }

    @Override
    public void showLoadingDialog(String title, String msg, boolean cancelable, boolean iosStyle) {
        showLoadingDialog(title, msg, cancelable, iosStyle, null);
    }

    @Override
    public void showLoadingDialog(String title, String msg, boolean cancelable, DialogInterface.OnDismissListener listener) {
        showLoadingDialog(title, msg, cancelable, false, null);
    }

    @Override
    public void showLoadingDialog(String title, String msg, boolean cancelable, boolean iosStyle, DialogInterface.OnDismissListener onDismissListener) {
        if (loadingDialog != null && loadingDialog.isLoading()) {
            loadingDialog.setCancelable(cancelable);
            loadingDialog.setTitle(title)
                    .setMsg(msg)
                    .setIosStyle(iosStyle)
                    .setOnDismissListener(onDismissListener)
                    .update();
        } else {
            hideLoadingDialog();
            loadingDialog = new LoadingDialog();
            loadingDialog.setTitle(title)
                    .setMsg(msg)
                    .setIosStyle(iosStyle)
                    .setOnDismissListener(onDismissListener)
                    .setCancelable(cancelable);
            loadingDialog.show(getSupportFragmentManager(), getClass().getSimpleName());
        }
    }

    public LoadingDialog getLoadingDialog() {
        return loadingDialog;
    }

    /**
     * LoadingDialog是否正在加载
     *
     * @return 是否
     */
    public boolean isLoading() {
        return loadingDialog != null && loadingDialog.isLoading();
    }

    @Override
    public void hideLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismissDialog();
        }
    }

    @Override
    public void showOneBtnProgressDialog(String title, String msg, OneBtnProgressDialog.OnProgressListener onProgressListener) {
        showOneBtnProgressDialog(title, msg, false, null, onProgressListener);
    }

    @Override
    public void showOneBtnProgressDialog(String title, String msg, DialogInterface.OnDismissListener onDismissListener, OneBtnProgressDialog.OnProgressListener onProgressListener) {
        showOneBtnProgressDialog(title, msg, false, onDismissListener, onProgressListener);
    }

    @Override
    public void showOneBtnProgressDialog(String title, String msg, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, OneBtnProgressDialog.OnProgressListener onProgressListener) {
        showOneBtnProgressDialog(title, msg, getString(R.string.magpie_ok_text), cancelable, onDismissListener, onProgressListener);
    }

    @Override
    public void showOneBtnProgressDialog(String title, String msg, String btnString, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, OneBtnProgressDialog.OnProgressListener onProgressListener) {
        progressDialog = new OneBtnProgressDialog();
        progressDialog.setTitle(title)
                .setMsg(msg)
                .setBtnText(btnString)
                .setOnDismissListener(onDismissListener)
                .setProgressListener(onProgressListener)
                .setCancelable(cancelable);
        progressDialog.show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    @Override
    public void hideOneBtnProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismissDialog();
        }
    }

    @Override
    public void showTwoBtnTextDialog(String title, String msg, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick) {
        showTwoBtnTextDialog(title, msg, false, onTwoBtnClick);
    }

    @Override
    public void showTwoBtnTextDialog(String title, String msg, boolean cancelable, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick) {
        showTwoBtnTextDialog(title, msg, cancelable, null, onTwoBtnClick);
    }

    @Override
    public void showTwoBtnTextDialog(String title, String msg, boolean msgCenter, boolean cancelable, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick) {
        showTwoBtnTextDialog(title, msg, msgCenter, cancelable, null, onTwoBtnClick);
    }

    @Override
    public void showTwoBtnTextDialog(String title, String msg, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick) {
        showTwoBtnTextDialog(title, msg, getString(R.string.magpie_cancel_text), getString(R.string.magpie_ok_text), cancelable, onDismissListener, onTwoBtnClick);
    }

    public void showTwoBtnTextDialog(String title, String msg, boolean msgCenter, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick) {
        showTwoBtnTextDialog(title, msg, msgCenter, getString(R.string.magpie_cancel_text), getString(R.string.magpie_ok_text), cancelable, onDismissListener, onTwoBtnClick);
    }

    @Override
    public void showTwoBtnTextDialog(String title, String msg, String leftBtnString, String rightBtnString, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick) {
        showTwoBtnTextDialog(title, msg, true, leftBtnString, rightBtnString, cancelable, onDismissListener, onTwoBtnClick);
    }

    @Override
    public void showTwoBtnTextDialog(String title, String msg, boolean msgCenter, String leftBtnString, String rightBtnString, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick) {
        hideTwoBtnTextDialog();
        twoBtnTextDialog = new TwoBtnTextDialog();
        twoBtnTextDialog.setTitle(title)
                .setMsg(msg)
                .setLeftText(leftBtnString)
                .setRightText(rightBtnString)
                .setGravity(msgCenter ? Gravity.CENTER : Gravity.CENTER_VERTICAL | Gravity.START)
                .setOnDismissListener(onDismissListener)
                .setOnTwoBtnClickListener(onTwoBtnClick)
                .setCancelable(cancelable);
        twoBtnTextDialog.show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    @Override
    public void hideTwoBtnTextDialog() {
        if (twoBtnTextDialog != null) {
            twoBtnTextDialog.dismissDialog();
        }
    }


    @Override
    public void showListDialog(String[] items, boolean cancelable, ListDialog.OnItemClick onItemClick) {
        showListDialog(null, CollectionUtil.stringToList(items), cancelable, null, onItemClick);
    }

    @Override
    public void showListDialog(String title, String[] items, boolean cancelable, ListDialog.OnItemClick onItemClick) {
        showListDialog(title, CollectionUtil.stringToList(items), cancelable, null, onItemClick);
    }

    @Override
    public void showListDialog(String title, String[] items, boolean alignLeft, boolean cancelable, ListDialog.OnItemClick onItemClick) {
        showListDialog(title, CollectionUtil.stringToList(items), alignLeft, cancelable, null, onItemClick);
    }

    @Override
    public void showListDialog(String title, List<String> items, boolean alignLeft, boolean cancelable, ListDialog.OnItemClick onItemClick) {
        showListDialog(title, items, alignLeft, cancelable, null, onItemClick);
    }

    @Override
    public void showListDialog(String title, List<String> items, boolean cancelable, ListDialog.OnItemClick onItemClick) {
        showListDialog(title, items, cancelable, null, onItemClick);
    }

    @Override
    public void showListDialog(String title, List<String> items, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, ListDialog.OnItemClick onItemClick) {
        showListDialog(title, items, false, cancelable, onDismissListener, onItemClick);
    }

    @Override
    public void showListDialog(String title, List<String> items, boolean alignLeft, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, ListDialog.OnItemClick onItemClick) {
        hideListDialog();
        listDialog = new ListDialog();
        listDialog
                .setOnItemClick(onItemClick)
                .setOnDismissListener(onDismissListener)
                .setAlignLeft(alignLeft)
                .setTitle(title)
                .setItems(items)
                .setCancelable(cancelable);
        listDialog.show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    @Override
    public void hideListDialog() {
        if (listDialog != null) {
            listDialog.dismissDialog();
        }
    }

    @Override
    public void refreshData(String... params) {

    }

    @Override
    public void loadMoreData(String... params) {

    }

    @Override
    public int openInAnimation() {
        return R.anim.mp_slide_in_right;
    }

    @Override
    public int openOutAnimation() {
        return R.anim.mp_side_out_left;
    }


    @Override
    public TextWatcher setEditImageListener(final EditText et, final ImageView iv) {
        if (et == null) {
            throw new NullPointerException("EditText should not be null.");
        }
        if (iv == null) {
            throw new NullPointerException("ImageView should not be null.");
        }
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText("");
            }
        });
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    iv.setVisibility(View.VISIBLE);
                } else {
                    iv.setVisibility(View.GONE);
                }
            }
        };
        et.addTextChangedListener(textWatcher);
        return textWatcher;

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        //如果支持多语言，才给切换语言
        if (shouldSupportMultiLanguage()) {
            int language = SpUtil.getInt(Cons.SP_KEY_OF_CHOOSED_LANGUAGE);
            switch (language) {
                case LanguageUtil.SIMPLE_CHINESE:
                    super.attachBaseContext(LanguageUtil.attachBaseContext(newBase, Cons.SIMPLIFIED_CHINESE));
                    break;
                case LanguageUtil.TRADITIONAL_CHINESE:
                    super.attachBaseContext(LanguageUtil.attachBaseContext(newBase, Cons.TRADITIONAL_CHINESE));
                    break;
                case LanguageUtil.ENGLISH:
                    super.attachBaseContext(LanguageUtil.attachBaseContext(newBase, Cons.ENGLISH));
                    break;
                case LanguageUtil.VI:
                    super.attachBaseContext(LanguageUtil.attachBaseContext(newBase, Cons.VI));
                    break;
                case LanguageUtil.PT:
                    super.attachBaseContext(LanguageUtil.attachBaseContext(newBase, Cons.PT));
                    break;
                case LanguageUtil.RU:
                    super.attachBaseContext(LanguageUtil.attachBaseContext(newBase, Cons.RU));
                    break;
            }
        } else {
            super.attachBaseContext(newBase);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (shouldSupportMultiLanguage()) {
            LanguageUtil.updateActivityLanguage(this);
        }
    }

    @Override
    public <T extends BaseFragment> T findFragmentByClazzAsTag(Class<T> clazz) {
        if (getSupportFragmentManager() == null) {
            return null;
        }
        return (T) getSupportFragmentManager().findFragmentByTag(clazz.getSimpleName());
    }

    @Override
    public <T extends BaseFragment> T findFragmentByTag(String tag) {
        if (getSupportFragmentManager() == null) {
            return null;
        }
        return (T) getSupportFragmentManager().findFragmentByTag(tag);
    }

    @Override
    public <T extends BaseFragment> void replaceFragment(@IdRes int containerId, Class<T> clazz, T fragment, Bundle bundle) {
        if (fragment == null) {
            fragment = T.newInstance(clazz, bundle);
        }
        fragment.setArguments(bundle);
        if (getSupportFragmentManager() != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(containerId, fragment)
                    .commitNow();
        }
    }

    @Override
    public <T extends BaseFragment> T addOrShowFragment(@IdRes int containerId, Class<T> clazz, Bundle bundle) {
        T fragment = findFragmentByClazzAsTag(clazz);
        if (fragment == null) {
            fragment = T.newInstance(clazz, bundle);
            fragment.setArguments(bundle);
            if (getSupportFragmentManager() != null) {
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (fragments != null) {
                    for (Fragment fgm : fragments) {
                        if (fgm != null) {
                            if (fgm.isAdded() && !fgm.isHidden()) {
                                transaction.hide(fgm);
                            }
                        }
                    }
                }
                transaction.add(containerId, fragment, clazz.getSimpleName())
                        .show(fragment)
                        .commitNow();
            }
        } else {
            if (getSupportFragmentManager() != null) {
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (fragments != null) {
                    for (Fragment fgm : fragments) {
                        if (fgm != null) {
                            if (fgm.isAdded() && !fgm.isHidden()) {
                                transaction.hide(fgm);
                            }
                        }
                    }
                }
                transaction.show(fragment)
                        .commitNow();
            }
        }
        return fragment;
    }

    public <T extends BaseFragment> T addOrShowFragmentCustomTag(@IdRes int containerId, Class<T> clazz, String tag, Bundle bundle) {
        T fragment = findFragmentByTag(tag);
        if (fragment == null) {
            fragment = T.newInstance(clazz, bundle);
            fragment.setArguments(bundle);
            if (getSupportFragmentManager() != null) {
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (fragments != null) {
                    for (Fragment fgm : fragments) {
                        if (fgm != null) {
                            if (fgm.isAdded() && !fgm.isHidden()) {
                                transaction.hide(fgm);
                            }
                        }
                    }
                }
                transaction.add(containerId, fragment, tag)
                        .show(fragment)
                        .commitNow();
            }
        } else {
            if (getSupportFragmentManager() != null) {
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (fragments != null) {
                    for (Fragment fgm : fragments) {
                        if (fgm != null) {
                            if (fgm.isAdded() && !fgm.isHidden()) {
                                transaction.hide(fgm);
                            }
                        }
                    }
                }
                transaction.show(fragment)
                        .commitNow();
            }
        }
        return fragment;
    }

    @Override
    public <T extends BaseFragment> void hideFragment(T fragment) {
        if (fragment != null) {
            if (getSupportFragmentManager() != null) {
                getSupportFragmentManager().beginTransaction()
                        .hide(fragment)
                        .commitNow();
            }
        }
    }

    @Override
    public <T extends BaseFragment> void hideFragmentByClass(Class<T> clazz) {
        T fragment = findFragmentByClazzAsTag(clazz);
        if (fragment != null) {
            if (getSupportFragmentManager() != null) {
                getSupportFragmentManager().beginTransaction()
                        .hide(fragment)
                        .commitNow();
            }
        }
    }

    @Override
    public void hideFragmentByTag(String tag) {
        Fragment fgm = findFragmentByTag(tag);
        if (fgm != null) {
            if (getSupportFragmentManager() != null) {
                getSupportFragmentManager().beginTransaction()
                        .hide(fgm)
                        .commitNow();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCloseAllActEvent(CloseAllActEvent event) {
        if (event.isDefaultAnim()) {
            finish();
        } else {
            closeActWithOutAnim();
        }
    }

    @Override
    protected void onDestroy() {
        hideListDialog();
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void cancelDisposable(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
