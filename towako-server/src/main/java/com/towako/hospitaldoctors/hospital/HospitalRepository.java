package com.towako.hospitaldoctors.hospital;

import com.cartisan.repositories.BaseRepository;

import java.util.Collection;
import java.util.List;

public interface HospitalRepository extends BaseRepository<Hospital, Long> {
    List<Hospital> findByIdIn(List<Long> hospitalIds);
}
