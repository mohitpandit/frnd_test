package com.konnectshift.frnd.di;


import android.app.Application;

import com.konnectshift.frnd.MyApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AppModule.class, AndroidSupportInjectionModule.class, ActivityBuilder.class})
public interface AppComponent {

    void inject(MyApplication application);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();

    }
}
