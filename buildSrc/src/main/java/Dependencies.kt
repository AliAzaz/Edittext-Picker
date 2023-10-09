object Layouts {
    const val constraint =
        "androidx.constraintlayout:constraintlayout:${DependenciesVersions.constraintVersion}"
}

object AndroidApp {
    const val appCompat =
        "androidx.appcompat:appcompat:${DependenciesVersions.supportLibraryVersion}"
    const val coreKTX = "androidx.core:core-ktx:${DependenciesVersions.ktxVersion}"
    const val stringLANG =
        "org.apache.commons:commons-lang3:${DependenciesVersions.stringLangVersion}"
}

object Testing {
    const val junit = "junit:junit:${DependenciesVersions.junitVersion}"
    const val junitImpl = "androidx.test.ext:junit:${DependenciesVersions.junitImplVersion}"
    const val expresso =
        "androidx.test.espresso:espresso-core:${DependenciesVersions.espressoVersion}"
}