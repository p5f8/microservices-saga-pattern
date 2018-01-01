package kz.jug.saga.sail.boundary;

import kz.jug.saga.sail.entity.EquipmentType;
import kz.jug.saga.sail.entity.SailType;
import kz.jug.saga.sail.entity.ValkyrieType;
import lombok.Data;

@Data
public class SailRequest {
    private int vikingsNumber;
    private SailType sailType;
    private EquipmentType equipment;
    private ValkyrieType valkyrieType;
}
