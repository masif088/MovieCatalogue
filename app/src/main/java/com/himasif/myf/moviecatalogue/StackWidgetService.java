package com.himasif.myf.moviecatalogue;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackViewRemoteFactory(this.getApplicationContext(), intent);
    }
}
