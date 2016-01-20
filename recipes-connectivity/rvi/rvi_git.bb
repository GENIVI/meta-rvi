SUMMARY = "Remote Vehicle Interaction (RVI) Core Node Server"
HOMEPAGE = "https://github.com/PDXostc/rvi_core"
BUGTRACKER = "https://github.com/PDXostc/rvi_core/issues"

LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b278a92d2c1509760384428817710378"

SRCREV_rvi = "0d12de7d7706fc939544a3fb77637d3cfa56b8ba"
PV = "0.4.0+git${SRCPV}"

SRC_URI = "git://github.com/PDXostc/rvi_core;branch=release-0.4.0-yocto_makefile;name=rvi;protocol=https \
          "

DEPENDS = "erlang rebar bluez5"
RDEPENDS_${PN} = "python erlang erlang-kernel erlang-stdlib erlang-sasl \
                  erlang-syntax-tools erlang-crypto erlang-public-key \
                  erlang-compiler erlang-ssl erlang-asn1 \
                 "

S = "${WORKDIR}/git"

# need to do this because the pesky Erlang release scripts running during
# install expect the toplevel directory to be named after the application
base_do_unpack_append () {
    w = d.getVar("WORKDIR", True)
    os.symlink(w + "/git", w + "/rvi")
}

inherit update-rc.d systemd

SYSTEMD_SERVICE_${PN} = "rvi.service"

INITSCRIPT_NAME = "rvi"
INITSCRIPT_PARAMS = "start 99 3 5 . stop 20 0 1 2 6 ."

EXTRA_OEMAKE = ""

do_compile() {
	oe_runmake deps
	oe_runmake compile
}

RVI_DIR = "/opt/rvi"

FILES_RVI = "${RVI_DIR}/* \
            "
FILES_${PN} += "${FILES_RVI}"
INHIBIT_PACKAGE_STRIP = "1"

do_install() {
	pushd ${WORKDIR}/rvi
	oe_runmake install DESTDIR=${D}
    
	install -d ${D}${sysconfdir}/init.d/
	install -m 0755 scripts/rvi ${D}${sysconfdir}/init.d/rvi
	install -d ${D}${systemd_unitdir}/system
	install -m 0644 scripts/rvi.service ${D}${systemd_unitdir}/system
	sed -i -e 's,@SBINDIR@,${sbindir},g' ${D}${systemd_unitdir}/system/rvi.service
	popd
}

