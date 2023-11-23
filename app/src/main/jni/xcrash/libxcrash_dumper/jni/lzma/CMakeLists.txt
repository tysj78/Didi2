cmake_minimum_required(VERSION 3.4.1)


SET(LZMA_SRC_LIST 7zCrc.c
             7zCrcOpt.c
             CpuArch.c Bra.c
             Bra86.c
             BraIA64.c
             Delta.c
             Lzma2Dec.c
             LzmaDec.c
             Sha256.c
             Xz.c
             XzCrc64.c
             XzCrc64Opt.c
             XzDec.c
             Alloc.c)

add_library(lzma ${LZMA_SRC_LIST})

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -std=c11 -D_7ZIP_ST -fPIE -c")