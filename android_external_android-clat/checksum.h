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
 * checksum.h - checksum functions
 */
#ifndef __CHECKSUM_H__
#define __CHECKSUM_H__

uint32_t ip_checksum_add(uint32_t current_sum, const void *data, int len);
uint16_t ip_checksum_finish(uint32_t temp_sum);
uint16_t ip_checksum(const void *data, int len);

uint32_t ipv6_pseudo_header_checksum(uint32_t current_sum, const struct ip6_hdr *ip6);
uint32_t ipv4_pseudo_header_checksum(uint32_t current_sum, const struct iphdr *ip);

#endif /* __CHECKSUM_H__ */
