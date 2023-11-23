#include "memmap.h"
#include "elfParser.h"
#include <stdio.h>
#include <elf.h>
#include <fcntl.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

vector<string> printX64ELF(const char *start) {
    const Elf64_Ehdr *hdr = (Elf64_Ehdr *) start;
    //printf("section offset 0x%x, num %d\n", hdr->e_shoff, hdr->e_shnum);
    const Elf64_Shdr *pSection = (const Elf64_Shdr *) (start + hdr->e_shoff);

    const char *pstringTable = (start + (pSection + hdr->e_shstrndx)->sh_offset);
    Elf64_Word lenght = (pSection + hdr->e_shstrndx)->sh_size;
    //getStringTable64(pstringTable, lenght);

    const char *pstrtab = 0;
    Elf64_Word sstrtab = 0;
    const char *pdynstr = 0;
    Elf64_Word sdynstr = 0;
    const char *pdynamic = 0;
    Elf64_Word sdynamic = 0;
    Elf64_Xword dmEntsize = 0;
    for (int i = 0; i < hdr->e_shnum; ++i) {
        //printf("%x %s %d\n", i, pstringTable + pSection->sh_name, pSection->sh_entsize);
        ++pSection;

        if (strncmp(".strtab", pstringTable + pSection->sh_name, 7) == 0) {
            pstrtab = start + pSection->sh_offset;
            sstrtab = pSection->sh_size;
        }

        if (strncmp(".dynstr", pstringTable + pSection->sh_name, 7) == 0) {
            pdynstr = start + pSection->sh_offset;
            sdynstr = pSection->sh_size;
        }

        if (strncmp(".dynamic", pstringTable + pSection->sh_name, 8) == 0) {
            pdynamic = start + pSection->sh_offset;
            sdynamic = pSection->sh_size;
            dmEntsize = sdynamic / sizeof(Elf64_Dyn);
        }
    }

    //getStringTable64(pstrtab, sstrtab);
    //getStringTable64(pdynstr, sdynstr);

    //依赖的so文件
    Elf64_Dyn *one = (Elf64_Dyn *) pdynamic;
    vector <string> result;
    for (int i = 0; i < dmEntsize; ++i) {
        if (one->d_tag == 1) {
            printf("%lld %s\n", one->d_tag, pdynstr + one->d_un.d_val);
            result.push_back(pdynstr + one->d_un.d_val);
        } else;//printf("%d %d\n", one->d_tag, one->d_un);
        ++one;
    }

    return result;
}

vector<string> printArm32ELF(const char *start) {
    const Elf32_Ehdr *hdr = (Elf32_Ehdr *) start;
    //printf("section offset 0x%x, num %d\n", hdr->e_shoff, hdr->e_shnum);
    const Elf32_Shdr *pSection = (const Elf32_Shdr *) (start + hdr->e_shoff);

    const char *pstringTable = (start + (pSection + hdr->e_shstrndx)->sh_offset);
    Elf32_Word lenght = (pSection + hdr->e_shstrndx)->sh_size;
    //getStringTable64(pstringTable, lenght);

    const char *pstrtab = 0;
    Elf32_Word sstrtab = 0;
    const char *pdynstr = 0;
    Elf32_Word sdynstr = 0;
    const char *pdynamic = 0;
    Elf32_Word sdynamic = 0;
    Elf32_Word dmEntsize = 0;
    for (int i = 0; i < hdr->e_shnum; ++i) {
        //printf("%x %s %d\n", i, pstringTable + pSection->sh_name, pSection->sh_entsize);
        ++pSection;

        if (strncmp(".strtab", pstringTable + pSection->sh_name, 7) == 0) {
            pstrtab = start + pSection->sh_offset;
            sstrtab = pSection->sh_size;
        }

        if (strncmp(".dynstr", pstringTable + pSection->sh_name, 7) == 0) {
            pdynstr = start + pSection->sh_offset;
            sdynstr = pSection->sh_size;
        }

        if (strncmp(".dynamic", pstringTable + pSection->sh_name, 8) == 0) {
            pdynamic = start + pSection->sh_offset;
            sdynamic = pSection->sh_size;
            dmEntsize = sdynamic / sizeof(Elf32_Dyn);
        }
    }

    //getStringTable64(pstrtab, sstrtab);
    //getStringTable64(pdynstr, sdynstr);

    //依赖的so文件
    Elf32_Dyn *one = (Elf32_Dyn *) pdynamic;
    vector <string> result;
    for (int i = 0; i < dmEntsize; ++i) {
        if (one->d_tag == 1) {
            printf("%d %s\n", one->d_tag, pdynstr + one->d_un.d_val);
            result.push_back(pdynstr + one->d_un.d_val);
        } else;//printf("%d %d\n", one->d_tag, one->d_un);
        ++one;
    }

    return result;
}

vector<string> getDependence(string modulename, vector<string> prefix)
{
    vector <string> result;
    MemMap elfFile;
    const char *pData = NULL;

    string path;
    vector<string>::iterator prefixpath = prefix.begin();

    while (prefixpath != prefix.end()) {
        path = *prefixpath + modulename;

        bool bFailed = elfFile.Map(path.c_str());
        if (!bFailed) {
            printf("map error\n");
            ++prefixpath;
            continue;
        }

        size_t uFileSize = elfFile.GetSize();
        pData = (const char *)elfFile.GetData();
        if (uFileSize <= 0 || NULL == pData) {
            printf("getdata error\n");
            ++prefixpath;
            continue;
        }

        break;
    }

    if (prefixpath == prefix.end())
        return result;

    Elf32_Ehdr *hdr = (Elf32_Ehdr *) pData;
    printf("%d\n", hdr->e_machine);
    switch (hdr->e_machine) {
        case 62:
            result = printX64ELF(pData);
            break;
        case 40:
            result = printArm32ELF(pData);
            break;
        default:
            break;
    }

    return result;
}