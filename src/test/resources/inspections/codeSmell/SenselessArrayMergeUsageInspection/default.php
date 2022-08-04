<?php

use function array_merge;

$dummy = <weak_warning descr="🔨 PHP Hammer: senseless array_merge() usage.">array_merge()</weak_warning>;

$dummy = <weak_warning descr="🔨 PHP Hammer: senseless array_merge() usage.">array_merge([1, 2, 3])</weak_warning>;

// Not applicable:

$dummy = array_merge([1, 2, 3], [1, 2, 3]);

$dummy = array_merge(... [1, 2, 3]);

$dummy = array_merge(... x());

$dummy = array_merge(...);
