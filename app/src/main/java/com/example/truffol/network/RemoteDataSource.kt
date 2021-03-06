package com.example.truffol.network

import com.example.truffol.domain.model.Shop
import com.example.truffol.domain.model.Truffle
import com.example.truffol.network.model.ShopDtoMapper
import com.example.truffol.network.model.TruffleDtoMapper
import timber.log.Timber
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val truffleService: TruffleService,
    private val shopService: ShopService,
    private val shopDtoMapper: ShopDtoMapper,
    private val truffleDtoMapper: TruffleDtoMapper
) {

    suspend fun getTruffleList() : List<Truffle> {
        var truffleList : List<Truffle>? = null
        try {
            truffleList = truffleService.getTruffleList().body()?.let {
                truffleDtoMapper.toDomainList(it)
            }
        }
        catch (error : Error ){
            Timber.e(error)
        }
        return truffleList!!
    }

    suspend fun getTruffleDetail(truffleId : Int): Truffle {
        var truffleDetail : Truffle? = null
        try {
            truffleDetail = truffleService.getTruffleDetail(truffleId).body()?.let {
                truffleDtoMapper.mapToDomainModel(it)
            }
        }
        catch (error : Error ){
            Timber.e(error)
        }
        return truffleDetail!!
    }

    suspend fun getShopList(): List<Shop> {
        var shopList : List<Shop>? = null
        try {
            shopList = shopService.getShopList().body()?.let {
                shopDtoMapper.toDomainList(it)
            }
        }
        catch (error : Error){
            Timber.e(error)
        }
        return shopList!!
    }

    suspend fun getShopDetail(shopId: Int): Shop {
        var shopDetail : Shop? = null
        try {
            shopDetail = shopService.getShopDetail(shopId).body()?.let {
                shopDtoMapper.mapToDomainModel(it)
            }
        }
        catch (error : Error){
            Timber.e(error)
        }
        return shopDetail!!
    }

}