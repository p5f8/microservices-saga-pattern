package kz.jug.saga.sail.control;

import kz.jug.saga.sail.entity.events.incoming.SailFightResultEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SailFightService {
    private final SailRepository sailRepository;
    private final SailSagaTransactions sailSagaTransactions;

    public void processFightResult(SailFightResultEvent event) {
        val sail = this.sailRepository.findByTransactionId(event.getTransactionId());
        this.sailSagaTransactions.attachTo(sail);
        sail.completeFight(event);
        sail.cleanSteps();
        this.sailRepository.save(sail);
    }
}
