cmake_minimum_required(VERSION 3.4.1)

include_directories(common)

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -std=c11")

add_subdirectory(libxcrash/jni)
add_subdirectory(libxcrash_dumper/jni)