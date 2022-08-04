package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.enums.OptionNullCheckFormat
import net.rentalhost.plugins.services.TestCase

class NullCheckFormatInspectionTestCase: TestCase() {
    fun testNullCheckFormatComparison(): Unit =
        testInspection(
            NullCheckFormatInspection::class.java,
            "nullCheckFormatComparison",
            { it.nullCheckFormat = OptionNullCheckFormat.COMPARISON }
        )

    fun testNullCheckFormatFunction(): Unit =
        testInspection(
            NullCheckFormatInspection::class.java,
            "nullCheckFormatFunction",
            { it.nullCheckFormat = OptionNullCheckFormat.FUNCTION }
        )
}
