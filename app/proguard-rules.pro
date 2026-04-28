# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\admin\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.

# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# Room
-keep class * extends androidx.room.RoomDatabase
-keep class * extends androidx.room.TypeConverter
-keepclassmembers class * extends androidx.room.RoomDatabase {
    public *** getInstance(...);
}

# Koin
-keep class org.koin.** { *; }
-keep class kotlin.** { *; }

# Compose
-keep class androidx.compose.** { *; }

# Security
-keep class androidx.security.** { *; }

# Biometric
-keep class androidx.biometric.** { *; }

# Keep all data classes (entities)
-keep class com.example.proyecto.data.local.entity.** { *; }
