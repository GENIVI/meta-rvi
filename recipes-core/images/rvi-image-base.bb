SUMMARY = "A console-only image with RVI support that fully supports the target device \
hardware."

IMAGE_FEATURES += "package-management ssh-server-openssh"

LICENSE = "MIT"

inherit core-image

IMAGE_INSTALL += "python ncurses git"
IMAGE_INSTALL += "erlang rebar"
IMAGE_INSTALL += "rvi"


IMAGE_FSTYPES += "tar.bz2"

