package kz.jug.saga.sail.control

import kz.jug.saga.sail.entity.EquipmentType
import kz.jug.saga.sail.entity.Sail
import kz.jug.saga.sail.entity.SailStep
import kz.jug.saga.sail.entity.SailType
import kz.jug.saga.sail.entity.ValkyrieType
import kz.jug.saga.sail.entity.events.incoming.SailFightResultEvent
import kz.jug.saga.sail.entity.saga.StepAction
import spock.lang.Specification

import java.util.function.Consumer

class SailFightServiceSpec extends Specification {

    def repo = Mock(SailRepository)
    def sailSagaTransactions = Mock(SailSagaTransactions)
    def service = new SailFightService(repo, sailSagaTransactions)
    def transaction = Mock(Consumer)


    def "should save fight result in repository"() {
        given: "fight is over and result is returned back"
        def transactionId = "SA-855e57P"
        def sailFightEvent = new SailFightResultEvent(transactionId: transactionId)

        and: "a sail that is saved in the repository"
        def sail = new Sail(transactionId: transactionId)
        repo.findByTransactionId(transactionId) >> sail

        and: "transactions must be prepared for the saga"
        sailSagaTransactions.attachTo(_ as Sail) >> { arguments ->
            def saga = arguments[0] as Sail
            def action = new StepAction<Sail>(new FakeAction(), new FakeAction())
            saga.addStep(SailStep.VALKIRIA_REQUESTED, action)
            saga
        }

        when: "when fight result is returned"
        service.processFightResult(sailFightEvent)

        then: "it must be saved in the repository"
        1 * repo.save(*_) >> { arguments ->
            def sailSaga = arguments[0] as Sail
            assert sailSaga.step == SailStep.VALKIRIA_REQUESTED
        }
    }
}
