package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.ArrayCreationExpression
import com.jetbrains.php.lang.psi.elements.MultiassignmentExpression
import com.jetbrains.php.lang.psi.elements.impl.ArrayHashElementImpl
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl
import com.jetbrains.php.lang.psi.elements.impl.VariableImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.extensions.psi.isRef
import net.rentalhost.plugins.extensions.psi.unpackValues
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService

class CompactReplacementInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpArrayCreationExpression(element: ArrayCreationExpression) {
            if (element.parent is MultiassignmentExpression)
                return

            val arrayElements = element.unpackValues()

            if (arrayElements.isEmpty())
                return

            val arrayVariables = mutableListOf<String>()

            for (arrayElement in arrayElements) {
                if (arrayElement !is ArrayHashElementImpl)
                    return

                val arrayElementValue = arrayElement.value as? VariableImpl ?: return

                if (arrayElementValue.isRef())
                    return

                val arrayElementKey = arrayElement.key as? StringLiteralExpressionImpl ?: return

                if (arrayElementKey.contents != arrayElementValue.name)
                    return

                arrayVariables.add("'${arrayElementValue.name}'")
            }

            ProblemsHolderService.registerProblem(
                problemsHolder,
                element,
                "array can be replaced by compact()",
                LocalQuickFixService.SimpleReplaceQuickFix(
                    "Replace with compact()",
                    FactoryService.createFunctionCall(problemsHolder.project, "compact", arrayVariables)
                )
            )
        }
    }
}
