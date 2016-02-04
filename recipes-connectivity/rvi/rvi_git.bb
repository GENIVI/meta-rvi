SUMMARY = "Remote Vehicle Interaction (RVI) Core Node Server"
HOMEPAGE = "https://github.com/PDXostc/rvi_core"
BUGTRACKER = "https://github.com/PDXostc/rvi_core/issues"

LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b278a92d2c1509760384428817710378"

SRCREV_rvi = "0088b7bbec6c40d1ec3c45c7d0492484db96e9d9"
PV = "0.5.0+git${SRCPV}"

SRC_URI = "git://github.com/rstreif/rvi_core;branch=develop;name=rvi;protocol=https \
          "

DEPENDS = "erlang erlang-native rebar rebar-native bluez5 libcap"
RDEPENDS_${PN} = "python erlang erlang-kernel erlang-stdlib erlang-sasl \
                  erlang-syntax-tools erlang-crypto erlang-public-key \
                  erlang-compiler erlang-ssl erlang-asn1 \
                 "

S = "${WORKDIR}/git"

inherit update-rc.d systemd

SYSTEMD_SERVICE_${PN} = "rvi.service"

INITSCRIPT_NAME = "rvi"
INITSCRIPT_PARAMS = "start 99 3 5 . stop 20 0 1 2 6 ."

EXTRA_OEMAKE = ""

ERL_DIR = "${STAGING_DIR_TARGET}/usr/lib/erlang"
ERTS_DIR = "`ls -d ${ERL_DIR}/erts-*`"
EI_DIR = "`ls -d ${ERL_DIR}/lib/erl_interface*`"
export ERL_CFLAGS="-I ${EI_DIR}/include -I ${ERTS_DIR}/include"
export ERL_EI_LIBDIR="${EI_DIR}/lib"

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
	oe_runmake install DESTDIR=${D}

	# need to do this is RVI install script uses tar/untar for installation
        # of dependencies
	chown -R root:root ${D}${RVI_DIR}
    
	install -d ${D}${sysconfdir}/init.d/
	install -m 0755 scripts/rvi ${D}${sysconfdir}/init.d/rvi
	install -d ${D}${systemd_unitdir}/system
	install -m 0644 scripts/rvi.service ${D}${systemd_unitdir}/system
	sed -i -e 's,@SBINDIR@,${sbindir},g' ${D}${systemd_unitdir}/system/rvi.service
}

