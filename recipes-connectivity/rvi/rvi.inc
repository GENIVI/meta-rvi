SUMMARY = "Remote Vehicle Interaction (RVI) Core Node Server"
HOMEPAGE = "https://github.com/PDXostc/rvi_core"
BUGTRACKER = "https://github.com/PDXostc/rvi_core/issues"

LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b278a92d2c1509760384428817710378"

DEPENDS = "erlang erlang-native rebar rebar-native bluez5 libcap"
RDEPENDS_${PN} = "erlang erlang-kernel erlang-stdlib erlang-sasl \
                  erlang-syntax-tools erlang-crypto erlang-public-key \
                  erlang-compiler erlang-ssl erlang-asn1 erlang-eunit \
                  python \
                 "

INC_PR = "r0"

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

RVI_DIR = "/usr/share/rvi_core"

FILES_RVI = "${RVI_DIR}/*"
FILES_${PN} += "${FILES_RVI}"
INHIBIT_PACKAGE_STRIP = "1"

do_install() {
	oe_runmake install DESTDIR=${D} STRIPPATH=${D}

	# need to do this is RVI install script uses tar/untar for installation
        # of dependencies
	chown -R root:root ${D}${RVI_DIR}

	# install RVI default configuration file
	install -d ${D}${sysconfdir}/rvi/
	install -m 0644 ./priv/config/rvi_yocto.config ${D}${sysconfdir}/rvi/rvi.config

	# install startup scripts
	install -d ${D}${sysconfdir}/init.d/
	install -m 0755 yocto_template/rvi.init ${D}${sysconfdir}/init.d/rvi
	install -d ${D}${systemd_unitdir}/system
	install -m 0644 yocto_template/rvi.service ${D}${systemd_unitdir}/system
	sed -i -e 's,@SBINDIR@,${sbindir},g' ${D}${systemd_unitdir}/system/rvi.service
}

pkg_postinst_${PN} () {
#!/bin/sh -e
if test -z "$D"; then
   # actions to carry out on the device
   # set RVI device ID
   echo "genivi.org/node/$(cat /proc/sys/kernel/random/uuid)" > /etc/opt/rvi/device_id
   exit 0
else
   # actions to carry out during image creation
   exit 1
fi
}
