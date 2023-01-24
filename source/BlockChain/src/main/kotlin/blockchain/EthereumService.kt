package blockchain

import org.web3j.crypto.Credentials
import org.web3j.crypto.RawTransaction
import org.web3j.crypto.TransactionEncoder
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.utils.Numeric
import java.math.BigInteger
import java.util.concurrent.CompletableFuture

class EthereumService(
    private val web3j: Web3j,
    private val ethereumProperties: EthereumProperties
) {

    fun sendETH(from: Credentials, toAddress: String, amountWei: BigInteger): CompletableFuture<String> {
        val nonce = getTransactionCount(from)
        val rawTransaction = RawTransaction.createEtherTransaction(nonce, ethereumProperties.gasPrice, ethereumProperties.gasLimit, toAddress, amountWei)
        val hexSingedMessage = signTransaction(rawTransaction, from)

        return web3j.ethSendRawTransaction(
            hexSingedMessage
        ).sendAsync().thenApply { transaction -> transaction.transactionHash }
    }

    private fun getTransactionCount(from: Credentials): BigInteger {
        val ethGetTransactionCount = web3j.ethGetTransactionCount(from.address, DefaultBlockParameterName.LATEST)
            .send()
        return ethGetTransactionCount.transactionCount
    }

    private fun signTransaction(rawTransaction: RawTransaction, from: Credentials): String {
        val signedMessage: ByteArray =
            TransactionEncoder.signMessage(rawTransaction, from)
        return Numeric.toHexString(signedMessage)
    }

}