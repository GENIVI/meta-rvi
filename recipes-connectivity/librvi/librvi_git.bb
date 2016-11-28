LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9741c346eef56131163e13b9db1241b3"

inherit autotools pkgconfig

DEPENDS += " jansson libjwt "
RDEPENDS_${PN} += " jansson libjwt "

SRC_URI = "git://github.com/tjamison/rvi_lib.git;protocol=https;branch=use-jansson-2.4"

PV = "0.1+git${SRCPV}"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"
