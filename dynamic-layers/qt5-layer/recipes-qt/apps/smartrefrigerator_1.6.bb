DESCRIPTION = "Microchip QT smart refrigerator demo"
LICENSE = "ATMEL_LLA"
LIC_FILES_CHKSUM = "file://main.cpp;endline=146;md5=e461c6aa7c87631950f0a71c1552f706"

PR = "r2"

PACKAGES = "${PN}-dbg ${PN}"

DEPENDS = "qtbase qtwebkit libv4l"
inherit qmake5

SRC_URI = "git://github.com/linux4sam/smart-refrigerator.git;protocol=https"
PV = "1.6+git${SRCPV}"
SRCREV = "5c7bbb38ec639313d6b0e0d8e6919856eff8fc0a"

S = "${WORKDIR}/smart-refrigerator-${PV}"

inherit pkgconfig

FILES_${PN}-dbg = " \
	/opt/SmartRefrigerator/bin \
	/opt/SmartRefrigerator/bin/.debug \
	/opt/SmartRefrigerator/bin/.debug/SmartRefrigerator \
	/opt/SmartRefrigerator/.debug \
	/opt/SmartRefrigerator/.debug/SmartRefrigerator \
	/opt/SmartRefrigerator/CustomWidgets/Fridge-Whiteboard/Canvas/.debug/libcanvasplugin.so \
	/opt/SmartRefrigerator/output/* \
	/usr/* \
	"

FILES_${PN} = " \
	/etc/* \
	/op* \
	"

do_install() {
	make INSTALL_ROOT=${D} install
	cd ${B}/output/
	for file in $(find SmartRefrigerator ApplicationLauncher -type f); do
		if [ -x ${file} ]; then
			PERM="755"
		else
			PERM="644"
		fi
		echo install -m ${PERM} -D ${file} ${D}/opt/${file}
		install -m ${PERM} -D ${file} ${D}/opt/${file}
	done
	install -d ${D}/etc
	install -m 644 nhttpd.conf ${D}/etc/nhttpd.conf
}

pkg_postinst_PACKAGENAME() {
	echo "ma conf a moi" > ${D}/etc/nhttpd.conf
}
