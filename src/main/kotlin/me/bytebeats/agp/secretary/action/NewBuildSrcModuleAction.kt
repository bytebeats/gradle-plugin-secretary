package me.bytebeats.agp.secretary.action

import com.android.tools.idea.gradle.project.GradleProjectInfo
import com.android.tools.idea.projectsystem.getProjectSystem
import com.android.tools.idea.sdk.wizard.SdkQuickfixUtils
import com.android.tools.idea.ui.wizard.StudioWizardDialogBuilder
import com.android.tools.idea.wizard.model.ModelWizard
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.ui.DialogWrapper
import me.bytebeats.agp.secretary.*
import me.bytebeats.agp.secretary.Resources.getString
import me.bytebeats.agp.secretary.ui.GradlePluginModuleStep
import org.jetbrains.android.sdk.AndroidSdkUtils
import org.jetbrains.annotations.Nullable
import javax.swing.Icon

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/8/7 17:11
 * @Version 1.0
 * @Description TO-DO
 */

open class NewBuildSrcModuleAction(
    text: String = getString(NEW_BUILD_SRC_MODULE),
    description: String = getString(NEW_BUILD_SRC_MODULE_DESC),
    icon: Icon = AllIcons.Nodes.Module
) : AnAction(text, description, icon), DumbAware {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project
        var isAvailable = project?.getProjectSystem()?.allowsFileCreation() ?: return
        isAvailable = isAvailable and GradleProjectInfo.getInstance(project).isBuildWithGradle
        if (!isAvailable) return
        if (!AndroidSdkUtils.isAndroidSdkAvailable()) {
            SdkQuickfixUtils.showSdkMissingDialog()
            return
        }
        val pair = ModuleTemplateHelper.buildSrcTemplate() ?: return
        val template = pair.first
        val wizard = ModelWizard.Builder().addStep(
            GradlePluginModuleStep(
                GradlePluginModuleModel(project, template, pair.second),
                "BuildSrc",
                ModuleType.BUILD_SRC
            )
        ).build()
        StudioWizardDialogBuilder(
            wizard,
            getString(CREATE_NEW_MODULE)
        ).setModalityType(DialogWrapper.IdeModalityType.IDE)
            .build()
            .show()
    }

    override fun update(e: AnActionEvent) {
        val project = e.project
        val isAvailable = project?.getProjectSystem()?.allowsFileCreation() == true &&
                GradleProjectInfo.getInstance(project).isBuildWithGradle
        e.presentation.isVisible = isAvailable
        e.presentation.isEnabled = isAvailable && getEventProject(e) != null
    }
}