/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.sleeptracker

import android.app.Application
import androidx.lifecycle.*
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.formatNights
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * ViewModel for SleepTrackerFragment.
 */
// view model needs access to the data in the database (which provided by the dao interface [SleepDatabaseDao])
// strings and styles form crom application
class SleepTrackerViewModel(
        val database: SleepDatabaseDao,
        application: Application,
) : AndroidViewModel(application) { // takes application context for use




    //TODO (01) Define a variable, to hold ------- make mutableLiveData.
    private var tonight = MutableLiveData<SleepNight?>()

    //TODO (02) Define a variable, nights. Then getAllNights() from the database.
    private val nights = database.getAllNights()

    private val _navigateToSleepQuality = MutableLiveData<SleepNight?>()
    val navigateToSleepQuality : MutableLiveData<SleepNight?>
        get() = _navigateToSleepQuality



    val nightsString = Transformations.map(nights,{nights ->
        formatNights(nights,application.resources)
    })

    //while tonight is null start should be visibile
    val startButtonVisible = Transformations.map(tonight,{
        null == it
    })

    //while tonight is not null stop should be visible
    val stopButtonVisible = Transformations.map(tonight,{
        null != it
    })

    val clearButtonVisible = Transformations.map(nights,{
        it?.isNotEmpty()
    })
    fun doneNavigating(){
        _navigateToSleepQuality.value = null
    }

    private var _showSnackBarEvent = MutableLiveData<Boolean>()
    val showSnackBarEvent : LiveData<Boolean>
        get() = _showSnackBarEvent

    fun doneShowingSnackBar(){
        _showSnackBarEvent.value = false
    }

    //TODO (03) In an init block, initializeTonight(), and implement it to launch a coroutine
    // we want tonight to be available at start so we use an init block
    init {
        initializeTonight() // we implement this with a coroutine so it doesnt block main thread
    }

    private fun initializeTonight() {
        viewModelScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun getTonightFromDatabase(): SleepNight? {
        var night = database.getTonight()

        if (night?.endTimeMilli != night?.startTimeMilli) {
            night = null
        }
        return night
    }

    fun onStartTracking() {
        viewModelScope.launch {
            val newNight = SleepNight()
            insert(newNight)
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun insert(newNight: SleepNight) {
        database.insert(newNight)
    }
//return@label specifies which nested function the statement returns from
    fun onStopTracking() {
        viewModelScope.launch {
            val oldNight = tonight.value ?: return@launch
            oldNight.endTimeMilli = System.currentTimeMillis()
            update(oldNight)

            _navigateToSleepQuality.value = oldNight
        }
    }

    private suspend fun update(oldNight: SleepNight) {
        database.update(oldNight)
    }

        fun onClear(){
                viewModelScope.launch {
                        clear()
                        tonight.value = null
                    _showSnackBarEvent.value = true
                }
        }

        suspend fun clear(){
                database.clear()
        }
}





