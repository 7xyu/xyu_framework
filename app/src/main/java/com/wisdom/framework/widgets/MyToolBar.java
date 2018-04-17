package com.wisdom.framework.widgets;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wisdom.framework.R;
import com.wisdom.framework.utils.UIUtils;

import java.lang.reflect.Field;

/**
 * Created by chejiangwei on 2017/6/20.
 * Describe:自定义常规三段式toolbar
 */

public class MyToolBar extends Toolbar {


    private TextView titleLeftTv;
    private TextView titleTv;
    private FrameLayout titleRightFrame;
    private TextView titleRightTv;
    private ImageView titleRightIv;

    public MyToolBar(Context context) {
        this(context, null);
    }

    public MyToolBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.toolbarStyle);
    }

    public MyToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

//    @Override
//    public void setTitle(@StringRes int resId) {
////        super.setTitle(resId);
//        setTitleText(getContext().getText(resId));
//    }

    @Override
    public void setNavigationIcon(@Nullable Drawable icon) {
        super.setNavigationIcon(icon);
        setGravityCenter();
    }

    public void setGravityCenter() {
        post(new Runnable() {
            @Override
            public void run() {
                setCenter("mNavButtonView");
                setCenter("mMenuView");
            }

        });
    }

    /**
     * ，反射！通过反射拿到对应的View，然后修改LayoutParams.gravity= Gravity.CENTER即可。
     *
     * @param fieldName
     */
    private void setCenter(String fieldName) {
        try {
            Field field = getClass().getSuperclass().getDeclaredField(fieldName);//反射得到父类Field
            field.setAccessible(true);
            Object obj = field.get(this);//拿到对应的Object
            if (obj == null) return;
            if (obj instanceof View) {
                View view = (View) obj;
                ViewGroup.LayoutParams lp = view.getLayoutParams();//拿到LayoutParams
                if (lp instanceof ActionBar.LayoutParams) {
                    ActionBar.LayoutParams params = (ActionBar.LayoutParams) lp;
                    params.gravity = Gravity.CENTER;//设置居中
                    view.setLayoutParams(lp);
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    private void init(Context context, @Nullable AttributeSet attrs) {
//        setId(R.id.toolbar);
//        setNavigationIcon(R.drawable.ic_back_normal);

        if (isInEditMode()) return;
        if (attrs == null) return;
        TypedArray typedArr = context.obtainStyledAttributes(attrs,
                R.styleable.MyToolBar);

        String titleText = typedArr.getString(
                R.styleable.MyToolBar_titleText);
        if (!TextUtils.isEmpty(titleText)) setTitleText(titleText);
        else {
            int StringResId = typedArr.getResourceId(R.styleable.MyToolBar_titleText, 0);
            if (StringResId != 0) setTitleText(StringResId);
        }

        String titleLeftText = typedArr.getString(
                R.styleable.MyToolBar_titleLeftText);
        if (!TextUtils.isEmpty(titleLeftText)) setTitleLeftText(titleLeftText);
        else {
            int StringResId = typedArr.getResourceId(R.styleable.MyToolBar_titleLeftText, 0);
            if (StringResId != 0) setTitleLeftText(StringResId);
        }

        String titleRightText = typedArr.getString(
                R.styleable.MyToolBar_titleRightText);
        if (!TextUtils.isEmpty(titleRightText)) setTitleRightText(titleRightText);
        else {
            int StringResId = typedArr.getResourceId(R.styleable.MyToolBar_titleRightText, 0);
            if (StringResId != 0) setTitleRightText(StringResId);
        }
        int drawableRes = typedArr.getResourceId(
                R.styleable.MyToolBar_titleRightDrawable, 0);
        if (drawableRes != 0) setTitleRightDrawable(drawableRes);
        typedArr.recycle();
    }

    /*private void addChildrens(Context context) {
        titleTv = buildTitleTv(context);
        Toolbar.LayoutParams tlpCenter =
                new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                        , Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        titleTv.setLayoutParams(tlpCenter);
        addView(titleTv);

        titleLeftTv = buildTitleTv(context);
        Toolbar.LayoutParams tlpLeft =
                new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                        , Gravity.TOP | Gravity.LEFT);
        tlpLeft.leftMargin = UIUtils.getDimens(R.dimen.dp16);
        titleLeftTv.setLayoutParams(tlpLeft);
        addView(titleLeftTv);

        titleRightTv = buildTitleTv(context);
        Toolbar.LayoutParams tlpRight =
                new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                        , Gravity.TOP | Gravity.RIGHT);
        tlpRight.rightMargin = UIUtils.getDimens(R.dimen.dp16);
        titleRightTv.setLayoutParams(tlpRight);
        addView(titleRightTv);

        titleRightIv = new ImageView(context);
        titleRightIv.setLayoutParams(tlpRight);
        addView(titleRightIv);
    }*/

    private TextView buildTitleTv(Context context) {
        TextView tv = new TextView(context);
        tv.setTextColor(UIUtils.getColor(R.color.titleTextColor));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, UIUtils.getDimens(R.dimen.sp15));
        tv.setVisibility(View.INVISIBLE);
        return tv;
    }

    private void addLeftTv() {
        if (titleLeftTv != null) return;
        titleLeftTv = buildTitleTv(getContext());
        LayoutParams tlpLeft =
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
//                        , Gravity.TOP | Gravity.LEFT);
                        , Gravity.CENTER_VERTICAL | Gravity.LEFT);
        tlpLeft.leftMargin = UIUtils.getDimens(R.dimen.activity_horizontal_margin);
        titleLeftTv.setLayoutParams(tlpLeft);
        addView(titleLeftTv);
    }

    private void addCenterTv() {
        if (titleTv != null) return;
        titleTv = buildTitleTv(getContext());
        titleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, UIUtils.getDimens(R.dimen.sp18));
        titleTv.setMaxEms(6);
        titleTv.setMaxLines(1);
        titleTv.setEllipsize(TextUtils.TruncateAt.END);
        LayoutParams tlpCenter =
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
//                        , Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                        , Gravity.CENTER);
        titleTv.setLayoutParams(tlpCenter);
        addView(titleTv);
    }


    private void addRightTv() {
        if (titleRightTv != null) return;
        titleRightTv = buildTitleTv(getContext());
        /*Toolbar.LayoutParams tlpRight =
                new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                        , Gravity.TOP | Gravity.RIGHT);
        tlpRight.rightMargin = UIUtils.getDimens(R.dimen.dp16);
        titleRightTv.setLayoutParams(tlpRight);*/
        FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        flp.gravity = Gravity.CENTER_VERTICAL;
        titleRightFrame.addView(titleRightTv, flp);
    }

    private void addRightIv() {
        if (titleRightIv != null) {
            return;
        }
        titleRightIv = new ImageView(getContext());
        /*Toolbar.LayoutParams tlpRight =
                new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                        , Gravity.TOP | Gravity.RIGHT);
        tlpRight.rightMargin = UIUtils.getDimens(R.dimen.dp16);
        titleRightIv.setLayoutParams(tlpRight);*/
        FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        flp.gravity = Gravity.CENTER_VERTICAL;
        titleRightFrame.addView(titleRightIv, flp);
    }

    public MyToolBar setTitleLeftText(CharSequence titleLeftText) {
        addLeftTv();
        titleLeftTv.setText(titleLeftText);
        titleLeftTv.setVisibility(View.VISIBLE);
        return this;
    }

    public MyToolBar setTitleLeftText(int StringResId) {
        return setTitleLeftText(UIUtils.getString(StringResId));
    }

    public void clearLeft() {
        if (titleLeftTv == null) return;
        titleLeftTv.setVisibility(View.GONE);
        titleLeftTv.setOnClickListener(null);
    }

    public MyToolBar setTitleText(CharSequence titleText) {
        addCenterTv();
        titleTv.setText(titleText);
        titleTv.setVisibility(View.VISIBLE);
        return this;
    }

    public MyToolBar setTitleText(int StringResId) {
        return setTitleText(UIUtils.getString(StringResId));
    }

    public MyToolBar setTitleRightText(String titleRightText) {
        initRightFrame();
        addRightTv();
        titleRightTv.setText(titleRightText);
        titleRightTv.setVisibility(View.VISIBLE);
        if (titleRightIv != null) titleRightIv.setVisibility(View.GONE);
        return this;
    }

    private void initRightFrame() {
        if (titleRightFrame != null) return;
        titleRightFrame = new FrameLayout(getContext());

        titleRightFrame.setMinimumHeight(MyToolBar.this.getHeight());
        LayoutParams tlpRight =
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
//                        , Gravity.TOP | Gravity.RIGHT);
                        , Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        tlpRight.rightMargin = UIUtils.getDimens(R.dimen.activity_horizontal_margin);
        titleRightFrame.setLayoutParams(tlpRight);
        addView(titleRightFrame);
    }

    public void clearRight() {
        if (titleRightFrame == null) return;
        titleRightFrame.setOnClickListener(null);
        if (titleRightTv != null)
            titleRightTv.setVisibility(View.GONE);
        if (titleRightIv != null)
            titleRightIv.setVisibility(View.GONE);

    }

    public MyToolBar setTitleRightText(int StringResId) {
        return setTitleRightText(UIUtils.getString(StringResId));
    }

    public MyToolBar setTitleRightDrawable(int drawResId) {
        initRightFrame();
        addRightIv();
        titleRightIv.setImageResource(drawResId);
        titleRightIv.setVisibility(View.VISIBLE);
        if (titleRightTv != null) titleRightTv.setVisibility(View.GONE);
        return this;
    }

    public MyToolBar setTitleLeftClickListener(OnClickListener onClickListener) {
        addLeftTv();
        titleLeftTv.setOnClickListener(onClickListener);
        return this;
    }

    public MyToolBar setTitleRightClickListener(final OnClickListener onClickListener) {
        initRightFrame();
        titleRightFrame.setOnClickListener(onClickListener);
      /*  if(titleRightIv==null&&titleRightTv==null){
            addRightTv();
            titleRightTv.setOnClickListener(onClickListener);
            return this;
        }
        if (titleRightTv != null)
            titleRightTv.setOnClickListener(onClickListener);
        if (titleRightIv != null)
            titleRightIv.setOnClickListener(onClickListener);*/
        return this;
    }

    public TextView getTitleLeftTv() {
        addLeftTv();
        return titleLeftTv;
    }

    public FrameLayout getTitleRightFrame() {
        initRightFrame();
        return titleRightFrame;
    }

    public TextView getTitleTv() {
        addCenterTv();
        return titleTv;
    }


    /*------------------    让toolbar的title居中--------------------*/
/*    public class MyToolbar extends android.support.v7.widget.Toolbar {
        privateTextView mTitleTextView;
        privateCharSequence mTitleText;
        private int mTitleTextColor;
        private int mTitleTextAppearance;
        publicToolbar(Context context) {
            super(context);
            resolveAttribute(context, null,R.attr.toolbarStyle);
        }
        publicToolbar(Context context,@NullableAttributeSet attrs) {
            super(context,attrs);
            resolveAttribute(context,attrs,R.attr.toolbarStyle);
        }
        publicToolbar(Context context,@NullableAttributeSet attrs, intdefStyleAttr) {
            super(context,attrs,defStyleAttr);
            resolveAttribute(context,attrs,defStyleAttr);
        }
        private voidresolveAttribute(Context context,@NullableAttributeSet attrs, intdefStyleAttr) {
// Need to use getContext() here so that we use the themed context
            context = getContext();
            finalTintTypedArray a = TintTypedArray.obtainStyledAttributes(context,attrs,
                    R.styleable.Toolbar,defStyleAttr,0);
            final inttitleTextAppearance = a.getResourceId(R.styleable.Toolbar_titleTextAppearance,0);
            if(titleTextAppearance !=0) {
                setTitleTextAppearance(context,titleTextAppearance);
            }
            if(mTitleTextColor!=0) {
                setTitleTextColor(mTitleTextColor);
            }
            a.recycle();
            post(newRunnable() {
@Override
public voidrun() {
                    if(getLayoutParams()instanceofLayoutParams) {
                        Log.v(Toolbar.class,"is Toolbar.LayoutParams");
                        ((LayoutParams) getLayoutParams()).gravity= Gravity.CENTER;
                    }
                }
            });
        }
        @Override
        publicCharSequencegetTitle() {
            returnmTitleText;
        }
        @Override
        public voidsetTitle(CharSequence title) {
            if(!TextUtils.isEmpty(title)) {
                if(mTitleTextView==null) {
                    finalContext context = getContext();
                    mTitleTextView=newTextView(context);
                    mTitleTextView.setSingleLine();
                    mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                    if(mTitleTextAppearance!=0) {
                        mTitleTextView.setTextAppearance(context,mTitleTextAppearance);
                    }
                    if(mTitleTextColor!=0) {
                        mTitleTextView.setTextColor(mTitleTextColor);
                    }
                }
                if(mTitleTextView.getParent() !=this) {
                    addCenterView(mTitleTextView);
                }
            }else if(mTitleTextView!=null&&mTitleTextView.getParent() ==this) {// 当title为空时，remove
                removeView(mTitleTextView);
            }
            if(mTitleTextView!=null) {
                mTitleTextView.setText(title);
            }
            mTitleText= title;
        }
        private voidaddCenterView(View v) {
            finalViewGroup.LayoutParams vlp = v.getLayoutParams();
            finalLayoutParams lp;
            if(vlp ==null) {
                lp = generateDefaultLayoutParams();
            }else if(!checkLayoutParams(vlp)) {
                lp = generateLayoutParams(vlp);
            }else{
                lp = (LayoutParams) vlp;
            }
            addView(v,lp);
        }
        @Override
        publicLayoutParamsgenerateLayoutParams(AttributeSet attrs) {
            LayoutParams lp =newLayoutParams(getContext(),attrs);
            lp.gravity= Gravity.CENTER;
            returnlp;
        }
        @Override
        protectedLayoutParamsgenerateLayoutParams(ViewGroup.LayoutParams p) {
            LayoutParams lp;
            if(pinstanceofLayoutParams) {
                lp =newLayoutParams((LayoutParams) p);
            }else if(pinstanceofActionBar.LayoutParams) {
                lp =newLayoutParams((ActionBar.LayoutParams) p);
            }else if(pinstanceofMarginLayoutParams) {
                lp =newLayoutParams((MarginLayoutParams) p);
            }else{
                lp =newLayoutParams(p);
            }
            lp.gravity= Gravity.CENTER;
            returnlp;
        }
        @Override
        protectedLayoutParamsgenerateDefaultLayoutParams() {
            LayoutParams lp =newLayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            lp.gravity= Gravity.CENTER;
            returnlp;
        }
        @Override
        public voidsetTitleTextAppearance(Context context,@StyleResintresId) {
            mTitleTextAppearance= resId;
            if(mTitleTextView!=null) {
                mTitleTextView.setTextAppearance(context,resId);
            }
        }
        @Override
        public voidsetTitleTextColor(@ColorIntintcolor) {
            mTitleTextColor= color;
            if(mTitleTextView!=null) {
                mTitleTextView.setTextColor(color);
            }
        }
    }*/

}
