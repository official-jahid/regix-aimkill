package com.regix.aimkill;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParticlesView extends View {

    private static final int PARTICLE_COUNT = 65;
    private static final int PRIMARY_COLOR = 0xFFE53935;   // Red (UPGRADE theme)
    private static final int ACCENT_COLOR = 0xFFFF5722;   // Orange

    private final List<Star> stars = new ArrayList<>();
    private final Paint paint = new Paint();
    private final Paint bgPaint = new Paint();
    private final Random random = new Random();

    public ParticlesView(Context context) {
        super(context);
        init();
    }

    public ParticlesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createStars(w, h);
        setupGradient(w, h);
    }

    private void setupGradient(int w, int h) {
        int topColor = 0xFF0D0D0D;
        int bottomColor = 0xFF141414;
        bgPaint.setShader(new LinearGradient(0, 0, 0, h, topColor, bottomColor, Shader.TileMode.CLAMP));
    }

    private void createStars(int width, int height) {
        stars.clear();
        for (int i = 0; i < PARTICLE_COUNT; i++) {
            stars.add(new Star(width, height, true));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (bgPaint.getShader() != null) {
            canvas.drawRect(0, 0, getWidth(), getHeight(), bgPaint);
        }

        for (Star star : stars) {
            star.update(getWidth(), getHeight());

            paint.setStrokeWidth(star.size);
            paint.setColor(star.color);
            paint.setAlpha(star.alpha);

            canvas.drawLine(star.x, star.y, star.x, star.y - star.length, paint);
        }

        invalidate();
    }

    private class Star {
        float x, y;
        float speed;
        float size;
        float length;
        int alpha;
        int color;

        Star(int width, int height, boolean randomizeY) {
            reset(width, height, randomizeY);
        }

        void reset(int width, int height, boolean randomizeY) {
            x = random.nextFloat() * width;
            y = randomizeY ? random.nextFloat() * height : -random.nextFloat() * 150;

            float z = random.nextFloat();

            speed = 2.5f + z * 5.0f;
            size = 0.8f + z * 1.5f;
            length = 15.0f + z * 45.0f;
            alpha = 35 + (int)(z * 90);

            float colorChance = random.nextFloat();
            if (colorChance > 0.85f) {
                color = 0xFFFFFFFF; // White
            } else if (colorChance > 0.5f) {
                color = PRIMARY_COLOR; // Red
            } else {
                color = ACCENT_COLOR;  // Orange
            }
        }

        void update(int width, int height) {
            y += speed;
            if (y - length > height) {
                reset(width, height, false);
            }
        }
    }
}

