package com.lckb.lck_backend.domain

data class MatchDetailRequest(
    val metadata: Metadata,
    val info: MatchInfo
)

data class Metadata(
    val matchId: String,
    val participants: List<String>
)

data class MatchInfo(
    val gameDuration: Long,
    val gameCreation: Long,
    val participants: List<Participant>
)

data class Participant(
    val puuid: String,
    val summonerName: String,
    val championName: String,
    val kills: Int,
    val deaths: Int,
    val assists: Int,
    val win: Boolean
)
