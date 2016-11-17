# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)
#
# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=9741c346eef56131163e13b9db1241b3"

DEPENDS += " jansson openssl " 
RDEPENDS_${PN} += " jansson openssl "

inherit autotools pkgconfig

SRC_URI = "git://github.com/benmcollins/libjwt.git;protocol=https"

PROVIDES = "libjwt" 

# Modify these as desired
PV = "0.1+git${SRCPV}"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"
