FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PRINC = "1"

SRC_URI_append_nokia900 = " file://keymap-2.6.map"

do_install_append_nokia900 () {
  install -m 0644 ${WORKDIR}/keymap-*.map     ${D}${sysconfdir}
}
