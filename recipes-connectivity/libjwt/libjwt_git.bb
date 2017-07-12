LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=9741c346eef56131163e13b9db1241b3"

DEPENDS += " jansson openssl " 
RDEPENDS_${PN} += " jansson openssl "

inherit autotools pkgconfig

SRC_URI = "git://github.com/benmcollins/libjwt.git;protocol=https"

PROVIDES = "libjwt" 

PV = "1.7.4+git${SRCPV}"
SRCREV = "6e014fe1dd18febfcce001a4f0b741d9236a82ca"

S = "${WORKDIR}/git"
