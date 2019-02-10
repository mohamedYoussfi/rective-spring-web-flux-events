package org.sid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@RestController
public class EventReactiveRestAPI {
    @GetMapping(value = "/streamEvents/{id}",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Event> listEvenets(@PathVariable  String id){
        Flux<Long> interval=Flux.interval(Duration.ofMillis(1000));
        Flux<Event> events=Flux.fromStream(Stream.generate(()->{
            Event event=new Event();
            event.setInstant(Instant.now());
            event.setSocieteID(id);
            event.setValue(100+Math.random()*1000);
            return event;
        }));

        return Flux.zip(interval,events)
                .map(data->{
                   return data.getT2();
                });
    }

}

class Event{
    private Instant instant;
    private double value;
    private String societeID;

    public Instant getInstant() {
        return instant;
    }

    public double getValue() {
        return value;
    }

    public String getSocieteID() {
        return societeID;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setSocieteID(String societeID) {
        this.societeID = societeID;
    }
}