package com.nuevafrecuencia.patria5kweb.repository;

import com.nuevafrecuencia.patria5kweb.model.Participant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findAllByOrderByIdAsc();

    @Query("""
        select p from Participant p
        where lower(coalesce(p.bibNumber, '')) like lower(concat('%', :q, '%'))
           or lower(p.name) like lower(concat('%', :q, '%'))
           or lower(p.municipality) like lower(concat('%', :q, '%'))
           or lower(p.phone) like lower(concat('%', :q, '%'))
        order by p.id asc
        """)
    List<Participant> search(@Param("q") String query);
}
