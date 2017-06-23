# MovableFrameLayout
A customized FrameLayout that responses to touch events and can move after your finger. This can be applied as a view wrapper.

# Demo

![](https://github.com/kfrozen/MovableFrameLayout/raw/master/logo/movableframelayoutDemo.gif)

# Usage

	dependencies {
	    compile 'com.troy.movableframelayout:movableframelayout:1.0.0'
	}

# Integration

Just wrap your content view inside of it.

    <com.troy.movableframelayout.MovableFrameLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="left|top">

            <!-- Here should be the main content view, a TextView for example -->
            <TextView
                android:layout_width="100dp"
                android:layout_height="100dp"
                android:text="Movable Block A"
                android:textColor="#ffffff"
                android:textSize="14sp"
                android:textStyle="bold"
                android:gravity="center"
                android:background="@color/colorPrimary"/>

    </com.troy.movableframelayout.MovableFrameLayout>

**Public methods**

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

