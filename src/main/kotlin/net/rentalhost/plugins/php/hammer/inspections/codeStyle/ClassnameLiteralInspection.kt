package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.lang.PhpLangUtil
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.BinaryExpression
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.services.*

class ClassnameLiteralInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpStringLiteralExpression(string: StringLiteralExpression) {
            if (!string.contents.contains("\\"))
                return

            val stringParent = string.parent

            if (stringParent is BinaryExpression &&
                !TypeService.compareOperations.contains(stringParent.operationType))
                return

            val stringNormalized = StringService.unescapeString(string)

            if (!LanguageService.isQualifiedClassname(stringNormalized))
                return

            val classname = PhpLangUtil.toFQN(stringNormalized)
            val classReference = ClassService.findFQN(classname, string.project) ?: return

            ProblemsHolderService.registerProblem(
                problemsHolder,
                string,
                "string can be replaced by ::class equivalent",
                LocalQuickFixService.SimpleReplaceQuickFix(
                    "Replace with ::class equivalent",
                    lazy { FactoryService.createClassConstantReference(problemsHolder.project, classReference.fqn) }
                )
            )
        }
    }
}
