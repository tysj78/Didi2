//
// Created by uniking on 17-10-17.
//

#ifndef MSM_URLMONITOR_H
#define MSM_URLMONITOR_H

using namespace std;

#include <map>
#include <vector>
#include <set>
#include <string>


class UrlMonitor {

private:
    set<int> m_socket_list;
public:
    void addSocket(int s);
    void deleteSocket(int s);
    bool isSocket(int s);

    bool isHttp(const void *buf);
    string getUrl(const void *buf);
    string getHost(const void *buf);
    string getUri(const void *buf);

    bool isWebViewAddr(const void *buf);
    string getWebViewUri(const void *buf);
};


#endif //MSM_URLMONITOR_H
