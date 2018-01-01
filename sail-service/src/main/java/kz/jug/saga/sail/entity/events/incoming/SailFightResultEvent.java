package kz.jug.saga.sail.entity.events.incoming;

import kz.jug.saga.sail.entity.events.AbstractEvent;
import kz.jug.saga.sail.entity.events.ApprovalStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SailFightResultEvent extends AbstractEvent {
    private ApprovalStatus approvalStatus;
    private List<Long> survivedVikings = new ArrayList<>();

    public SailFightResultEvent(String transactionId, List<Long> survivedVikings, ApprovalStatus approvalStatus) {
        super(transactionId);
        this.survivedVikings = survivedVikings;
        this.approvalStatus = approvalStatus;
    }
}
