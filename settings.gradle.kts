rootProject.buildFileName = "build.gradle.kts"

include(":frontend:android:app")
include(":frontend:android:core")
include(":frontend:android:domain")
include(":frontend:android:data")
include(":frontend:android:data:task:sqldelight:common")
include(":frontend:android:data:preference")
include(":frontend:android:data:dataCore")
include(":frontend:android:data:task:room")
include(":frontend:android:data:task:firestore")
include(":frontend:android:data:task:sqldelight")
include(":frontend:android:settings")
include(":frontend:android:main:mainNavigation")
include(":frontend:android:main:tasks")
include(":frontend:android:main:taskdetail")
include(":frontend:android:base")
include(":frontend:android:main:about")
include(":frontend:android:main:addedit")
include(":frontend:android:main:search")
include(":frontend:android:main:setting")
include(":frontend:android:splash")
include(":frontend:android:main:statistics")
include(":frontend:android:main")
include(":frontend:android:work")
include(":frontend:android:seedDatabase")
include(":frontend:android:downloadModule")
include(":frontend:android:navigation")

enableFeaturePreview("GRADLE_METADATA")
