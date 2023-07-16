package com.ontask.android

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Family : NavigationItem("dashboard_screen", R.drawable.home_icon, "Family")
    object Account : NavigationItem("parentProfile_screen", R.drawable.person_icon, "Account")
}
