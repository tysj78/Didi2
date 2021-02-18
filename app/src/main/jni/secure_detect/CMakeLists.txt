######## libcheckstatus.so
add_library(# Sets the name of the library.
          checkstatus

          # Sets the library as a shared library.
          SHARED

          debugDetect.cpp
          injectDetect.cpp
          pltHookDetect.cpp
          elfParser.cpp
          memmap.cpp
          memmap.h
          uninstall.cpp
          uninstall.h
          nettools.cpp
          system.cpp
)

target_link_libraries( # Specifies the target library.
                      checkstatus

                      # Links the target library to the log library
                      # included in the NDK.
                      log
                      )