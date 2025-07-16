package com.lckb.lck_backend.service

import com.lckb.lck_backend.domain.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class RiotService(
    private val restTemplate: RestTemplate,
    @Value("\${riot.api-key}")
    private val apiKey: String
) {

    fun getSummonerByName(summonerName: String): SummonerRequest {
        val url = UriComponentsBuilder
            .fromHttpUrl("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/$summonerName")
            .queryParam("api_key", apiKey)
            .toUriString()

        return restTemplate.getForObject(url, SummonerRequest::class.java)
            ?: throw RuntimeException("소환사 정보를 찾을 수 없습니다.")
    }

    fun getMatchIdsByPuuid(puuid: String): List<String> {
        val url = UriComponentsBuilder
            .fromHttpUrl("https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/$puuid/ids")
            .queryParam("start", 0)
            .queryParam("count", 10)
            .queryParam("api_key", apiKey)
            .toUriString()

        return restTemplate.getForObject(url, Array<String>::class.java)?.toList()
            ?: emptyList()
    }

    fun getMatchDetail(matchId: String): MatchDetailRequest {
        val url = UriComponentsBuilder
            .fromHttpUrl("https://asia.api.riotgames.com/lol/match/v5/matches/$matchId")
            .queryParam("api_key", apiKey)
            .toUriString()

        return restTemplate.getForObject(url, MatchDetailRequest::class.java)
            ?: throw RuntimeException("매치 상세 정보를 찾을 수 없습니다.")
    }
}
