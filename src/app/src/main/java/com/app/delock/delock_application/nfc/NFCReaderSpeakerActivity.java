package com.app.delock.delock_application.nfc;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class NFCReaderSpeakerActivity extends NFCSpeakerActivity {
    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "NfcRead";
    protected boolean alwaysRead = false;
    private NfcAdapter mNfcAdapter;
    String readType;

    protected class NdefReaderTask extends AsyncTask<Tag, Void, String> {
        protected NdefReaderTask() {
        }

        protected String doInBackground(Tag... params) {
            String str = null;
            Ndef ndef = Ndef.get(params[0]);
            if (ndef != null) {
                for (NdefRecord ndefRecord : ndef.getCachedNdefMessage().getRecords()) {
                    if (ndefRecord.getTnf() == (short) 1 && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                        try {
                            NFCReaderSpeakerActivity.this.readType = "text/plain";
                            str = readText(ndefRecord);
                            break;
                        } catch (UnsupportedEncodingException e) {
                            Log.e("NfcRead", "Unsupported Encoding", e);
                        }
                    }
                }
            }
            return str;
        }

        private String readText(NdefRecord record) throws UnsupportedEncodingException {
            String textEncoding;
            byte[] payload = record.getPayload();
            if ((payload[0] & 128) == 0) {
                textEncoding = "UTF-8";
            } else {
                textEncoding = "UTF-16";
            }
            int languageCodeLength = payload[0] & 51;
            return new String(payload, languageCodeLength + 1, (payload.length - languageCodeLength) - 1, textEncoding);
        }

        protected void onPostExecute(String result) {
            if (result != null) {
                NFCReaderSpeakerActivity.this.readResultAction(result);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        handleIntent(getIntent());
    }

    protected void onResume() {
        super.onResume();
        if (this.alwaysRead) {
            setupForegroundDispatch(this, this.mNfcAdapter);
        }
    }

    protected void onPause() {
        stopForegroundDispatch(this, this.mNfcAdapter);
        super.onPause();
    }

    protected void activateReading(boolean value) {
        if (this.alwaysRead != value) {
            this.alwaysRead = value;
            if (this.alwaysRead) {
                setupForegroundDispatch(this, this.mNfcAdapter);
            } else {
                stopForegroundDispatch(this, this.mNfcAdapter);
            }
        }
    }

    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    protected void readResultAction(String result) {
        Log.d("NfcRead", "Read result: " + result);
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        Tag tag = null;
        if ("android.nfc.action.NDEF_DISCOVERED".equals(action)) {
            String type = intent.getType();
            if ("text/plain".equals(type)) {
                tag = (Tag) intent.getParcelableExtra("android.nfc.extra.TAG");
                new NdefReaderTask().execute(new Tag[]{tag});
                return;
            }
            Log.d("NfcRead", "Wrong mime type: " + type);
        } else if ("android.nfc.action.TECH_DISCOVERED".equals(action)) {
            String[] techList = ((Tag) intent.getParcelableExtra("android.nfc.extra.TAG")).getTechList();
            String searchedTech = Ndef.class.getName();
            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
                    new NdefReaderTask().execute(new Tag[]{tag});
                    return;
                }
            }
        }
    }

    public static void setupForegroundDispatch(Activity activity, NfcAdapter adapter) {
        Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
//        intent.setFlags(536870912);
        PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);
        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[0][];
        filters[0] = new IntentFilter();
        filters[0].addAction("android.nfc.action.NDEF_DISCOVERED");
        filters[0].addCategory("android.intent.category.DEFAULT");
        try {
            filters[0].addDataType("text/plain");
            adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
        } catch (MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }
    }

    public static void stopForegroundDispatch(Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }
}
