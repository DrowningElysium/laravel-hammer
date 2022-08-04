package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.extensions.psi.isName
import net.rentalhost.plugins.extensions.psi.isVariadicPreceded
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService

class SenselessArrayMergeUsageInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpFunctionCall(element: FunctionReference) {
            if (!element.isName("array_merge") ||
                element.parameterList == null)
                return

            val elementSimplified = when (element.parameters.size) {
                0 -> FactoryService.createArrayEmpty(problemsHolder.project)
                1 -> with(element.parameters[0]) { if (isVariadicPreceded()) return else this }
                else -> return
            }

            ProblemsHolderService.registerProblem(
                problemsHolder,
                element,
                "senseless array_merge() usage",
                LocalQuickFixService.SimpleReplaceQuickFix(
                    "Simplify useless array_merge()",
                    elementSimplified
                )
            )
        }
    }
}
