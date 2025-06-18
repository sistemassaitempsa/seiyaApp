# Habilita conservación de anotaciones
-keepattributes *Annotation*

# Conserva las clases y métodos anotados con @Keep
-keep class ** {
    @androidx.annotation.Keep *;
}

# Conserva los constructores públicos y métodos para serialización (ej: Gson, Moshi)
-keepclassmembers class * {
    public <init>(...);
}

# Evita que se ofusquen los nombres de clases en android.support y androidx
-keep class android.support.** { *; }
-keep class androidx.** { *; }

# Si usas WebView con interfaces JS, descomenta esto y reemplaza con tu clase real
# -keepclassmembers class com.tu.paquete.TuClaseWebView {
#     public *;
# }

# Conserva los nombres de los archivos fuente y los números de línea para stacktraces
-keepattributes SourceFile,LineNumberTable

# Please add these rules to your existing keep rules in order to suppress warnings.
# This is generated automatically by the Android Gradle plugin.
-dontwarn org.chromium.net.CronetEngine
-dontwarn org.chromium.net.UploadDataProvider
-dontwarn org.chromium.net.UploadDataProviders
-dontwarn org.chromium.net.UrlRequest$Builder
-dontwarn org.chromium.net.UrlRequest$Callback
-dontwarn org.chromium.net.UrlRequest
