package com.example.criminalintent

import android.app.Application


class CriminalIntentApllication :Application(){


    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
    }

}