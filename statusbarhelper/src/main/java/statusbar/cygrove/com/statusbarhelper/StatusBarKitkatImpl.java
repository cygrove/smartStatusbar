package statusbar.cygrove.com.statusbarhelper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * 兼容KITKAT版本
 */
class StatusBarKitkatImpl implements IStatusBar {

    private static final String TAG_FAKE_STATUS_BAR_VIEW = "statusBarView";
    private static final String TAG_MARGIN_ADDED = "marginAdded";

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void setStatusBarColor(Window window, int color, boolean lightStatusBar) {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup decorViewGroup = (ViewGroup) window.getDecorView();
        View statusBarView = new View(window.getContext());
        int statusBarHeight = StatusBarHelper.getStatusBarHeight(window.getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
        params.gravity = Gravity.TOP;
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(color);
        decorViewGroup.addView(statusBarView);
        StatusBarHelper.setFitsSystemWindows(window, true);

        StatusBarHelperFlavorRom.setLightStatusBar(window, lightStatusBar);
    }


    /**
     * translucentStatusBar
     * <p>
     * 1. set Window Flag : WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
     * 2. removeFakeStatusBarViewIfExist
     * 3. removeMarginTopOfContentChild
     * 4. cancel ContentChild's fitsSystemWindow
     */
    static void translucentStatusBar(Activity activity) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        View mContentChild = mContentView.getChildAt(0);

        removeFakeStatusBarViewIfExist(activity);
        removeMarginTopOfContentChild(mContentChild, StatusBarHelper.getStatusBarHeight(activity));
        if (mContentChild != null) {
            ViewCompat.setFitsSystemWindows(mContentChild, false);
        }
    }

    /**
     * use reserved order to remove is more quickly.
     */
    private static void removeFakeStatusBarViewIfExist(Activity activity) {
        Window window = activity.getWindow();
        ViewGroup mDecorView = (ViewGroup) window.getDecorView();

        View fakeView = mDecorView.findViewWithTag(TAG_FAKE_STATUS_BAR_VIEW);
        if (fakeView != null) {
            mDecorView.removeView(fakeView);
        }
    }


    /**
     * remove marginTop to simulate set FitsSystemWindow false
     */
    private static void removeMarginTopOfContentChild(View mContentChild, int statusBarHeight) {
        if (mContentChild == null) {
            return;
        }
        if (TAG_MARGIN_ADDED.equals(mContentChild.getTag())) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContentChild.getLayoutParams();
            lp.topMargin -= statusBarHeight;
            mContentChild.setLayoutParams(lp);
            mContentChild.setTag(null);
        }
    }
}