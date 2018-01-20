package com.komarek.rabbitmq.rabbit.club;

import com.komarek.rabbitmq.rabbit.domain.Bookmark;
import com.komarek.rabbitmq.rabbit.domain.BookmarkEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClubService {
    @Autowired
    private ClubRepository repository;

    public List<Club> getAllClubs() {
        List<Club> clubs = new ArrayList<>();
        repository.findAll().forEach(clubs::add);
        return clubs;
    }

    public Club getClub(String id) {
        return repository.findById(id);
    }

    public void addClub(Club club) {
        repository.save(club);
    }

    public void addClubData(String id, ClubData data) {
        Club club = repository.findById(id);
        club.setData(data);
        repository.save(club);
    }

    public Set<Club> getAllClubsByName(String name) {
        new BookmarkEventHandler().handleAfterBookmarkCreate(new Bookmark() {{
            setName(name);
        }});
        List<Club> clubs = new ArrayList<>();
        repository.findAllByName(name).forEach(clubs::add);
        return new HashSet<Club>(clubs);
    }
}
