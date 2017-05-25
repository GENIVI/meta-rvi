LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=9741c346eef56131163e13b9db1241b3"

DEPENDS += " jansson openssl " 
RDEPENDS_${PN} += " jansson openssl "

inherit autotools pkgconfig

SRC_URI = "git://github.com/benmcollins/libjwt.git;protocol=https"

PROVIDES = "libjwt" 

PV = "0.1+git${SRCPV}"
SRCREV = "9b608c445295ee76b2b045f4ea6de1378ca6add2"

S = "${WORKDIR}/git"
