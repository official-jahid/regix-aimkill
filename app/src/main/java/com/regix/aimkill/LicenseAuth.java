package com.regix.aimkill;

import android.content.Context;
import android.provider.Settings;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class LicenseAuth {
    private String appname;
    private String ownerid;
    private String secret;
    private String version;
    private String url;
    private Context context;
    private String sessionid;
    private String enckey;

    public LicenseAuth(String appname, String ownerid, String secret, String version, String url, Context context) {
        this.appname = appname;
        this.ownerid = ownerid;
        this.secret = secret;
        this.version = version;
        this.url = url;
        this.context = context;
        this.sessionid = null;
        this.enckey = null;
    }

    public void init() throws Exception {
        String sentKey = UUID.randomUUID().toString().substring(0, 16);
        this.enckey = sentKey + "-" + this.secret;

        String postData = "type=init"
                + "&ver=" + URLEncoder.encode(version, "UTF-8")
                + "&hash=" + URLEncoder.encode("", "UTF-8")
                + "&enckey=" + URLEncoder.encode(sentKey, "UTF-8")
                + "&name=" + URLEncoder.encode(appname, "UTF-8")
                + "&ownerid=" + URLEncoder.encode(ownerid, "UTF-8");

        RawResponse raw = doPost(postData);
        if (raw.body.equals("LicenseAuth_Invalid")) {
            throw new Exception("The application doesn't exist");
        }
        verifySignature(raw.body, raw.signature, true);

        JSONObject json = new JSONObject(raw.body);
        if ("invalidver".equals(json.optString("message", ""))) {
            String download = json.optString("download", "");
            if (download != null && !download.isEmpty()) {
                throw new Exception("New version available: " + download);
            }
            throw new Exception("Invalid version, contact owner");
        }
        if (!json.getBoolean("success")) {
            throw new Exception(json.optString("message", "Initialization failed"));
        }
        this.sessionid = json.optString("sessionid", null);
        if (this.sessionid == null || this.sessionid.isEmpty()) {
            throw new Exception("Session ID not provided in init response");
        }
    }

    public JSONObject license(String key) throws Exception {
        if (sessionid == null || sessionid.isEmpty()) {
            throw new Exception("Session ID not initialized. Call init first.");
        }
        String hwid = getHWID(context);
        String postData = "type=license"
                + "&key=" + URLEncoder.encode(key, "UTF-8")
                + "&hwid=" + URLEncoder.encode(hwid, "UTF-8")
                + "&sessionid=" + URLEncoder.encode(sessionid, "UTF-8")
                + "&name=" + URLEncoder.encode(appname, "UTF-8")
                + "&ownerid=" + URLEncoder.encode(ownerid, "UTF-8");

        RawResponse raw = doPost(postData);
        verifySignature(raw.body, raw.signature, false);

        JSONObject json = new JSONObject(raw.body);
        if (!json.getBoolean("success")) {
            throw new Exception(json.optString("message", "License activation failed"));
        }
        return json;
    }

    private String getHWID(Context context) {
        String base = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
        if (base == null || base.isEmpty()) {
            base = "UNKNOWN_DEVICE";
        }
        if (base.length() < 20) {
            base += "HWIDFILLEREXTRA123";
        }
        return base;
    }

    private void verifySignature(String body, String signature, boolean isInit) throws Exception {
        if (signature == null || signature.isEmpty()) {
            throw new Exception("Missing response signature");
        }
        String key = isInit ? this.secret : this.enckey;
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(keySpec);
        byte[] hash = mac.doFinal(body.getBytes(StandardCharsets.UTF_8));
        StringBuilder hex = new StringBuilder();
        for (byte b : hash) {
            hex.append(String.format("%02x", b));
        }
        String computed = hex.toString();
        if (!MessageDigest.isEqual(computed.getBytes(StandardCharsets.UTF_8),
                signature.getBytes(StandardCharsets.UTF_8))) {
            throw new Exception("Signature checksum failed. Request was tampered with or session ended.");
        }
    }

    private static class RawResponse {
        String body;
        String signature;
    }

    private RawResponse doPost(String postData) throws Exception {
        URL endpoint = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(15000);
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 10; SM-G973F) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Mobile Safari/537.36");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(false);

        if (!postData.isEmpty()) {
            byte[] bytes = postData.getBytes("UTF-8");
            connection.getOutputStream().write(bytes);
            connection.getOutputStream().close();
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == 301 || responseCode == 302 || responseCode == 307 || responseCode == 308) {
            String newUrl = connection.getHeaderField("Location");
            connection.disconnect();
            URL redirect = new URL(newUrl);
            HttpURLConnection conn2 = (HttpURLConnection) redirect.openConnection();
            conn2.setRequestMethod("POST");
            conn2.setConnectTimeout(15000);
            conn2.setReadTimeout(15000);
            conn2.setRequestProperty("Accept", "application/json");
            conn2.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn2.setDoOutput(true);
            byte[] bytes = postData.getBytes("UTF-8");
            conn2.getOutputStream().write(bytes);
            conn2.getOutputStream().close();
            connection = conn2;
            responseCode = connection.getResponseCode();
        }

        BufferedReader reader;
        if (responseCode >= 200 && responseCode <= 299) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            java.io.InputStream errorStream = connection.getErrorStream();
            if (errorStream != null) {
                reader = new BufferedReader(new InputStreamReader(errorStream));
            } else {
                throw new Exception("HTTP error code: " + responseCode);
            }
        }

        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        String signature = connection.getHeaderField("signature");
        connection.disconnect();

        RawResponse raw = new RawResponse();
        raw.body = response.toString();
        raw.signature = signature;
        if (responseCode != HttpURLConnection.HTTP_OK) {
            try {
                JSONObject json = new JSONObject(raw.body);
                if (json.has("message")) {
                    throw new Exception(json.getString("message"));
                }
            } catch (org.json.JSONException ignored) {
            }
            if (raw.body.isEmpty()) {
                throw new Exception("HTTP error code: " + responseCode);
            }
        }
        return raw;
    }

    public String getSessionId() {
        return sessionid;
    }
}
