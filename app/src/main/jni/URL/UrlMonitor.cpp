//
// Created by uniking on 17-10-17.
//

#include <cstring>
#include "UrlMonitor.h"
#include "../ElfHook/elf_common.h"

void stringSplit(string src, string pattern, vector<string>& strVec)
{
    string::size_type pos=0;
    src += pattern;
    int size = src.size();

    for(int i=0; i<size; i++)
    {
        pos = src.find(pattern, i);
        if(i == pos)
            break;
        if(pos < size)
        {
            string s = src.substr(i, pos-i);
            strVec.push_back(s);
            i=pos + pattern.size()-1;
        }
    }
}

void UrlMonitor::addSocket(int s) {
    m_socket_list.insert(s);
}

void UrlMonitor::deleteSocket(int s) {
    m_socket_list.erase(s);
}

bool UrlMonitor::isSocket(int s) {
    if(m_socket_list.find(s) == m_socket_list.end())
        return false;

    return true;
}

bool UrlMonitor::isHttp(const void *buf)
{
    //00000000: 4745 5420 2f0a                           GET /.
    // 0x20544547
    const unsigned int * c = (const unsigned int *)buf;
    if(0x20544547 == *c)
    {
        return true;
    }

    return false;
}



string UrlMonitor::getUrl(const void *buf)
{
    string a = "";
    return a;
}

string UrlMonitor::getHost(const void *buf)
{
    string a = "";
    return a;
}

string UrlMonitor::getUri(const void *buf)
{
    string url="";
    string host="";
    string sb = (const char*)buf;
    vector<string> lines;

    if(!isHttp(buf))
        return "";

    stringSplit(sb, "\r", lines);
    vector<string>::iterator line = lines.begin();

    vector<string> commands;
    stringSplit(*line, " ", commands);
    url = commands[1];
    ++line;
    while(line != lines.end())
    {
        vector<string> keyvals;
        stringSplit(*line, ":", keyvals);
        if(keyvals[0] == "Host")
        {
            host = keyvals[1];
            break;
        }

        ++line;
    }

    return host+url;
}

bool UrlMonitor::isWebViewAddr(const void *buf)
{
    //getaddrinfo www.sougou.com ^ 1024 2 1 0 0
    log_info("%s", buf);
    if(0 == memcmp(buf, "getaddrinfo", 11))
        return true;
    return false;
}

string UrlMonitor::getWebViewUri(const void *buf)
{
    if(!isWebViewAddr(buf))
        return "";
    //log_info("%s", buf);
    string msg = (const char*)buf;
    vector<string> info;
    stringSplit(msg, " ", info);

    return info[1];
}