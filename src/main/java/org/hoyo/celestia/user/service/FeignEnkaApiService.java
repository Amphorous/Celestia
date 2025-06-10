package org.hoyo.celestia.user.service;

import feign.Param;
import feign.RequestLine;
import org.hoyo.celestia.user.model.User;

interface FeignEnkaApiService {
    @RequestLine("GET /api/hsr/uid/{uid}")
    User getPlayerData(@Param("uid") String uid);
}
