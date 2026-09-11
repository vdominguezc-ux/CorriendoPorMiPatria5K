package com.nuevafrecuencia.patria5kweb.service;

import com.nuevafrecuencia.patria5kweb.model.Participant;
import com.nuevafrecuencia.patria5kweb.repository.ParticipantRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ParticipantService {
    private final ParticipantRepository repository;

    public ParticipantService(ParticipantRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Participant register(Participant participant) {
        participant.setId(null);
        participant.setBibNumber(null);
        Participant saved = repository.saveAndFlush(participant);
        saved.setBibNumber(String.format("PATRIA-%04d", saved.getId()));
        return repository.save(saved);
    }

    public List<Participant> list(String query) {
        if (query == null || query.isBlank()) return repository.findAllByOrderByIdAsc();
        return repository.search(query.trim());
    }

    public Participant get(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Participante no encontrado"));
    }

    @Transactional
    public Participant update(Long id, Participant edited) {
        Participant current = get(id);
        current.setName(edited.getName().trim());
        current.setAge(edited.getAge());
        current.setMunicipality(edited.getMunicipality().trim());
        current.setPhone(edited.getPhone().trim());
        return repository.save(current);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public long count() {
        return repository.count();
    }
}
