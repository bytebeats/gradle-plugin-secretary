package me.bytebeats.agp.secretary.action

import com.android.SdkConstants
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.ide.actions.CreateTemplateInPackageAction
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.InputValidatorEx
import com.intellij.psi.*
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.util.IncorrectOperationException
import icons.JetgroovyIcons
import me.bytebeats.agp.secretary.CREATE_GROOVY_CLASS
import me.bytebeats.agp.secretary.GROOVY_CLASS
import me.bytebeats.agp.secretary.NEW_GROOVY_CLASS
import me.bytebeats.agp.secretary.Resources.getString
import org.jetbrains.plugins.groovy.GroovyBundle
import org.jetbrains.plugins.groovy.actions.GroovyTemplatesFactory
import org.jetbrains.plugins.groovy.config.GroovyConfigUtils
import org.jetbrains.plugins.groovy.lang.psi.GroovyFile
import org.jetbrains.plugins.groovy.lang.psi.api.statements.typedef.GrTypeDefinition
import org.jetbrains.plugins.groovy.projectRoots.ROOT_TYPES

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/8/7 16:18
 * @Version 1.0
 * @Description TO-DO
 */

class NewGroovyClassAction : CreateTemplateInPackageAction<GrTypeDefinition>(
    getString(GROOVY_CLASS),
    getString(CREATE_GROOVY_CLASS),
    JetgroovyIcons.Groovy.Class,
    ROOT_TYPES
), DumbAware {

    override fun buildDialog(project: Project, directory: PsiDirectory, builder: CreateFileFromTemplateDialog.Builder) {
        builder.setTitle(getString(NEW_GROOVY_CLASS))
            .addKind("Class", JetgroovyIcons.Groovy.Class, "GroovyClass.groovy")
            .addKind("Interface", JetgroovyIcons.Groovy.Interface, "GroovyInterface.groovy")
        if (GroovyConfigUtils.getInstance().isVersionAtLeast(directory, "2.3", true)) {
            builder.addKind("Trait", JetgroovyIcons.Groovy.Trait, "GroovyTrait.groovy")
        }
        builder.addKind("Enum", JetgroovyIcons.Groovy.Enum, "GroovyEnum.groovy")
            .addKind("Annotation", JetgroovyIcons.Groovy.AnnotationType, "GroovyAnnotation.groovy")
            .setValidator(object : InputValidatorEx {
                override fun getErrorText(inputString: String?): String? = "This is not a valid Groovy qualified name"
                override fun checkInput(inputString: String?): Boolean = true
                override fun canClose(inputString: String?): Boolean =
                    !inputString.isNullOrEmpty() && PsiNameHelper.getInstance(project).isQualifiedName(inputString)
            })
    }

    override fun getActionName(directory: PsiDirectory?, newName: String, templateName: String?): String =
        getString(GROOVY_CLASS)

    override fun getNavigationElement(createdElement: GrTypeDefinition): PsiElement? = createdElement.lBrace

    override fun checkPackageExists(directory: PsiDirectory): Boolean {
        val psiPackage = JavaDirectoryService.getInstance().getPackage(directory) ?: return false
        val name = psiPackage.qualifiedName
        return name.isEmpty() || PsiNameHelper.getInstance(directory.project).isQualifiedName(name)
    }

    override fun doCreate(dir: PsiDirectory, className: String, templateName: String): GrTypeDefinition? {
        val fileName = "$className.groovy"
        val fromTemplate = GroovyTemplatesFactory.createFromTemplate(dir, className, fileName, templateName, true)
        if (fromTemplate is GroovyFile) {
            CodeStyleManager.getInstance(fromTemplate.manager).reformat(fromTemplate)
            return fromTemplate.typeDefinitions[0]
        } else {
            throw IncorrectOperationException(
                GroovyBundle.message(
                    "groovy.file.extension.is.not.mapped.to.groovy.file.type",
                    fromTemplate.fileType.description
                )
            )
        }
    }
}