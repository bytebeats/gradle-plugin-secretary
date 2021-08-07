package me.bytebeats.agp.secretary

import com.android.tools.idea.npw.model.TemplateMetrics
import com.android.tools.idea.observable.core.BoolValueProperty
import com.android.tools.idea.observable.core.StringValueProperty
import com.android.tools.idea.wizard.model.WizardModel
import com.android.utils.FileUtils
import com.intellij.codeInsight.template.Template
import com.intellij.openapi.project.Project
import java.io.File


/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/8/6 20:44
 * @Version 1.0
 * @Description TO-DO
 */

class GradlePluginModuleModel(
    val project: Project,
    val template: Template,
    val templateDirectory: File?
) ://val templateHandle: TemplateHandle,
        WizardModel() {
    val pluginName = StringValueProperty("plugin")
    val packageName = StringValueProperty()
    val className = StringValueProperty("MyPlugin")
    val createGitIgnore = BoolValueProperty(true)
    override fun handleFinished() {
        createModule()
        deleteTemplateDirectory()
    }

    override fun handleSkipped() {
        super.handleSkipped()
        deleteTemplateDirectory()
    }

    override fun dispose() {
        super.dispose()
        deleteTemplateDirectory()
    }

    private fun deleteTemplateDirectory() {
        try {
            templateDirectory?.let {
                FileUtils.deletePath(it)
            }
        } catch (ignore: Exception) {

        }
    }

    fun createModule() {
        val moduleRoot = File(project.basePath, pluginName.get())
        val templateValues = mutableMapOf<String, Any>()
//        val injector = TemplateResolver.Companion.
    }

    private fun renderTemplate(
        dryRun: Boolean,
        project: Project,
        moduleRoot: File,
        templateValues: Map<String, Any>,
        filesToOpen: List<File>?
    ) {


    }

}