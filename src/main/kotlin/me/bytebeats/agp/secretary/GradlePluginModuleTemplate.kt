package me.bytebeats.agp.secretary

import com.android.SdkConstants
import com.android.tools.idea.npw.module.getModuleRoot
import com.android.tools.idea.projectsystem.AndroidModulePathsImpl
import com.android.tools.idea.projectsystem.NamedModuleTemplate
import java.io.File


/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/8/6 20:29
 * @Version 1.0
 * @Description TO-DO
 */

class GradlePluginModuleTemplate {
    companion object {
        fun createDefaultTemplateAt(projectPath: String, moduleName: String): NamedModuleTemplate {
            val moduleRoot = getModuleRoot(projectPath, moduleName)
            val baseSrcDir = File(moduleRoot, SdkConstants.FD_SOURCES)
            val baseFlavorDir = File(baseSrcDir, SdkConstants.FD_MAIN)
            val paths = AndroidModulePathsImpl(
                moduleRoot = moduleRoot,
                manifestDirectory = null,
                srcRoot = File(baseFlavorDir, "groovy"),
                unitTestRoot = File(baseFlavorDir, "test"),
                testRoot = File(baseFlavorDir, "groovyTest"),
                aidlRoot = null,
                resDirectories = listOf(File(baseFlavorDir, "resources")),
                mlModelsDirectories = listOf(File(baseFlavorDir, "ml"))
            )
            return NamedModuleTemplate(SdkConstants.FD_MAIN, paths)
        }
    }
}