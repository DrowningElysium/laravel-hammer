<?php

$dummy = function ($dummy) {
    <weak_warning descr="🔨 PHP Hammer: useless conditional can be safely dropped.">if</weak_warning> ($dummy)
        return true;
    else
        return true;
};

$dummy = function ($dummy) {
    <weak_warning descr="🔨 PHP Hammer: useless conditional can be safely dropped.">if</weak_warning> ($dummy) {
        return true;
    } else {
        return true;
    }
};

$dummy = function ($dummy) {
    <weak_warning descr="🔨 PHP Hammer: useless conditional can be safely dropped.">if</weak_warning> ($dummy) {
        $dummy++;

        return true;
    } else {
        $dummy++;

        return true;
    }
};

$dummy = function ($dummy) {
    if ($dummy) {
        return false;
    } <weak_warning descr="🔨 PHP Hammer: useless conditional can be safely dropped.">elseif</weak_warning> ($dummy) {
        $dummy++;

        return true;
    } else {
        $dummy++;

        return true;
    }
};

$dummy = function ($dummy) {
    if ($dummy) {
        return false;
    } else <weak_warning descr="🔨 PHP Hammer: useless conditional can be safely dropped.">if</weak_warning> ($dummy) {
        $dummy++;

        return true;
    } else {
        $dummy++;

        return true;
    }
};

$dummy = function ($dummy) {
    if ($dummy) {
        return false;
    } <weak_warning descr="🔨 PHP Hammer: useless conditional can be safely dropped.">elseif</weak_warning> ($dummy) {
        $dummy++;

        return true;
    } else {
        $dummy++;

        return true;
    }
};
