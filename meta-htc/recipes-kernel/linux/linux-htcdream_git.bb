require recipes-kernel/linux/linux.inc
DEPENDS += "android-image-utils-native"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

PE = "2"
PV = "2.6.32+${PR}+gitr${SRCPV}"
# for bumping PR bump MACHINE_KERNEL_PR in the machine config
inherit machine_kernel_pr

COMPATIBLE_MACHINE = "htcdream"
CMDLINE = "console=tty1 root=/dev/mmcblk0p1 rootdelay=8 fbcon=rotate:1 panic=30 mem=110M"

SRCREV = "bf7221ba95f418269dd573c9ff3e7f00452221a1"

SRC_URI = "\
  git://github.com/shr-distribution/linux.git;protocol=git;branch=htcdream/2.6.32/master \
  file://defconfig \
"
S = "${WORKDIR}/git"

do_deploy_append() {
    if [ ! -e empty.cpio.gz ];then
        echo -n | cpio -o -H newc | gzip > empty.cpio.gz
    fi
    mkbootimg --kernel ${S}/${KERNEL_OUTPUT} \
              --ramdisk empty.cpio.gz \
              --cmdline "${CMDLINE}" \
              --output ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGE_BASE_NAME}.fastboot

    cd ${DEPLOYDIR}
    rm -f ${KERNEL_IMAGE_SYMLINK_NAME}.fastboot
    ln -sf ${KERNEL_IMAGE_BASE_NAME}.fastboot ${KERNEL_IMAGE_SYMLINK_NAME}.fastboot
}

