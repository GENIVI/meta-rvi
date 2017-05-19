LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9741c346eef56131163e13b9db1241b3"

inherit autotools pkgconfig 

DEPENDS += " jansson libjwt "
RDEPENDS_${PN} += " jansson libjwt "

SRC_URI = " \
    git://github.com/GENIVI/rvi_lib.git;protocol=https;branch=master \
    file://0100-ignore-libjwt-submodule.patch \
    file://conf.json \
"

PV = "0.1+git${SRCPV}"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

RVI_DIR = "${sysconfdir}/rvi"

do_install_append() {
    install -d ${D}${RVI_DIR}
    install -m 0644 ${WORKDIR}/conf.json ${D}${sysconfdir}/rvi/

    install -d ${D}${RVI_DIR}/keys
    install -d ${D}${RVI_DIR}/certificates
    install -d ${D}${RVI_DIR}/credentials
    install -m 0755 ${S}/examples/ex/keys/* ${D}${RVI_DIR}/keys/
    install -m 0755 ${S}/examples/ex/certificates/* ${D}${RVI_DIR}/certificates/
    install -m 0755 ${S}/examples/ex/credentials/* ${D}${RVI_DIR}/credentials/
}
