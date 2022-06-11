package com.example.workschedule.automator

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 28)
class MainFragmentTest {
    private val uiDevice: UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val packageName = context.packageName
    private val TIMEOUT = 5000L

    @Before
    fun setup() = setupBehavior(uiDevice, context, packageName)

    @Test
    fun test_MainActivityIsStarted() = test_MainActivityIsStarted_Behavior(uiDevice, packageName)

    private fun setupBehavior(uiDevice: UiDevice, context: Context, packageName: String) {
        uiDevice.pressHome()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
    }

    private fun test_MainActivityIsStarted_Behavior(uiDevice: UiDevice, packageName: String) {
        val recyclerMain = uiDevice.findObject(By.res(packageName, "mainFragmentRecyclerView"))
        Assert.assertNotNull(recyclerMain)
    }
}
