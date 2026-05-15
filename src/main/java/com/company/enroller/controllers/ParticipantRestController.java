package com.company.enroller.controllers;

import com.company.enroller.model.Participant;
import com.company.enroller.persistence.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/participants")
public class ParticipantRestController {

    @Autowired
    ParticipantService participantService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getParticipants(
            @RequestParam(value = "sortBy", defaultValue = "") String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") String sortOrder,
            @RequestParam(value = "key", defaultValue = "") String key) {

        Collection<Participant> participants =
                participantService.getAll(sortBy, sortOrder, key);

        return new ResponseEntity<>(participants, HttpStatus.OK);
    }

    // participants/{id}
    @RequestMapping(value = "/{login}", method = RequestMethod.GET)
    public ResponseEntity<?> getParticipant(@PathVariable("login") String login) {
        Participant participant = participantService.findByLogin(login);

        if (participant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(participant, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> addParticipant(@Valid @RequestBody Participant participant) {
        if (participantService.findByLogin(participant.getLogin()) != null) {
            return new ResponseEntity<String>(
                    "Unable to create. A participant with login " + participant.getLogin() + " already exist.",
                    HttpStatus.CONFLICT);
        }

        //szyfrowanie hasła
        String hashedPassword = passwordEncoder.encode(participant.getPassword());
        participant.setPassword(hashedPassword);

        participantService.add(participant);

        return new ResponseEntity<Participant>(participant, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{login}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("login") String login) {

        Participant participant = participantService.findByLogin(login);
        if (participant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        participantService.delete(participant);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{login}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(
            @PathVariable("login") String login,
            @Valid @RequestBody Participant updatedParticipant) {

        Participant participant = participantService.findByLogin(login);
        if (participant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        participant.setPassword(
                passwordEncoder.encode(updatedParticipant.getPassword())
        );
        participantService.update(participant);

        return new ResponseEntity<>(participant, HttpStatus.OK);
    }
}
