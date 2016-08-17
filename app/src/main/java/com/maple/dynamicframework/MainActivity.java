package com.maple.dynamicframework;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.maple.frameworkcore.lib.DynamicloadPluginManager;
import com.maple.frameworkcore.lib.inter.IFrameworkCenterCallBack;
import com.maple.frameworkcore.lib.inter.IPluginParamsProxy;
import com.maple.frameworkcore.lib.inter.IPluginSurfaceProxy;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mRelativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.container);
        final Context  context = getApplicationContext();
        DynamicloadPluginManager.getInstance(context).initPreload(new IPluginParamsProxy() {
            @Override
            public String getADID(String packageName) {
                return null;
            }

            @Override
            public String getBuychannel(String packageName) {
                return null;
            }

            @Override
            public String getInstallDays(String packageName) {
                return null;
            }
        });

        findViewById(R.id.default_activity_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IPluginSurfaceProxy pluginSurfaceProxy = DynamicloadPluginManager.getInstance(context).getPlugins("com.maple.plugins.plugindemo", new IFrameworkCenterCallBack() {
                    @Override
                    public void onBack(View view) {

                    }

                    @Override
                    public void informShowFullScreenView(View view) {

                    }

                    @Override
                    public void informExit(Object obj) {

                    }
                });
                mRelativeLayout.addView(pluginSurfaceProxy.getView());
            }
        });
    }
}
