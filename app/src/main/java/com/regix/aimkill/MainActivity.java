package com.regix.aimkill;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.animation.ValueAnimator;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    static {
        try {
            System.loadLibrary("REGIX_MENU");
        } catch (UnsatisfiedLinkError e) {
            android.util.Log.e("MainActivity", "Lib load failed: " + e.getMessage());
        }
    }

    private static final int REQUEST_OVERLAY_PERMISSION = 1234;
    private LinearLayout splashLayout;

    // Unique red theme colors
    private static final int PRIMARY_RED = 0xFFFF1744;
    private static final int DARK_RED = 0xFFD50000;
    private static final int LIGHT_RED = 0xFFFF5252;
    private static final int DARK_BG = 0xFF0D0D0D;
    private static final int CARD_BG = 0xFF1A1A1A;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) actionBar.hide();
        getWindow().setBackgroundDrawable(new ColorDrawable(DARK_BG));

        showSplash();
        new Handler().postDelayed(this::checkOverlayAndStartLogin, 1800);
    }

    private void checkOverlayAndStartLogin() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_OVERLAY_PERMISSION);
            } else {
                startLoginUI();
            }
        } else {
            startLoginUI();
        }
    }

    private void startLoginUI() {
        runCloseAnimationThen(() -> {
            new Handler().postDelayed(() -> {
                try {
                    new Login(MainActivity.this);
                } catch (Exception e) {
                    android.util.Log.e("MainActivity", "Login Error: " + e.getMessage());
                }
            }, 100);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_OVERLAY_PERMISSION) {
            startLoginUI();
        }
    }

    private void showSplash() {
        splashLayout = new LinearLayout(this);
        splashLayout.setOrientation(LinearLayout.VERTICAL);
        splashLayout.setGravity(Gravity.CENTER);
        splashLayout.setBackgroundColor(Color.TRANSPARENT);

        // Red rain animation background
        RainView rainView = new RainView(this);
        rainView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        splashLayout.addView(rainView);

        LinearLayout titleRow = new LinearLayout(this);
        titleRow.setGravity(Gravity.CENTER);
        titleRow.setOrientation(LinearLayout.HORIZONTAL);

        // Staggered letter animation for "REGIX"
        String brandShort = "REGIX";
        for (int i = 0; i < brandShort.length(); i++) {
            final TextView letter = new TextView(this);
            letter.setText(String.valueOf(brandShort.charAt(i)));
            letter.setTextSize(52);
            letter.setTypeface(Typeface.DEFAULT_BOLD);
            letter.setAlpha(0f);
            letter.setTranslationY(40);
            letter.setTextColor(PRIMARY_RED);
            letter.setShadowLayer(10, 0, 0, 0x80FF1744);
            
            final int index = i;
            new Handler().postDelayed(() -> {
                letter.animate()
                    .alpha(1f)
                    .translationY(0)
                    .setDuration(500)
                    .setStartDelay(index * 100)
                    .start();
            }, 300);
            
            titleRow.addView(letter);
        }

        // Add space
        TextView space = new TextView(this);
        space.setText("  ");
        space.setTextSize(52);
        titleRow.addView(space);

        // Staggered letter animation for "AIMKILL"
        String brandLong = "AIMKILL";
        for (int i = 0; i < brandLong.length(); i++) {
            final TextView letter = new TextView(this);
            letter.setText(String.valueOf(brandLong.charAt(i)));
            letter.setTextSize(52);
            letter.setTypeface(Typeface.DEFAULT_BOLD);
            letter.setTextColor(PRIMARY_RED);
            letter.setAlpha(0f);
            letter.setTranslationY(40);
            letter.setShadowLayer(8, 0, 0, 0x80FF1744);
            
            final int index = i;
            new Handler().postDelayed(() -> {
                letter.animate()
                    .alpha(1f)
                    .translationY(0)
                    .setDuration(500)
                    .setStartDelay(600 + index * 50)
                    .start();
            }, 300);
            
            titleRow.addView(letter);
        }

        // Animated accent underline
        View accentLine = new View(this);
        LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(300, 6);
        lineParams.setMargins(0, 24, 0, 0);
        accentLine.setLayoutParams(lineParams);
        GradientDrawable lineGradient = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, 
            new int[]{0x00FF1744, LIGHT_RED, PRIMARY_RED, DARK_RED, 0x00FF1744});
        lineGradient.setCornerRadius(3);
        accentLine.setBackground(lineGradient);

        splashLayout.addView(titleRow);
        splashLayout.addView(accentLine);
        setContentView(splashLayout);

        // Animate accent line
        accentLine.setScaleX(0f);
        accentLine.animate().scaleX(1f).setDuration(700).setStartDelay(1000).start();
    }

    private void runCloseAnimationThen(Runnable onEnd) {
        if (splashLayout == null) {
            if (onEnd != null) onEnd.run();
            return;
        }
        splashLayout.animate()
                .alpha(0f)
                .scaleX(0.8f)
                .scaleY(0.8f)
                .setDuration(400)
                .withEndAction(onEnd)
                .start();
    }

    private static class RainView extends View {
        private final float[] dropX;
        private final float[] dropY;
        private final float[] dropSpeed;
        private final float[] dropLength;
        private final int dropCount = 100;
        private final Paint paint = new Paint();
        private boolean running = true;

        public RainView(android.content.Context context) {
            super(context);
            paint.setColor(0x80FF1744);
            paint.setStrokeWidth(3);
            paint.setStyle(Paint.Style.FILL);
            dropX = new float[dropCount];
            dropY = new float[dropCount];
            dropSpeed = new float[dropCount];
            dropLength = new float[dropCount];
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            for (int i = 0; i < dropCount; i++) {
                dropX[i] = (float) (Math.random() * w);
                dropY[i] = (float) (Math.random() * h);
                dropSpeed[i] = (float) (Math.random() * 10 + 5);
                dropLength[i] = (float) (Math.random() * 20 + 10);
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int w = getWidth();
            int h = getHeight();
            for (int i = 0; i < dropCount; i++) {
                canvas.drawLine(dropX[i], dropY[i], dropX[i], dropY[i] + dropLength[i], paint);
                dropY[i] += dropSpeed[i];
                if (dropY[i] > h) {
                    dropY[i] = -dropLength[i];
                    dropX[i] = (float) (Math.random() * w);
                }
            }
            if (running) {
                invalidate();
            }
        }
    }
}

