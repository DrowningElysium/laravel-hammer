<?php

$dummy = function (string $x) {
    <error descr="🔨 PHP Hammer: native type must not be used as object.">$x</error>->dummy();
};

$dummy = function (string|null $x) {
    <error descr="🔨 PHP Hammer: native type must not be used as object.">$x</error>->dummy();
};

$dummy = function (DateTime|string $x) {
    <error descr="🔨 PHP Hammer: native type must not be used as object.">$x</error>->dummy();
};

$dummy = function (int $x) {
    <error descr="🔨 PHP Hammer: native type must not be used as object.">$x</error>::dummy();
};

$dummy = function (int $x) {
    <error descr="🔨 PHP Hammer: native type must not be used as object.">$x</error>::DUMMY;
};

$dummy = function (DateTime|string|int $x) {
    <error descr="🔨 PHP Hammer: native type must not be used as object.">$x</error>::createFromFormat();
};

// Not applicable:

$dummy = function (DateTime|string $x) {
    $x::createFromFormat();
};

$dummy = function (string $x) {
    $x::dummy();
};

$dummy = function (string $x) {
    $x::DUMMY;
};

$dummy = function ($x) {
    $x->dummy();
};

$dummy = function ($x = null) {
    $x->dummy();
};

$dummy = function (mixed $x) {
    $x->dummy();
};

$dummy = function (DateTime $x) {
    $x->dummy();
};
