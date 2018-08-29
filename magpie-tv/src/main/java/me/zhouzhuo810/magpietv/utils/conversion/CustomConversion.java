package me.zhouzhuo810.magpietv.utils.conversion;

import android.view.View;

import me.zhouzhuo810.magpie.utils.loadviewhelper.AbsLoadViewHelper;


public class CustomConversion implements IConversion {

    @Override
    public void transform(View view, AbsLoadViewHelper loadViewHelper) {
        if (view.getLayoutParams() != null) {
            loadViewHelper.loadWidthHeightFont(view);
            loadViewHelper.loadPadding(view);
            loadViewHelper.loadLayoutMargin(view);
            loadViewHelper.loadMaxWidthAndHeight(view);
            loadViewHelper.loadMinWidthAndHeight(view);
        }
    }

}
