package com.avos.avoscloud.PushDemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.avos.avoscloud.*;
import com.avos.avospush.*;

public class PushDemo extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        PushService.setDefaultPushCallback(this, PushDemo.class);
        PushService.subscribe(this, "public", PushDemo.class);
        PushService.subscribe(this, "private", Callback1.class);
        PushService.subscribe(this, "protected", Callback2.class);

        final Context context = this;

        final TextView t = (TextView)this.findViewById(R.id.mylabel);
        t.setText("id: " + AVInstallation.getCurrentInstallation().getInstallationId());
        AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                PushService.unsubscribe(context, "protected");
                AVInstallation.getCurrentInstallation().saveInBackground();
            }
        });
    }
}
