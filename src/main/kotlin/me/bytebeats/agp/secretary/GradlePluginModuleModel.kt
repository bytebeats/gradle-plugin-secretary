package me.bytebeats.agp.secretary

import com.android.tools.idea.npw.template.TemplateResolver
import com.android.tools.idea.observable.core.BoolValueProperty
import com.android.tools.idea.observable.core.StringValueProperty
import com.android.tools.idea.wizard.model.WizardModel
import com.intellij.openapi.project.Project
import org.gradle.buildinit.plugins.internal.TemplateValue
import java.io.File


/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/8/6 20:44
 * @Version 1.0
 * @Description TO-DO
 */

class GradlePluginModuleModel(val project: Project, val templateDir: File) ://val templateHandle: TemplateHandle,
        WizardModel() {
    val pluginName = StringValueProperty("plugin")
    val packageName = StringValueProperty()
    val className = StringValueProperty("MyPlugin")
    val createGitIgnore = BoolValueProperty(true)
    override fun handleFinished() {
        TODO("Not yet implemented")
    }

    fun createModule() {
        val moduleRoot = File(project.basePath, pluginName.get())
        val templateValues = mutableMapOf<String, Any>()
//        val injector =
    }


}