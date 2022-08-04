<?php

$dummy = function () use ($a, <weak_warning descr="🔨 PHP Hammer: unused reference for variable declared in use().">&</weak_warning>$b, $c) {
    return $a + $b + $c;
};

$dummy = function () use (<weak_warning descr="🔨 PHP Hammer: unused reference for variable declared in use().">&</weak_warning>$a, &$b, <weak_warning descr="🔨 PHP Hammer: unused reference for variable declared in use().">&</weak_warning>$c) {
    $b = 2;

    return $a + $c;
};

$dummy = function () use (<weak_warning descr="🔨 PHP Hammer: unused reference for variable declared in use().">&</weak_warning>$b) {
    $a[][] = true;
};

$dummy = function () use (<weak_warning descr="🔨 PHP Hammer: unused reference for variable declared in use().">&</weak_warning>$b) {
    $a[][][][][] = true;
};

// Not applicable:

$dummy = function () use (&$a) {
    $a[] = 123;
};

$dummy = function () use (&$a) {
    array_push($a, 123);
};

$dummy = function () use (&$a) {
    (function () use (&$a) {
        $a = true;
    })();
};

$dummy = function () use (&$dummy) {
    $dummy();
};

$dummy = function () use (&$b) {
    $b[][] = true;
};

$dummy = function () use (&$b) {
    $b[][][][][] = true;
};

// Not applicable, but can be resolved by UnusedUseVariableInspection.

$dummy = function () use (&$dummy) {
};
