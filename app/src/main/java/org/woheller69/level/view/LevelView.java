package org.woheller69.level.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

import androidx.annotation.NonNull;

import org.woheller69.level.orientation.Orientation;
import org.woheller69.level.painter.LevelPainter;

/*
 *  This file is part of Level (an Android Bubble Level).
 *  <https://github.com/avianey/Level>
 *
 *  Copyright (C) 2014 Antoine Vianey
 *
 *  Level is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Level is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Level. If not, see <http://www.gnu.org/licenses/>
 */
public class LevelView extends SurfaceView implements SurfaceHolder.Callback, OnTouchListener {

    private LevelPainter painter;

    public LevelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        setFocusable(true);
        setOnTouchListener(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (painter != null) {
            painter.pause(!hasWindowFocus);
        }
    }

    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        if (painter != null) {
            painter.setSurfaceSize(width, height);
        }
    }

    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        if (painter == null) {
            painter = new LevelPainter(holder, getContext(), new Handler(Looper.getMainLooper()));
        }
    }

    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        if (painter != null) {
            painter.pause(true);
            painter.clean();
            painter = null;
        }
        // free resources
        System.gc();
    }

    public void onOrientationChanged(Orientation orientation, float pitch, float roll, float balance) {
        if (painter != null) {
            painter.onOrientationChanged(orientation, pitch, roll, balance);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && painter != null) {
            painter.onTouch((int) event.getX(), (int) event.getY());
        }
        return true;
    }
}
