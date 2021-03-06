/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.facebook.react.views.swiperefresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MotionEvent;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.events.NativeGestureUtil;

/**
 * Basic extension of {@link SwipeRefreshLayout} with ReactNative-specific functionality.
 */
public class ReactSwipeRefreshLayout extends SwipeRefreshLayout {

  private boolean mRefreshing = false;

  public ReactSwipeRefreshLayout(ReactContext reactContext) {
    super(reactContext);
  }

  @Override
  public void setRefreshing(boolean refreshing) {
    if (mRefreshing != refreshing) {
      mRefreshing = refreshing;
      // Use `post` otherwise the control won't start refreshing if refreshing is true when
      // the component gets mounted.
      post(new Runnable() {
        @Override
        public void run() {
          ReactSwipeRefreshLayout.super.setRefreshing(mRefreshing);
        }
      });
    }
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    if (super.onInterceptTouchEvent(ev)) {
      NativeGestureUtil.notifyNativeGestureStarted(this, ev);
      return true;
    }
    return false;
  }
}
