mapOf(
    "ktor_version" to "1.3.2",
    "kotlin_version" to "1.3.72",
    "logback_version" to "1.2.1",
    "koin_version" to "2.1.5"
).forEach { (name, version) ->
    project.extra.set(name, version)
}