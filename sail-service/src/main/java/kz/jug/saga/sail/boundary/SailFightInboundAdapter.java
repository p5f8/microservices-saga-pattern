package kz.jug.saga.sail.boundary;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.jug.saga.sail.control.SailFightService;
import kz.jug.saga.sail.entity.events.incoming.SailFightResultEvent;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SailFightInboundAdapter {
    private final SailFightService sailFightService;
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @JmsListener(destination = "${activemq.queue.fight.reply}")
    public void receive(String message) {
        val event = extractEvent(message);
        this.sailFightService.processFightResult(event);
    }

    private SailFightResultEvent extractEvent(String message) {
        try {
            return OBJECT_MAPPER.readValue(message, SailFightResultEvent.class);
        } catch (IOException e) {
            throw new EventExtractionException("SailFightResultEvent cannot be extracted", e);
        }
    }
}
