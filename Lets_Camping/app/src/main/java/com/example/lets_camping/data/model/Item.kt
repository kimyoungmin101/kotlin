package com.example.lets_camping.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Item(
    @Json(name = "addr1")
    val addr1: String?,
    @Json(name = "addr2")
    val addr2: String?,
    @Json(name = "allar")
    val allar: Int?,
    @Json(name = "animalCmgCl")
    val animalCmgCl: String?,
    @Json(name = "autoSiteCo")
    val autoSiteCo: Int?,
    @Json(name = "bizrno")
    val bizrno: String?,
    @Json(name = "brazierCl")
    val brazierCl: String?,
    @Json(name = "caravAcmpnyAt")
    val caravAcmpnyAt: String?,
    @Json(name = "caravInnerFclty")
    val caravInnerFclty: String?,
    @Json(name = "caravSiteCo")
    val caravSiteCo: Int?,
    @Json(name = "clturEvent")
    val clturEvent: String?,
    @Json(name = "clturEventAt")
    val clturEventAt: String?,
    @Json(name = "contentId")
    val contentId: Int?,
    @Json(name = "createdtime")
    val createdtime: String?,
    @Json(name = "direction")
    val direction: String?,
    @Json(name = "doNm")
    val doNm: String?,
    @Json(name = "eqpmnLendCl")
    val eqpmnLendCl: String?,
    @Json(name = "exprnProgrm")
    val exprnProgrm: String?,
    @Json(name = "exprnProgrmAt")
    val exprnProgrmAt: String?,
    @Json(name = "extshrCo")
    val extshrCo: Int?,
    @Json(name = "facltDivNm")
    val facltDivNm: String?,
    @Json(name = "facltNm")
    val facltNm: String?,
    @Json(name = "featureNm")
    val featureNm: String?,
    @Json(name = "fireSensorCo")
    val fireSensorCo: Int?,
    @Json(name = "firstImageUrl")
    val firstImageUrl: String?,
    @Json(name = "frprvtSandCo")
    val frprvtSandCo: Int?,
    @Json(name = "frprvtWrppCo")
    val frprvtWrppCo: Int?,
    @Json(name = "glampInnerFclty")
    val glampInnerFclty: String?,
    @Json(name = "glampSiteCo")
    val glampSiteCo: Int?,
    @Json(name = "gnrlSiteCo")
    val gnrlSiteCo: Int?,
    @Json(name = "homepage")
    val homepage: String?,
    @Json(name = "hvofBgnde")
    val hvofBgnde: String?,
    @Json(name = "hvofEnddle")
    val hvofEnddle: String?,
    @Json(name = "induty")
    val induty: String?,
    @Json(name = "indvdlCaravSiteCo")
    val indvdlCaravSiteCo: Int?,
    @Json(name = "insrncAt")
    val insrncAt: String?,
    @Json(name = "intro")
    val intro: String?,
    @Json(name = "lctCl")
    val lctCl: String?,
    @Json(name = "lineIntro")
    val lineIntro: String?,
    @Json(name = "manageNmpr")
    val manageNmpr: Int?,
    @Json(name = "manageSttus")
    val manageSttus: String?,
    @Json(name = "mangeDivNm")
    val mangeDivNm: String?,
    @Json(name = "mapX")
    val mapX: Double?,
    @Json(name = "mapY")
    val mapY: Double?,
    @Json(name = "mgcDiv")
    val mgcDiv: String?,
    @Json(name = "modifiedtime")
    val modifiedtime: String?,
    @Json(name = "operDeCl")
    val operDeCl: String?,
    @Json(name = "operPdCl")
    val operPdCl: String?,
    @Json(name = "posblFcltyCl")
    val posblFcltyCl: String?,
    @Json(name = "posblFcltyEtc")
    val posblFcltyEtc: String?,
    @Json(name = "prmisnDe")
    val prmisnDe: String?,
    @Json(name = "resveCl")
    val resveCl: String?,
    @Json(name = "resveUrl")
    val resveUrl: String?,
    @Json(name = "sbrsCl")
    val sbrsCl: String?,
    @Json(name = "sbrsEtc")
    val sbrsEtc: String?,
    @Json(name = "sigunguNm")
    val sigunguNm: String?,
    @Json(name = "siteBottomCl1")
    val siteBottomCl1: Int?,
    @Json(name = "siteBottomCl2")
    val siteBottomCl2: Int?,
    @Json(name = "siteBottomCl3")
    val siteBottomCl3: Int?,
    @Json(name = "siteBottomCl4")
    val siteBottomCl4: Int?,
    @Json(name = "siteBottomCl5")
    val siteBottomCl5: Int?,
    @Json(name = "siteMg1Co")
    val siteMg1Co: Int?,
    @Json(name = "siteMg1Vrticl")
    val siteMg1Vrticl: Int?,
    @Json(name = "siteMg1Width")
    val siteMg1Width: Int?,
    @Json(name = "siteMg2Co")
    val siteMg2Co: Int?,
    @Json(name = "siteMg2Vrticl")
    val siteMg2Vrticl: Int?,
    @Json(name = "siteMg2Width")
    val siteMg2Width: Int?,
    @Json(name = "siteMg3Co")
    val siteMg3Co: Int?,
    @Json(name = "siteMg3Vrticl")
    val siteMg3Vrticl: Int?,
    @Json(name = "siteMg3Width")
    val siteMg3Width: Int?,
    @Json(name = "sitedStnc")
    val sitedStnc: Int?,
    @Json(name = "swrmCo")
    val swrmCo: Int?,
    @Json(name = "tel")
    val tel: String?,
    @Json(name = "themaEnvrnCl")
    val themaEnvrnCl: String?,
    @Json(name = "toiletCo")
    val toiletCo: Int?,
    @Json(name = "tooltip")
    val tooltip: String?,
    @Json(name = "tourEraCl")
    val tourEraCl: String?,
    @Json(name = "trlerAcmpnyAt")
    val trlerAcmpnyAt: String?,
    @Json(name = "trsagntNo")
    val trsagntNo: String?,
    @Json(name = "wtrplCo")
    val wtrplCo: Int?,
    @Json(name = "zipcode")
    val zipcode: Int?
)