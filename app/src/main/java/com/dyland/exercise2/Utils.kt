package com.dyland.exercise2

import android.app.usage.UsageStatsManager
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
            val usageStatsManager =
                context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            val usageStats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY,
                System.currentTimeMillis() - 1000 * 60 * 60 * 24,
                System.currentTimeMillis()
            )

            val apps = mutableListOf<AppInfo>()
            usageStats.forEach { app ->
                try {
                    // Lấy icon của ứng dụng
                    val icon = context.packageManager.getApplicationIcon(app.packageName)
                    apps.add(AppInfo(app.packageName, app.packageName, icon))
                } catch (e: PackageManager.NameNotFoundException) {
                    // Ứng dụng có thể đã bị gỡ cài đặt, bỏ qua ứng dụng này
                    e.printStackTrace()
                }
            }
            return apps
        }
    }
}