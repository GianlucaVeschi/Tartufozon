package com.example.truffol.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.truffol.db.model.ShopEntity

@Dao
interface ShopDao {

    @Insert
    suspend fun insertShop(shop: ShopEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertShops(shops: List<ShopEntity>): LongArray

    @Query("SELECT * FROM shops")
    suspend fun getAllShops(): List<ShopEntity>

    @Query("SELECT * FROM shops WHERE id = :id")
    suspend fun getShopById(id: Int): ShopEntity?

    @Query("DELETE FROM shops WHERE id IN (:ids)")
    suspend fun deleteShops(ids: List<Int>): Int

    @Query("DELETE FROM shops")
    suspend fun deleteAllShops()

    @Query("DELETE FROM shops WHERE id = :primaryKey")
    suspend fun deleteShop(primaryKey: Int): Int

}