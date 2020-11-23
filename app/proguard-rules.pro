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

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#-keep class com.sun.mail.** {*;}
#-dontwarn com.sun.mail.**
-optimizationpasses 5
-keepattributes *Annotation*
#反射
#-keep class * extends com.mobilewise.mobileware.policy.AbstractPolicyProduct{
#    *;
#}
#第三方jar包
-ignorewarnings
-keep class com.baidu.**{*;}
-keep class org.apache.commons.io.**{*;}
-keep class com.google.gson.**{*;}
#-keep com.jwenfeng.**{*;}
#-keep com.bumptech.**{*;}
#cypto
#-keep class com.suninfo.sdk.**{*;}
#-keep class net.sqlcipher.**{*;}
#-keep class com.suninfo.sandbox.**{*;}
#bean类
-keep class com.yangyong.didi2.bean.**{*;}
#-keep class com.mobilewise.mobileware.model.**{*;}
#-keep class com.mobilewise.sandbox.model.**{*;}
#-keep class com.mobilewise.emmstore.model.**{*;}
#-keep class com.mobilewise.emmstore.reqmodel.**{*;}
#-keep class com.mobilewise.emmstore.resmodel.**{*;}
#-keep class com.aixunyun.product.imserver.comm.**{*;}
#-keep class com.aixunyun.product.imclient.api.IMConfig{*;}
