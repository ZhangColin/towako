package com.towako.channel.familyhotel;

import com.cartisan.repositories.BaseRepository;

public interface FamilyHotelRepository extends BaseRepository<FamilyHotel, Long> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
}
