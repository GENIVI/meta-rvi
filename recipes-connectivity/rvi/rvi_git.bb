require rvi.inc

SRC_URI = "git://github.com/GENIVI/rvi_core;branch=develop;name=rvi;protocol=https"
SRCREV_rvi = "${AUTOREV}"

PV = "0.5.0+git${SRCPV}"

PR = "${INC_PR}.0"
