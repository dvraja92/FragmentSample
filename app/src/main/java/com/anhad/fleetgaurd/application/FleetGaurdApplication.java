package com.anhad.fleetgaurd.application;

import android.app.Application;

/**
 * Created by Anhad on 07-01-2017.
 */
public class FleetGaurdApplication extends Application {
        private static FleetGaurdApplication application;

        @Override
        public void onCreate() {
            super.onCreate();
            application = this;
        }

        public static FleetGaurdApplication getInstance(){
            return application;
        }
}
