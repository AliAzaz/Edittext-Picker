# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

-dontwarn javax.annotation.**

-keepclasseswithmembernames class * {
    native <methods>;
}

-keep class java.io.** { *; }
-keep class android.support.** { *; }
-keep interface android.support.** { *; }

-keep class **$$ViewBinder { *; }
-renamesourcefileattribute SourceFile

-keep public class * extends java.lang.Exception
-keepattributes SourceFile,LineNumberTable

-keepnames class * implements android.os.Parcelable {
        public static final ** CREATOR;
}

# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Exceptions, Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

-keep,allowobfuscation interface <1>

-keep class androidx.lifecycle.** { *; }
-keep class androidx.arch.core.** { *; }

-keepattributes Signature
-keepattributes *Annotation*

#Not show logs
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int d(...);
    public static int w(...);
    public static int v(...);
    public static int i(...);
    public static int e(...);
}

#custom modules
-keep class com.edittextpicker.aliazaz.model.** { *; }
-keep class com.edittextpicker.aliazaz.EditTextPicker { *; }
-keep class com.edittextpicker.aliazaz.repository.EditTextPickerBuilder { *; }