package me.bytebeats.agp.secretary

import com.android.utils.FileUtils
import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.extensions.PluginId
import java.io.File
import java.io.FileOutputStream
import java.util.jar.JarFile

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/8/6 20:03
 * @Version 1.0
 * @Description TO-DO
 */

object ModuleTemplateHelper {
    private fun checkoutTemplate(): File? {
        val descriptor = PluginManagerCore.getPlugin(PluginId.getId(PLUGIN_ID))
            ?: throw IllegalStateException("can't find Plugin by $PLUGIN_ID")
        val pluginPath = descriptor.pluginPath.toFile()
        if (!pluginPath.exists()) {
            return null
        }
        try {
            var pluginFile: File? = null
            if (pluginPath.isFile && pluginPath.name.endsWith(DOT_JAR)) {
                pluginFile = pluginPath
            } else {
                pluginFile = File(pluginPath, "$PLUGIN_NAME-${descriptor.version}$DOT_JAR")
                if (!pluginFile.exists()) {
                    pluginFile = File(pluginPath, "lib${File.separator}$PLUGIN_NAME-${descriptor.version}$DOT_JAR")
                }
            }
            val destDir = File("${pluginFile?.parent}${File.separator}${pluginFile?.name?.replace(DOT_JAR, "")}")
            if (destDir.exists() && destDir.isDirectory) {
                FileUtils.deletePath(destDir)
            }
            destDir.mkdirs()
            val jar = JarFile(pluginFile)
            val entries = jar.entries()
            while (entries.hasMoreElements()) {
                val entry = entries.nextElement()
                if (entry.isDirectory) {
                    val outDir = File(destDir, entry.name)
                    outDir.mkdirs()
                } else {
                    val outFile = File(destDir, entry.name)
                    outFile.parentFile.mkdirs()
                    val jis = jar.getInputStream(entry)
                    val fos = FileOutputStream(outFile)
                    while (jis.available() > 0) {
                        fos.write(jis.read())
                    }
                    fos.close()
                    jis.close()
                }
            }
            jar.close()
            return destDir
        } catch (ignore: Exception) {

        }
        return null
    }

    fun gradlePluginTemplate(): Pair<File, File>? {
        val dir = checkoutTemplate()
        return if (dir == null) null else Pair(File(dir, "templates${File.separator}$TEMPLATE_GRADLE_PLUGIN"), dir)
    }

    fun buildSrcTemplate(): Pair<File, File>? {
        val dir = checkoutTemplate()
        return if (dir == null) null else Pair(File(dir, "templates${File.separator}$TEMPLATE_BUILD_SRC"), dir)
    }

}