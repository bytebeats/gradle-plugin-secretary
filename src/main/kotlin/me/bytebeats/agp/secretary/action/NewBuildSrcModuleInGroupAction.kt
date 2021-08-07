package me.bytebeats.agp.secretary.action

import com.intellij.icons.AllIcons
import com.intellij.ide.projectView.impl.ModuleGroup
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import me.bytebeats.agp.secretary.NEW_BUILD_SRC_MODULE
import me.bytebeats.agp.secretary.NEW_BUILD_SRC_MODULE_DESC
import me.bytebeats.agp.secretary.Resources
import me.bytebeats.agp.secretary.Resources.getString
import javax.swing.Icon

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/8/7 17:52
 * @Version 1.0
 * @Description TO-DO
 */

class NewBuildSrcModuleInGroupAction : NewBuildSrcModuleAction(
    getString(NEW_BUILD_SRC_MODULE),
    getString(NEW_BUILD_SRC_MODULE_DESC),
    AllIcons.Nodes.Module
) {
    override fun update(e: AnActionEvent) {
        super.update(e)
        if (!e.presentation.isVisible) return// Nothing to do, if above call to parent update() has disabled the action
        val moduleGroups = e.getData(ModuleGroup.ARRAY_DATA_KEY)
        val modules = e.getData(LangDataKeys.MODULE_CONTEXT_ARRAY)
        e.presentation.isVisible = !moduleGroups.isNullOrEmpty() && !modules.isNullOrEmpty()
    }
}