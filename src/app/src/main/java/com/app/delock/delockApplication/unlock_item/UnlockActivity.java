package com.app.delock.delockApplication.unlock_item;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.widget.Switch;

import com.app.delock.delockApplication.R;
import com.app.delock.delockApplication.nfc.NFCReaderSpeakerActivity;

import java.util.Locale;

public class UnlockActivity extends NFCReaderSpeakerActivity {
    String password;
    String passwordKey = "com.app.delock.delockApplication.password";
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_new_item);

        this.prefs = getSharedPreferences("com.app.delock.delockApplication", 0);
        this.prefs.edit().putString(this.passwordKey, "default_password").apply();
        this.password = this.prefs.getString(this.passwordKey, "default_password");
        this.alwaysRead = true;
    }


    public void onResume() {
        super.onResume();
        this.password = this.prefs.getString(this.passwordKey, "default_password");
    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_unlock, menu);
//        return true;
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() != R.id.change_password) {
//            return super.onOptionsItemSelected(item);
//        }
//        startActivity(new Intent(this, PasswordChangeActivity.class));
//        return true;
//    }

    @TargetApi(16)
    public NdefMessage createNdefMessage(NfcEvent event) {

        if (((Switch) findViewById(R.id.lock_unlock_switch)).isChecked()) {
            return new NdefMessage(createTextRecord(this.password, Locale.getDefault(), true), new NdefRecord[0]);
        }
        return new NdefMessage(createTextRecord("0", Locale.getDefault(), true), new NdefRecord[0]);
    }

    protected void readResultAction(String result) {
        if (result != null && !result.equals("")) {
//            if (result.equals("unlocked")) {
//                ((ImageView) findViewById(R.id.lockImage)).setImageResource(R.drawable.unlocked);
//            } else if (result.equals("locked")) {
//                ((ImageView) findViewById(R.id.lockImage)).setImageResource(R.drawable.locked);
//            }
        }
    }
}
