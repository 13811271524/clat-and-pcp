/*
 * Copyright 2011 Daniel Drown
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * config.h - configuration settings
 */
#ifndef __CONFIG_H__
#define __CONFIG_H__

#include <netinet/in.h>
#include <sys/system_properties.h>

#define DEFAULT_IPV4_LOCAL_SUBNET "192.168.255.1"
#define DEFAULT_DNS64_DETECTION_HOSTNAME "ipv4.google.com"

struct clat_config {
  int16_t mtu, ipv4mtu;
  struct in6_addr ipv6_local_subnet;
  struct in6_addr ipv6_host_id;
  struct in_addr ipv4_local_subnet;
  struct in6_addr plat_subnet;
  char default_pdp_interface[PROP_VALUE_MAX];
  char *plat_from_dns64_hostname;
};

extern struct clat_config Global_Clatd_Config;
//zi ji jia
int length;
char (*ch4)[50],(*ch6)[50],(*fix4)[50],(*fix6)[50];
uint32_t (*daddr4), (*daddr4gb);
uint8_t destinationsz[4];
//zi ji jia..
void duwenjian();
uint32_t fanhuiwldz(uint32_t daddr4,char *fix4);
uint32_t fanhuigbdz(uint32_t daddr4,char *fix4);
uint32_t fanhuiyanma(char *fix4);
uint32_t zhuanhuan(uint32_t addr);
uint16_t zhuanhuan16 (uint16_t addr);



int read_config(const char *file, const char *uplink_interface, const char *plat_prefix);
void config_generate_local_ipv6_subnet(struct in6_addr *interface_ip);

#endif /* __CONFIG_H__ */
