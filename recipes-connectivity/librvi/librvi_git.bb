LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9741c346eef56131163e13b9db1241b3"

inherit autotools pkgconfig

DEPENDS += " jansson libjwt "
RDEPENDS_${PN} += " jansson libjwt "

SRC_URI = " \
    git://github.com/GENIVI/rvi_lib.git;protocol=https \
    file://0100-ignore-libjwt-submodule.patch \
"

PV = "1.0.1+git${SRCPV}"
SRCREV = "8bc04947118560b73af5b7a21d23be9b7fc138c9"

S = "${WORKDIR}/git"
