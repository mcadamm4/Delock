package com.app.delock.delockApplication.nfc;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import java.nio.charset.Charset;
import java.util.Locale;

public class NFCSpeakerActivity extends AppCompatActivity implements CreateNdefMessageCallback, OnNdefPushCompleteCallback {
    private static final int MESSAGE_SENT = 1;
    protected boolean displaySendToast = false;
    private final Handler mHandler = new MessageHandler();
    private NfcAdapter mNfcAdapter;

    @SuppressLint("HandlerLeak")
    class MessageHandler extends Handler {
        MessageHandler () {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (NFCSpeakerActivity.this.displaySendToast) {
                        Toast.makeText(NFCSpeakerActivity.this.getApplicationContext(), "Message sent!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (this.mNfcAdapter != null) {
            this.mNfcAdapter.setNdefPushMessageCallback(this, this, new Activity[0]);
            this.mNfcAdapter.setOnNdefPushCompleteCallback(this, this, new Activity[0]);
        }
    }

    public NdefRecord createTextRecord(String payload, Locale locale, boolean encodeInUtf8) {
        byte[] langBytes = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));
        byte[] textBytes = payload.getBytes(encodeInUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16"));
        byte[] data = new byte[((langBytes.length + 1) + textBytes.length)];
        data[0] = (byte) ((char) (langBytes.length + (encodeInUtf8 ? 0 : 128)));
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, langBytes.length + 1, textBytes.length);
        return new NdefRecord((short) 1, NdefRecord.RTD_TEXT, new byte[0], data);
    }

    @TargetApi(16)
    public NdefMessage createNdefMessage(NfcEvent event) {
        return new NdefMessage(createTextRecord("Default", Locale.getDefault(), true), new NdefRecord[0]);
    }

    public void onNdefPushComplete(NfcEvent event) {
        this.mHandler.obtainMessage(1).sendToTarget();
        if (this.mNfcAdapter != null) {
            this.mNfcAdapter.setNdefPushMessageCallback(this, this, new Activity[0]);
            this.mNfcAdapter.setOnNdefPushCompleteCallback(this, this, new Activity[0]);
        }
    }
}
