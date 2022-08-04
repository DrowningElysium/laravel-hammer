<?php

$dummy = function () use ($a, $b, $c) {
    return $a + $b + $c;
};

$dummy = function () use ($a, &$b, $c) {
    $b = 2;

    return $a + $c;
};

$dummy = function () use ($b) {
    $a[][] = true;
};

$dummy = function () use ($b) {
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
