package com.example.socketapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


/*MainApp is the entry point for Hilt to inject dependencies
used as the custom application class in the manifest */

@HiltAndroidApp
class MainApp : Application()

