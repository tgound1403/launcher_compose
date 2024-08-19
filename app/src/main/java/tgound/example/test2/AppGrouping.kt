package tgound.example.test2

fun groupAppsIntoFolders(apps: List<AppInfo>): Map<String, List<AppInfo>> {
    return apps.groupBy { app ->
        when {
            app.name.isBlank() -> "#"
            app.name.first().isLetter() -> app.name.first().uppercase()
            else -> "#"
        }
    }.toSortedMap()
}
