package net.rentalhost.plugins.php.hammer.inspections.deprecation

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.Variable
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService

class DollarSignOutsideCurlyBracesInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpVariable(element: Variable) {
            val parent = element.parent

            if (parent !is StringLiteralExpressionImpl)
                return

            val elementCurly = element.text

            if (!elementCurly.startsWith("\${") ||
                !elementCurly.endsWith("}"))
                return

            val replaceWith = elementCurly.substring(2, elementCurly.length - 1)

            ProblemsHolderService.registerProblem(
                problemsHolder,
                element,
                "using \${var} in strings is deprecated",
                LocalQuickFixService.SimpleReplaceQuickFix(
                    "Replace with {\$$replaceWith}",
                    FactoryService.createCurlyVariable(problemsHolder.project, replaceWith)
                )
            )
        }
    }
}
