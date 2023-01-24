package bot

import dev.inmo.tgbotapi.types.Identifier
import java.math.BigInteger

data class UserTransaction(
    val operation: Operation,
    val userId: Identifier,
    val amount: BigInteger?,
    val metadata: Map<String, String>
)
