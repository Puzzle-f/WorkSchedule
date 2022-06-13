package com.example.workschedule.espresso

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workschedule.MainActivity
import com.example.workschedule.R
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun recycler_IsNotWorking() {
        scenario.onActivity {
            val recycler = it.findViewById<RecyclerView>(R.id.mainFragmentRecyclerView)
            TestCase.assertNotNull(recycler)
        }
    }

    @After
    fun close() {
        scenario.close()
    }
}
