package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import net.rentalhost.plugins.services.TestCase

class IfSimplificationElseInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(IfSimplificationElseInspection::class.java)
}
