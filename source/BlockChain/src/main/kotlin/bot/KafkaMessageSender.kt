package bot

import org.springframework.kafka.core.KafkaTemplate
import java.util.UUID

class KafkaMessageSender(
    private val kafkaTemplate: KafkaTemplate<UUID, UserTransaction>,
    private val kafkaProperties: KafkaProperties
) {

    fun sendTransactionRequest(userTransaction: UserTransaction) {
        val future = kafkaTemplate.send(
            kafkaProperties.topic,
            UUID.randomUUID(),
            userTransaction
        )

        kafkaTemplate.flush()
    }

}
