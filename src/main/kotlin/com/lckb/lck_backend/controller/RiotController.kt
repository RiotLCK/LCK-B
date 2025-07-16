package com.lckb.lck_backend.controller

import com.lckb.lck_backend.domain.*
import com.lckb.lck_backend.service.RiotService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/riot")
class RiotController(
    private val riotService: RiotService
) {

    @GetMapping("/summoner")
    fun getSummoner(@RequestParam summonerName: String): SummonerRequest {
        return riotService.getSummonerByName(summonerName)
    }

    @GetMapping("/matches")
    fun getMatchIds(@RequestParam puuid: String): List<String> {
        return riotService.getMatchIdsByPuuid(puuid)
    }

    @GetMapping("/match")
    fun getMatchDetail(@RequestParam matchId: String): MatchDetailRequest {
        return riotService.getMatchDetail(matchId)
    }
}
