package com.company.enroller.controllers;


import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import com.company.enroller.persistence.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/meetings")
public class MeetingRestController {


    // po nazwie klasy powiązane (Spring)
    @Autowired
    MeetingService meetingService;

    @Autowired
    ParticipantService participantService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getMeetings() {
        Collection<Meeting> meetings = meetingService.getAll();
        return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getMeeting(@PathVariable("id") long id) {
        Meeting meetings = meetingService.findById(id);
        if (meetings == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Meeting>(meetings, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> registerMeeting(@RequestBody Meeting meeting) {

        Meeting foundMeeting = meetingService.findById(meeting.getId());

        if (foundMeeting != null) {
            return new ResponseEntity<>("Already exists", HttpStatus.CONFLICT);
        }
        meetingService.add(meeting);
        return new ResponseEntity<Meeting>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeMeeting(
            @PathVariable("id") long id) {

        Meeting foundMeeting = meetingService.findById(id);

        if (foundMeeting == null) {
            return new ResponseEntity<>("Not found.",
                    HttpStatus.NOT_FOUND);
        }

        meetingService.delete(foundMeeting);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> getMeeting(
            @PathVariable("id") long id,
            @RequestBody Meeting meeting) {

        Meeting foundMeeting = meetingService.findById(id);

        if (foundMeeting == null) {
            return new ResponseEntity("Not found", HttpStatus.NOT_FOUND);
        }

        meetingService.update(meeting);
        return new ResponseEntity<Meeting>(foundMeeting, HttpStatus.OK);
    }


    // GET meetings/{id}/participants
    @RequestMapping(value = "/{id}/participants", method = RequestMethod.GET)
    public ResponseEntity<?> getParticipants(@PathVariable("id") long id) {
        Meeting meeting = meetingService.findById(id);
        if (meeting == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(meeting.getParticipants(), HttpStatus.OK);
    }


    // POST /meetings/{id}/participants - dodaje uczestnika do spotkania

    @RequestMapping(value = "/{id}/participants", method = RequestMethod.POST)
    public ResponseEntity<?> registerMeetingsParticipants(
            @PathVariable("id") long id,
            @RequestBody Participant participant) {

        Meeting foundMeeting = meetingService.findById(id);

        if (foundMeeting == null) {
            return new ResponseEntity<>("Not found",
                    HttpStatus.NOT_FOUND);
        }
        foundMeeting.getParticipants().add(participant);
        meetingService.update(foundMeeting);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    // TODO: nowsze
    // POST meetings/{id}/participants
/*    @RequestMapping(value = "/{id}/participants", method = RequestMethod.POST)
    public ResponseEntity<?> addParticipant(
            @PathVariable("id") long id,
            @RequestBody Participant participantRequest) {

        Meeting meeting = meetingService.findById(id);
        if (meeting == null) {
            return new ResponseEntity<>("Meeting not found", HttpStatus.NOT_FOUND);
        }

        Participant participant = participantService.findByLogin(participantRequest.getLogin());
        if (participant == null) {
            return new ResponseEntity<>("Participant not found", HttpStatus.NOT_FOUND);
        }

        meeting.addParticipant(participant);
        meetingService.update(meeting);

        return new ResponseEntity<>(participant, HttpStatus.CREATED);
    }*/

    // DELETE meetings/{id}/participants/{login}
    @RequestMapping(value = "/{id}/participants/{login}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeParticipant(
            @PathVariable("id") long id,
            @PathVariable("login") String login) {

        Meeting meeting = meetingService.findById(id);
        if (meeting == null) {
            return new ResponseEntity<>("Meeting not found", HttpStatus.NOT_FOUND);
        }

        Participant participant = participantService.findByLogin(login);
        if (participant == null) {
            return new ResponseEntity<>("Participant not found", HttpStatus.NOT_FOUND);
        }

        if (!meeting.getParticipants().contains(participant)) {
            return new ResponseEntity<>("Participant not registered in this meeting", HttpStatus.CONFLICT);
        }

        meeting.removeParticipant(participant);
        meetingService.update(meeting);

        return new ResponseEntity<>(HttpStatus.OK);
    }



}
