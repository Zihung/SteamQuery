package com.bignerdranch.android.cua.api

class Applist {
    private lateinit var apps: Array<Apps>
    fun getApps(): Array<Apps> {
        return apps
    }

    fun setApps(apps: Array<Apps>) {
        this.apps = apps
    }

    override fun toString(): String {
        return "ClassPojo [apps = $apps]"
    }
}
