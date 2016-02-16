SUMMARY = "Remote Vehicle Interaction (RVI) Core Node Server"
HOMEPAGE = "https://github.com/PDXostc/rvi_core"
BUGTRACKER = "https://github.com/PDXostc/rvi_core/issues"

LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b278a92d2c1509760384428817710378"

SRC_URI = "git://github.com/rstreif/rvi_core;branch=develop;name=rvi;protocol=https"
SRCREV_rvi = "e705de160d54b9a73d7d7d9a18af44bf49d3c730"

#SRC_URI = "git://github.com/magnusfeuer/rvi_core;branch=mfeuer-0.5.0_ubuntu_build;name=rvi;protocol=https"
#SRCREV_rvi = "a3c08b3eea427c2f14a184ecdcab57b6cb3840cc"

#SRC_URI = "git://github.com/PDXostc/rvi_core;branch=develop;name=rvi;protocol=https"
#SRCREV_rvi = "b7d0b75637f1deb3dc9cebacbdeea39dac67b908"

PV = "0.5.0+git${SRCPV}"


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
        oe_runmake escript
}

RVI_DIR = "/opt/rvi_core"

FILES_RVI = "${RVI_DIR}/* \
            "
FILES_${PN} += "${FILES_RVI}"
INHIBIT_PACKAGE_STRIP = "1"

do_install() {
	oe_runmake install DESTDIR=${D} STRIPPATH=${D}

	# need to do this is RVI install script uses tar/untar for installation
        # of dependencies
	chown -R root:root ${D}${RVI_DIR}

	# install RVI default configuration file
	install -d ${D}${sysconfdir}/opt/rvi/
	install -m 0644 ./priv/config/rvi_ubuntu.config ${D}${sysconfdir}/opt/rvi/rvi.config
    
	# install startup scripts
	install -d ${D}${sysconfdir}/init.d/
	install -m 0755 yocto_template/rvi.init ${D}${sysconfdir}/init.d/rvi
	install -d ${D}${systemd_unitdir}/system
	install -m 0644 yocto_template/rvi.service ${D}${systemd_unitdir}/system
	sed -i -e 's,@SBINDIR@,${sbindir},g' ${D}${systemd_unitdir}/system/rvi.service
}

