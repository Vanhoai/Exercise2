package com.dyland.exercise2

import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import com.dyland.exercise2.models.AppInfo

class Utils {
    companion object {
        fun getInstalledApps(packageManager: PackageManager): List<AppInfo> {
            val apps = mutableListOf<AppInfo>()
            val packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            packages.forEach { app ->
                val label = packageManager.getApplicationLabel(app).toString()
                val icon = packageManager.getApplicationIcon(app)
                apps.add(AppInfo(label, app.packageName, icon))
            }
            return apps
        }

        fun getRecentApps(context: Context): List<AppInfo> {
            val recentTasks = (context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).appTasks
            val recentApps = mutableListOf<AppInfo>()
            for (task in recentTasks) {
                val packageName = task.taskInfo.baseIntent.component?.packageName ?: continue
                try {
                    val label = context.packageManager.getApplicationLabel(
                        context.packageManager.getApplicationInfo(packageName, 0)
                    ).toString()
                    val icon = context.packageManager.getApplicationIcon(packageName)
                    recentApps.add(AppInfo(label, packageName, icon))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return recentApps
        }
    }
}