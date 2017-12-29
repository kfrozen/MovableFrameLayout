package com.troy.movableframelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

/**
 */
public class MovableFrameLayout extends FrameLayout
{
    private boolean mMovable;  //drag to move the view

    private boolean mIsFullScreen;

    private Helper mHelper;

    private float mTouchSlop;

    public MovableFrameLayout(Context context)
    {
        super(context);

        initialize();
    }

    public MovableFrameLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        initialize();
    }

    public MovableFrameLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);

        initialize();
    }

    /**
    *  @param enabled when set to false, this view will become a normal FrameLayout,
     *                and those made translations will be reset also.
    */
    public void setMovable(boolean enabled)
    {
        mMovable = enabled;

        if (!mMovable)
        {
            setTranslationX(0);

            setTranslationY(0);
        }
    }

    /**
    *  Use this method if your content view had a fullscreen state. For example, a video player.
    */
    public void setFullScreen(boolean isFullScreen)
    {
        mIsFullScreen = isFullScreen;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (mMovable)
        {
            super.onTouchEvent(event);

            return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        if(mIsFullScreen)
        {
            final int action = ev.getAction();

            switch (action)
            {
                case MotionEvent.ACTION_DOWN:
                {
                    requestDisallowInterceptTouchEvent(true);

                    break;
                }
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_OUTSIDE:
                {
                    requestDisallowInterceptTouchEvent(false);

                    break;
                }
            }
        }

        if (mMovable && mHelper.onTouchEvent(ev))
        {
            return true;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams()
    {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    private void initialize()
    {
        mHelper = new Helper();

        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

        mMovable = true;
    }

    // --------------------------------------------------------------------------
    // --------------------------------------------------------------------------
    // Helper
    // --------------------------------------------------------------------------
    // --------------------------------------------------------------------------

    private class Helper
    {
        private float mLastMotionX;

        private float mLastMotionY;

        private boolean mIsBeingDragged;

        public boolean onTouchEvent(MotionEvent event)
        {
            if (mMovable)
            {
                final int action = event.getAction();

                final float x = event.getRawX();

                final float y = event.getRawY();

                switch (action)
                {
                    case MotionEvent.ACTION_DOWN:
                    {
                        requestDisallowInterceptTouchEvent(true);

                        mLastMotionX = x;

                        mLastMotionY = y;

                        return false;
                    }
                    case MotionEvent.ACTION_MOVE:
                    {
                        final int deltaX = (int) (x - mLastMotionX);

                        final int deltaY = (int) (y - mLastMotionY);

                        if (mIsBeingDragged)
                        {
                            innerHandleActionMove(x, y, deltaX, deltaY);
                        }
                        else if(Math.abs(deltaX) >= mTouchSlop || Math.abs(deltaY) >= mTouchSlop)
                        {
                            innerHandleActionMove(x, y, deltaX, deltaY);

                            mIsBeingDragged = true;
                        }

                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_OUTSIDE:
                    {
                        if (mIsBeingDragged)
                        {
                            mLastMotionX = 0;

                            mLastMotionY = 0;

                            mIsBeingDragged = false;

                            requestDisallowInterceptTouchEvent(false);
                        }
                        else
                        {
                            return false;
                        }

                        break;
                    }
                }
            }

            return true;
        }

        private void innerHandleActionMove(float x, float y, int deltaX, int deltaY)
        {
            mLastMotionX = x;

            mLastMotionY = y;

            move(deltaX, deltaY);
        }

        private void move(int x, int y)
        {
            final float tX = (getTranslationX() + x);

            final float tY = (getTranslationY() + y);

            if (tX + getLeft() >= 0)
            {
                if(getParent() instanceof View)
                {
                    if(tX + getRight() <= ((View) getParent()).getWidth())
                    {
                        setTranslationX(tX);
                    }
                }
                else
                {
                    setTranslationX(tX);
                }
            }

            if (tY + getTop() >= 0)
            {
                if(getParent() instanceof View)
                {
                    if(tY + getBottom() <= ((View) getParent()).getHeight())
                    {
                        setTranslationY(tY);
                    }
                }
                else
                {
                    setTranslationY(tY);
                }
            }
        }
    }
}
