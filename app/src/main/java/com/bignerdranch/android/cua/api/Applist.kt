package com.bignerdranch.android.cua.api

class Applist {
    private lateinit var apps: Array<Apps>
    private var appsMap: HashMap<String, String> = HashMap<String, String>()

    fun setAppsMap(){
        for(each in this.apps){
//            appsMap[each.appid] = each.name
            appsMap.put(each.appid, each.name)
        }
    }

    fun getAppsMap(): HashMap<String, String>{
        return appsMap
    }

    fun getApps(): Array<Apps> {
        return apps
    }

    fun setApps(apps: Array<Apps>) {
        this.apps = apps
        setAppsMap()
    }

    override fun toString(): String {
        return "ClassPojo [apps = $apps]"
    }
}
