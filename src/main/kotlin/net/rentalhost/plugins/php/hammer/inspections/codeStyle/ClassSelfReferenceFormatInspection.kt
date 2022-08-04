package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.ClassReference
import com.jetbrains.php.lang.psi.elements.impl.PhpClassImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.enums.OptionClassSelfReferenceFormat
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.OptionsPanelService
import net.rentalhost.plugins.services.ProblemsHolderService
import javax.swing.JComponent

class ClassSelfReferenceFormatInspection: PhpInspection() {
    @OptionTag
    var classSelfReferenceFormat: OptionClassSelfReferenceFormat = OptionClassSelfReferenceFormat.SELF

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpClassReference(element: ClassReference) {
            val elementClass = PsiTreeUtil.getParentOfType(element, PhpClassImpl::class.java) ?: return
            val elementClassName = elementClass.name

            val referenceName = element.text.lowercase()

            if (classSelfReferenceFormat == OptionClassSelfReferenceFormat.SELF) {
                if (referenceName == "self" ||
                    referenceName != elementClassName.lowercase()) {
                    return
                }
            }
            else if (referenceName != "self" ||
                     referenceName == elementClassName.lowercase()) {
                return
            }

            val expectedFormat =
                if (classSelfReferenceFormat == OptionClassSelfReferenceFormat.SELF) "self"
                else elementClassName

            ProblemsHolderService.registerProblem(
                problemsHolder,
                element,
                "class reference format must be \"$expectedFormat\"",
                LocalQuickFixService.SimpleReplaceQuickFix(
                    "Replace with \"$expectedFormat\"",
                    FactoryService.createClassReference(problemsHolder.project, expectedFormat)
                )
            )
        }
    }

    override fun createOptionsPanel(): JComponent {
        return OptionsPanelService.create { component: OptionsPanelService ->
            component.delegateRadioCreation { radioComponent: OptionsPanelService.RadioComponent ->
                radioComponent.addOption(
                    "Prefer self reference", classSelfReferenceFormat === OptionClassSelfReferenceFormat.SELF,
                    "It will replace references to the class itself such as <code>Dummy::something()</code> with <code>self::something()</code>."
                ) { classSelfReferenceFormat = OptionClassSelfReferenceFormat.SELF }

                radioComponent.addOption(
                    "Prefer ClassName reference", classSelfReferenceFormat === OptionClassSelfReferenceFormat.NAMED,
                    "It will replace references to the class itself such as <code>self::something()</code> with <code>Dummy::something()</code>"
                ) { classSelfReferenceFormat = OptionClassSelfReferenceFormat.NAMED }
            }
        }
    }
}
