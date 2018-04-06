package com.app.delock.delockApplication.dashboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.app.delock.delockApplication.IPFSDaemon;
import com.app.delock.delockApplication.IPFSDaemonService;
import com.app.delock.delockApplication.R;
import com.app.delock.delockApplication.State;
import com.app.delock.delockApplication.browse.BrowseActivity;
import com.app.delock.delockApplication.my_profile.MyProfileActivity;

import org.jetbrains.annotations.NotNull;
import org.ligi.kaxt.ContextExtensionsKt;

import java.util.HashMap;

import io.ipfs.kotlin.IPFS;
import io.ipfs.kotlin.model.VersionInfo;
import kotlin.jvm.internal.Intrinsics;

import static rx.schedulers.Schedulers.start;

public class DashboardActivity extends AppCompatActivity {
    private final IPFSDaemon ipfsDaemon = new IPFSDaemon((Context)this);
    public IPFS ipfs;
    private HashMap _$_findViewCache;

    @NotNull
    public final IPFS getIpfs() {
        IPFS var10000 = this.ipfs;
        if(this.ipfs == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ipfs");
        }

        return var10000;
    }

    public final void setIpfs(@NotNull IPFS var1) {
        Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
        this.ipfs = var1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button runDaemon = findViewById(R.id.runDaemon);

        runDaemon.setOnClickListener((View it) -> {
            DashboardActivity.this.startService(new Intent(DashboardActivity.this, IPFSDaemonService.class));
            Button daemonButton = (Button)DashboardActivity.this._$_findCachedViewById(R.id.runDaemon);
            Intrinsics.checkExpressionValueIsNotNull(daemonButton, "daemonButton");

            daemonButton.setVisibility(View.INVISIBLE);
            State.INSTANCE.setDaemonRunning(true);

            final ProgressDialog progressDialog = new ProgressDialog(DashboardActivity.this);
            progressDialog.setMessage("starting daemon");
            progressDialog.show();
            (new Thread(() -> {
                VersionInfo version = null;

                while(version == null) {
                    try {
                        version = DashboardActivity.this.getIpfs().getInfo().version();
                    } catch (Exception ignored) {
                        ;
                    }
                }
                DashboardActivity.this.runOnUiThread(() -> {
                    progressDialog.dismiss();
                    ContextExtensionsKt.startActivityFromClass(DashboardActivity.this, BrowseActivity.class);
                });
            })).start();
        DashboardActivity.this.refresh();
        });
//        ((Button)this._$_findCachedViewById(id.daemonStopButton)).setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
//            public final void onClick(View it) {
//                MainActivity.this.stopService(new Intent((Context)MainActivity.this, IPFSDaemonService.class));
//                State.INSTANCE.setDaemonRunning(false);
//                MainActivity.this.refresh();
//            }
//        }));
//        ((Button)this._$_findCachedViewById(id.exampleButton)).setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
//            public final void onClick(View it) {
//                Intent intent = new Intent("android.intent.action.VIEW");
//                intent.setData(Uri.parse("http://ligi.de/ipfs/example_links2.html"));
//                MainActivity.this.startActivity(intent);
//            }
//        }));
//        TraceDroidEmailSender.sendStackTraces("ligi@ligi.de", (Context)this);
//        this.refresh();


        BottomNavigationView navMenu = findViewById(R.id.navigation);
        navMenu.setSelectedItemId(R.id.navigation_dashboard);
        navMenu.setOnNavigationItemSelectedListener(
                menuItem -> {
                    Intent intent;
                    // set item as selected to persist highlight
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_home:
                            intent = new Intent(DashboardActivity.this, BrowseActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.navigation_dashboard:
                            break;
                        case R.id.navigation_myProfile:
                            intent = new Intent(DashboardActivity.this, MyProfileActivity.class);
                            startActivity(intent);
                            break;
                    }
                    return true;
                });
    }
    private final void refresh() {
        Button var10000 = (Button)this._$_findCachedViewById(R.id.runDaemon);
        Intrinsics.checkExpressionValueIsNotNull(var10000, "daemonButton");
        var10000.setVisibility(this.ipfsDaemon.isReady() && !State.INSTANCE.isDaemonRunning()?View.VISIBLE:View.INVISIBLE);
        var10000 = (Button)this._$_findCachedViewById(R.id.stopDaemon);
        Intrinsics.checkExpressionValueIsNotNull(var10000, "daemonStopButton");
        var10000.setVisibility(this.ipfsDaemon.isReady() && State.INSTANCE.isDaemonRunning()?View.INVISIBLE:View.VISIBLE);
//        var10000 = (Button)this._$_findCachedViewById(id.downloadIPFSButton);
//        Intrinsics.checkExpressionValueIsNotNull(var10000, "downloadIPFSButton");
//        var10000.setVisibility(this.ipfsDaemon.isReady()?8:0);
    }

    public View _$_findCachedViewById(int var1) {
        if(this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }

        View var2 = (View)this._$_findViewCache.get(Integer.valueOf(var1));
        if(var2 == null) {
            var2 = this.findViewById(var1);
            this._$_findViewCache.put(Integer.valueOf(var1), var2);
        }

        return var2;
    }

    public void _$_clearFindViewByIdCache() {
        if(this._$_findViewCache != null) {
            this._$_findViewCache.clear();
        }

    }
}
