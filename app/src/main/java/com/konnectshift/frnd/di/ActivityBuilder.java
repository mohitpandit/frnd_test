package com.konnectshift.frnd.di;


import com.konnectshift.frnd.ui.main.RecordingActivity;
import com.konnectshift.frnd.ui.main.RecordingActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {


    @ContributesAndroidInjector(modules = RecordingActivityModule.class)
    abstract RecordingActivity provideRecordingActivity();
}
