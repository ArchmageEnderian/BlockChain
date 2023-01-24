package blockchain

import org.springframework.boot.context.properties.ConfigurationProperties
import java.math.BigInteger

@ConfigurationProperties(prefix = "eth")
class EthereumProperties(
    val gasPrice: BigInteger,
    val gasLimit: BigInteger
)
