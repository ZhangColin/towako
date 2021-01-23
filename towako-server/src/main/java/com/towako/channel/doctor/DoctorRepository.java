package com.towako.channel.doctor;

import com.cartisan.repositories.BaseRepository;

public interface DoctorRepository extends BaseRepository<Doctor, Long> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
}
