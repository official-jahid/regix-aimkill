package com.regix.aimkill;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.animation.ValueAnimator;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Login {

    // Unique red theme colors
    private static final int PrimaryColor = 0xFFFF1744;
    private static final int DarkRed = 0xFFD50000;
    private static final int LightRed = 0xFFFF5252;
    private static final int CardBgColor = 0xF0121212;
    private static final int InputBgColor = 0xFF1E1E1E;
    private static final int DarkBg = 0xFF0A0A0A;

    private static String sLoginUsername = "";
    private static String sLoginPassword = "";
    public static String getStoredUsername() { return sLoginUsername; }
    public static String getStoredPassword() { return sLoginPassword; }

    private TextView logo;
    private TextView copyright;
    public void setLogoText(String value) {
        if (logo != null) { logo.setText(value); logo.setVisibility(View.VISIBLE); logo.invalidate(); }
    }
    public void setCopyrightText(String value) {
        if (copyright != null) copyright.setText(value);
    }

    private Context context;
    private Utils utils;
    private ImageString imageString;
    private Handler mainHandler;
    private EditText input_username;
    private String selectedTargetPackage = "com.dts.freefireth";
    private LinearLayout cardContainer;
    private int selectedInjectType = 0;

    private static final String APPNAME    = "regix aimkill";
    private static final String OWNERID    = "RTgStl6UQK";
    private static final String SECRET     = "edf522cb0077ae716c23b64d0f8f7f12b96db899b2be04a657c274ec86a5fac1";
    private static final String VERSION    = "1.0";
    private static final String[] ALLOWED_PACKAGES = { "REGIXCOVER" };
    private static final String LICENSEAUTH_URL = "https://licenseauth.help/api/1.3/";

    public static final String PREF_NAME = "LoginPrefs";
    public static final String LICENSE_KEY = "key";

    public Login(Context ctx) {
        context = ctx;
        utils = new Utils(context);
        imageString = new ImageString();
        mainHandler = new Handler(Looper.getMainLooper());
        initUI();
    }

    private void initUI() {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String savedKey = preferences.getString(LICENSE_KEY, "");

        FrameLayout container = new FrameLayout(context);
        container.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        container.setBackgroundColor(DarkBg);
        
        // Red rain animation background
        RainView rainView = new RainView(context);
        rainView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        container.addView(rainView);

        cardContainer = new LinearLayout(context);
        FrameLayout.LayoutParams cardParams = new FrameLayout.LayoutParams(utils.FixDP(300), ViewGroup.LayoutParams.WRAP_CONTENT);
        cardParams.gravity = Gravity.CENTER;
        cardContainer.setLayoutParams(cardParams);
        cardContainer.setOrientation(LinearLayout.VERTICAL);
        cardContainer.setGravity(Gravity.CENTER);
        cardContainer.setPadding(utils.FixDP(32), utils.FixDP(32), utils.FixDP(32), utils.FixDP(28));
        cardContainer.setAlpha(0f);
        cardContainer.setScaleX(0.85f);
        cardContainer.setScaleY(0.85f);

        GradientDrawable cardBg = new GradientDrawable();
        cardBg.setCornerRadius(utils.FixDP(24));
        cardBg.setColor(CardBgColor);
        cardBg.setStroke(2, 0x30FF1744);
        cardContainer.setBackground(cardBg);

        // Brand
        LinearLayout brandRow = new LinearLayout(context);
        brandRow.setOrientation(LinearLayout.HORIZONTAL);
        brandRow.setGravity(Gravity.CENTER);

        // Staggered letter animation for "REGIX"
        String brandShort = "REGIX";
        for (int i = 0; i < brandShort.length(); i++) {
            final TextView letter = new TextView(context);
            letter.setText(String.valueOf(brandShort.charAt(i)));
            letter.setTextSize(32);
            letter.setTypeface(android.graphics.Typeface.create("sans-serif", android.graphics.Typeface.BOLD));
            letter.setAlpha(0f);
            letter.setTranslationY(30);
            letter.setTextColor(PrimaryColor);
            letter.setShadowLayer(8, 0, 0, 0x60FF1744);
            
            final int index = i;
            new Handler().postDelayed(() -> {
                letter.animate()
                    .alpha(1f)
                    .translationY(0)
                    .setDuration(400)
                    .setStartDelay(400 + index * 80)
                    .start();
            }, 200);
            
            brandRow.addView(letter);
        }

        // Add space
        TextView space = new TextView(context);
        space.setText("  ");
        space.setTextSize(32);
        brandRow.addView(space);

        // Staggered letter animation for "AIMKILL"
        String brandLong = "AIMKILL";
        for (int i = 0; i < brandLong.length(); i++) {
            final TextView letter = new TextView(context);
            letter.setText(String.valueOf(brandLong.charAt(i)));
            letter.setTextSize(32);
            letter.setTypeface(android.graphics.Typeface.create("sans-serif", android.graphics.Typeface.BOLD));
            letter.setTextColor(PrimaryColor);
            letter.setAlpha(0f);
            letter.setTranslationY(30);
            letter.setShadowLayer(6, 0, 0, 0x60FF1744);
            
            final int index = i;
            new Handler().postDelayed(() -> {
                letter.animate()
                    .alpha(1f)
                    .translationY(0)
                    .setDuration(400)
                    .setStartDelay(600 + index * 40)
                    .start();
            }, 200);
            
            brandRow.addView(letter);
        }

        cardContainer.addView(brandRow);

        // Accent line under brand
        View accentLine = new View(context);
        LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(utils.FixDP(60), 3);
        lineParams.setMargins(0, utils.FixDP(10), 0, utils.FixDP(6));
        accentLine.setLayoutParams(lineParams);
        GradientDrawable lineGradient = new GradientDrawable();
        lineGradient.setColors(new int[]{LightRed, PrimaryColor, DarkRed});
        lineGradient.setCornerRadius(2);
        accentLine.setBackground(lineGradient);
        cardContainer.addView(accentLine);

        // Package Toggle
        LinearLayout toggleBox = new LinearLayout(context);
        LinearLayout.LayoutParams toggleBoxParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, utils.FixDP(40));
        toggleBoxParams.setMargins(0, utils.FixDP(24), 0, 0);
        toggleBox.setLayoutParams(toggleBoxParams);
        toggleBox.setOrientation(LinearLayout.HORIZONTAL);
        toggleBox.setGravity(Gravity.CENTER);
        GradientDrawable toggleBg = new GradientDrawable();
        toggleBg.setCornerRadius(utils.FixDP(20));
        toggleBg.setColor(InputBgColor);
        toggleBox.setBackground(toggleBg);

        Button btnFF = new Button(context);
        Button btnFFMax = new Button(context);
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f);

        btnFF.setLayoutParams(btnParams);
        btnFFMax.setLayoutParams(btnParams);
        btnFF.setText("FREE FIRE");
        btnFFMax.setText("FF MAX");

        btnFF.setTextSize(10);
        btnFFMax.setTextSize(10);
        btnFF.setTypeface(null, android.graphics.Typeface.BOLD);
        btnFFMax.setTypeface(null, android.graphics.Typeface.BOLD);

        Runnable refreshToggles = () -> {
            if ("com.dts.freefireth".equals(selectedTargetPackage)) {
                GradientDrawable selectedBg = new GradientDrawable();
                selectedBg.setCornerRadius(utils.FixDP(18));
                selectedBg.setColors(new int[]{LightRed, PrimaryColor, DarkRed});
                btnFF.setBackground(selectedBg);
                btnFF.setTextColor(0xFFFFFFFF);
                btnFFMax.setBackgroundColor(Color.TRANSPARENT);
                btnFFMax.setTextColor(0xFF666666);
            } else {
                GradientDrawable selectedBg = new GradientDrawable();
                selectedBg.setCornerRadius(utils.FixDP(18));
                selectedBg.setColors(new int[]{LightRed, PrimaryColor, DarkRed});
                btnFFMax.setBackground(selectedBg);
                btnFFMax.setTextColor(0xFFFFFFFF);
                btnFF.setBackgroundColor(Color.TRANSPARENT);
                btnFF.setTextColor(0xFF666666);
            }
        };

        btnFF.setOnClickListener(v -> { selectedTargetPackage = "com.dts.freefireth"; refreshToggles.run(); });
        btnFFMax.setOnClickListener(v -> { selectedTargetPackage = "com.dts.freefiremax"; refreshToggles.run(); });
        refreshToggles.run();

        toggleBox.addView(btnFF);
        toggleBox.addView(btnFFMax);
        cardContainer.addView(toggleBox);

        // License Key Input
        FrameLayout inputContainer = new FrameLayout(context);
        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, utils.FixDP(46));
        containerParams.setMargins(0, utils.FixDP(14), 0, 0);
        inputContainer.setLayoutParams(containerParams);
        GradientDrawable inputBg = new GradientDrawable();
        inputBg.setCornerRadius(utils.FixDP(23));
        inputBg.setColor(InputBgColor);
        inputBg.setStroke(1, 0x30FFFFFF);
        inputContainer.setBackground(inputBg);

        input_username = new EditText(context);
        input_username.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        input_username.setHint("LICENSE KEY");
        input_username.setHintTextColor(0x40FFFFFF);
        input_username.setTextColor(0xFFFFFFFF);
        input_username.setBackgroundColor(Color.TRANSPARENT);
        input_username.setPadding(utils.FixDP(46), 0, utils.FixDP(46), 0);
        input_username.setTextSize(11);
        input_username.setGravity(Gravity.CENTER);
        input_username.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
        if (!savedKey.isEmpty()) input_username.setText(savedKey);

        final ImageView toggleIcon = new ImageView(context);
        FrameLayout.LayoutParams iconParams = new FrameLayout.LayoutParams(utils.FixDP(36), utils.FixDP(36));
        iconParams.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
        iconParams.rightMargin = utils.FixDP(6);
        toggleIcon.setLayoutParams(iconParams);
        toggleIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        toggleIcon.setImageDrawable(Utils.createEyeDrawable(context, false, 0x66FFFFFF));

        toggleIcon.setOnClickListener(v -> {
            boolean isMasked = input_username.getTransformationMethod() instanceof android.text.method.PasswordTransformationMethod;
            if (isMasked) {
                input_username.setTransformationMethod(android.text.method.HideReturnsTransformationMethod.getInstance());
                toggleIcon.setImageDrawable(Utils.createEyeDrawable(context, true, PrimaryColor));
            } else {
                input_username.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
                toggleIcon.setImageDrawable(Utils.createEyeDrawable(context, false, 0x66FFFFFF));
            }
            input_username.setSelection(input_username.getText().length());
        });

        inputContainer.addView(input_username);
        inputContainer.addView(toggleIcon);
        cardContainer.addView(inputContainer);

        // Login Button
        Button loginBtn = new Button(context);
        LinearLayout.LayoutParams loginParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, utils.FixDP(46));
        loginParams.setMargins(0, utils.FixDP(16), 0, 0);
        loginBtn.setLayoutParams(loginParams);
        loginBtn.setText("LOGIN");
        loginBtn.setTextColor(Color.WHITE);
        loginBtn.setTextSize(11);
        loginBtn.setTypeface(null, android.graphics.Typeface.BOLD);
        GradientDrawable loginBg = new GradientDrawable();
        loginBg.setCornerRadius(utils.FixDP(23));
        loginBg.setColors(new int[]{LightRed, PrimaryColor, DarkRed});
        loginBtn.setBackground(loginBg);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            loginBtn.setElevation(utils.FixDP(4));
        }

        loginBtn.setOnClickListener(v -> handleLogin(loginBtn, preferences));
        cardContainer.addView(loginBtn);

        // Action Row
        LinearLayout actionRow = new LinearLayout(context);
        actionRow.setOrientation(LinearLayout.HORIZONTAL);
        actionRow.setGravity(Gravity.CENTER);
        actionRow.setPadding(0, utils.FixDP(16), 0, 0);

        TextView btnGetKey = new TextView(context);
        btnGetKey.setText("Get License");
        btnGetKey.setTextColor(0xFF888888);
        btnGetKey.setTextSize(10);
        btnGetKey.setPadding(utils.FixDP(12), utils.FixDP(6), utils.FixDP(12), utils.FixDP(6));
        btnGetKey.setOnClickListener(v -> context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://TODO_REBRAND_SHOP"))));

        TextView divider = new TextView(context);
        divider.setText("|");
        divider.setTextColor(0x22FFFFFF);
        divider.setTextSize(11);
        divider.setPadding(utils.FixDP(6), 0, utils.FixDP(6), 0);

        TextView btnTelegram = new TextView(context);
        btnTelegram.setText("Discord");
        btnTelegram.setTextColor(0xFF888888);
        btnTelegram.setTextSize(10);
        btnTelegram.setPadding(utils.FixDP(12), utils.FixDP(6), utils.FixDP(12), utils.FixDP(6));
        btnTelegram.setOnClickListener(v -> context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://TODO_REBRAND_DISCORD"))));

        actionRow.addView(btnGetKey);
        actionRow.addView(divider);
        actionRow.addView(btnTelegram);
        cardContainer.addView(actionRow);

        container.addView(cardContainer);
        ((Activity) context).setContentView(container);

        // Start animations
        cardContainer.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(600).setStartDelay(300).start();
    }

    private void handleLogin(final Button loginBtn, final SharedPreferences preferences) {
        String key = input_username.getText().toString().trim();
        if (key.isEmpty()) {
            Toast.makeText(context, "Enter your license key", Toast.LENGTH_SHORT).show();
            return;
        }

        loginBtn.setEnabled(false);
        loginBtn.setText("VERIFYING...");

        new Thread(() -> {
            try {
                LicenseAuth licenseAuth = new LicenseAuth(APPNAME, OWNERID, SECRET, VERSION, LICENSEAUTH_URL, context);
                licenseAuth.init();
                org.json.JSONObject response = licenseAuth.license(key);
                org.json.JSONObject info = response.optJSONObject("info");

                String username = key;
                String subscription = "REGIX COVER";
                String expiryDate = "";
                String hwid = "";
                if (info != null) {
                    username = info.optString("username", key);
                    hwid = info.optString("hwid", "");
                    org.json.JSONArray subs = info.optJSONArray("subscriptions");
                    if (subs != null && subs.length() > 0) {
                        org.json.JSONObject sub = subs.optJSONObject(0);
                        if (sub != null) {
                            subscription = sub.optString("subscription", subscription);
                            long expirySec = sub.optLong("expiry", 0);
                            if (expirySec == 0) {
                                try {
                                    expirySec = Long.parseLong(sub.optString("expiry", "0"));
                                } catch (NumberFormatException ignored) {}
                            }
                            if (expirySec > 0) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                                expiryDate = sdf.format(new Date(expirySec * 1000));
                            }
                        }
                    }
                }

                final AuthHelper.LoginResult result = new AuthHelper.LoginResult(
                        true, "License valid", subscription,
                        expiryDate, "Active", "", hwid);
                final String displayUser = username;

                mainHandler.post(() -> {
                    preferences.edit().putString(LICENSE_KEY, key).apply();
                    sLoginUsername = displayUser;
                    sLoginPassword = "Authenticated";
                    loginBtn.setEnabled(true);
                    showSellerInfoPopup(result, key);
                });

            } catch (Exception e) {
                mainHandler.post(() -> {
                    loginBtn.setEnabled(true);
                    loginBtn.setText("LOGIN");
                    Toast.makeText(context, "Invalid license key", Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }

    private void showSellerInfoPopup(final AuthHelper.LoginResult result, final String userKey) {
        ((Activity) context).runOnUiThread(() -> {
            ViewGroup rootView = (ViewGroup) ((Activity) context).findViewById(android.R.id.content);

            FrameLayout overlay = new FrameLayout(context);
        overlay.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        overlay.setBackgroundColor(0xCC000000);
        overlay.setClickable(true);

        // Red rain animation background for modal
        RainView rainView = new RainView(context);
        rainView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        overlay.addView(rainView);

            LinearLayout modal = new LinearLayout(context);
        FrameLayout.LayoutParams modalParams = new FrameLayout.LayoutParams(utils.FixDP(300), ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        modal.setLayoutParams(modalParams);
        modal.setOrientation(LinearLayout.VERTICAL);
        modal.setPadding(utils.FixDP(28), utils.FixDP(28), utils.FixDP(28), utils.FixDP(24));
        modal.setGravity(Gravity.CENTER);
        modal.setAlpha(0f);
        modal.setScaleX(0.85f);
        modal.setScaleY(0.85f);

        GradientDrawable modalBg = new GradientDrawable();
        modalBg.setCornerRadius(utils.FixDP(24));
        modalBg.setColor(CardBgColor);
        modalBg.setStroke(2, 0x40FF1744);
        modal.setBackground(modalBg);

        // Premium header
        LinearLayout headerRow = new LinearLayout(context);
        headerRow.setOrientation(LinearLayout.HORIZONTAL);
        headerRow.setGravity(Gravity.CENTER);
        headerRow.setPadding(0, 0, 0, utils.FixDP(20));

        // Staggered letter animation for "REGIX"
        String brandShort = "REGIX";
        for (int i = 0; i < brandShort.length(); i++) {
            final TextView letter = new TextView(context);
            letter.setText(String.valueOf(brandShort.charAt(i)));
            letter.setTextSize(20);
            letter.setTypeface(null, android.graphics.Typeface.BOLD);
            letter.setAlpha(0f);
            letter.setTranslationY(25);
            letter.setTextColor(PrimaryColor);
            letter.setShadowLayer(6, 0, 0, 0x50FF1744);
            
            final int index = i;
            new Handler().postDelayed(() -> {
                letter.animate()
                    .alpha(1f)
                    .translationY(0)
                    .setDuration(350)
                    .setStartDelay(300 + index * 70)
                    .start();
            }, 150);
            
            headerRow.addView(letter);
        }

        // Add space
        TextView space = new TextView(context);
        space.setText("  ");
        space.setTextSize(20);
        headerRow.addView(space);

        // Staggered letter animation for "AIMKILL"
        String brandLong = "AIMKILL";
        for (int i = 0; i < brandLong.length(); i++) {
            final TextView letter = new TextView(context);
            letter.setText(String.valueOf(brandLong.charAt(i)));
            letter.setTextSize(20);
            letter.setTypeface(null, android.graphics.Typeface.BOLD);
            letter.setTextColor(PrimaryColor);
            letter.setAlpha(0f);
            letter.setTranslationY(25);
            letter.setShadowLayer(4, 0, 0, 0x50FF1744);
            
            final int index = i;
            new Handler().postDelayed(() -> {
                letter.animate()
                    .alpha(1f)
                    .translationY(0)
                    .setDuration(350)
                    .setStartDelay(500 + index * 35)
                    .start();
            }, 150);
            
            headerRow.addView(letter);
        }

        modal.addView(headerRow);

        // Info rows
        LinearLayout infoCont = new LinearLayout(context);
        infoCont.setOrientation(LinearLayout.VERTICAL);
        infoCont.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        infoCont.setPadding(0, 0, 0, utils.FixDP(20));

        infoCont.addView(createInfoRow("PACKAGE", result.packageName.toUpperCase()));
        infoCont.addView(createInfoRow("STATUS", result.status.toUpperCase()));
        String displayHwid = (result.hwid != null && result.hwid.length() > 8) ? result.hwid : AuthHelper.getDeviceId(context);
        infoCont.addView(createInfoRow("DEVICE", displayHwid.substring(0, Math.min(displayHwid.length(), 16)) + "..."));
        final LinearLayout validRow = createInfoRow("EXPIRES", "");
        final TextView validVal = (TextView) validRow.getChildAt(1);
        infoCont.addView(validRow);

        final Handler timerHandler = new Handler(Looper.getMainLooper());
        final Runnable timerRunnable = new Runnable() {
            @Override public void run() {
                validVal.setText(getRemainingValidity(result.expiryDate).toUpperCase());
                timerHandler.postDelayed(this, 1000);
            }
        };
        timerHandler.post(timerRunnable);

        LinearLayout keyRow = createInfoRow("LICENSE", userKey);
        final TextView keyVal = (TextView) keyRow.getChildAt(1);
        keyVal.setTextColor(PrimaryColor);
        keyVal.setTypeface(null, android.graphics.Typeface.BOLD);
        infoCont.addView(keyRow);

        modal.addView(infoCont);

        Button btnContinue = new Button(context);
        btnContinue.setText("CONTINUE");
        btnContinue.setTextColor(0xFFFFFFFF);
        btnContinue.setAllCaps(true);
        btnContinue.setTextSize(11);
        btnContinue.setTypeface(null, android.graphics.Typeface.BOLD);
        GradientDrawable btnBg = new GradientDrawable();
        btnBg.setCornerRadius(utils.FixDP(20));
        btnBg.setColors(new int[]{LightRed, PrimaryColor, DarkRed});
        btnContinue.setBackground(btnBg);
        btnContinue.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, utils.FixDP(44)));
        btnContinue.setOnClickListener(v -> {
            timerHandler.removeCallbacks(timerRunnable);
            overlay.animate().alpha(0).setDuration(300).withEndAction(() -> {
                rootView.removeView(overlay);
                new Menu(context, selectedInjectType, selectedTargetPackage);
                launchGame();
            }).start();
        });
            modal.addView(btnContinue);

            overlay.addView(modal);
            rootView.addView(overlay);
            overlay.setAlpha(0);
            overlay.animate().alpha(1).setDuration(300).start();
            modal.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(400).setStartDelay(150).start();
        });
    }

    private String getRemainingValidity(String expiryStr) {
        if (expiryStr == null || expiryStr.trim().isEmpty() || expiryStr.equalsIgnoreCase("Lifetime")) return "LIFETIME";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            Date date = sdf.parse(expiryStr);
            if (date == null) return expiryStr;
            long diff = date.getTime() - System.currentTimeMillis();
            if (diff <= 0) return "EXPIRED";
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            return String.format(Locale.US, "%dd %dh %dm %ds", days, hours % 24, minutes % 60, seconds % 60);
        } catch (Exception e) { return expiryStr; }
    }

    private LinearLayout createInfoRow(String label, String value) {
        LinearLayout row = new LinearLayout(context);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setPadding(0, utils.FixDP(4), 0, utils.FixDP(4));
        TextView lbl = new TextView(context);
        lbl.setText(label + ": ");
        lbl.setTextColor(0xFF666666);
        lbl.setTextSize(9);
        lbl.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        TextView val = new TextView(context);
        val.setText(value);
        val.setTextColor(0xFFF5F5F5);
        val.setTextSize(9);
        val.setGravity(Gravity.RIGHT);
        val.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2f));
        row.addView(lbl);
        row.addView(val);
        return row;
    }

    private void launchGame() {
        Intent i = context.getPackageManager().getLaunchIntentForPackage(selectedTargetPackage);
        if (i != null) context.startActivity(i);
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
