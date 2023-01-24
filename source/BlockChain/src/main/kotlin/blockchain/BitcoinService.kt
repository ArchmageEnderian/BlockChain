package blockchain

import org.bitcoinj.core.Address
import org.bitcoinj.core.Coin
import org.bitcoinj.core.InsufficientMoneyException
import org.bitcoinj.core.PeerGroup
import org.bitcoinj.core.Transaction
import org.bitcoinj.wallet.SendRequest
import org.bitcoinj.wallet.Wallet
import java.util.concurrent.Future

class BitcoinService(
    private val peerGroup: PeerGroup
) {

    fun sendBitcoins(fromWallet: Wallet, to: Address, amount: Coin): Future<Transaction> {
        val sendRequest = SendRequest.to(to, amount)
        try {
            val sendingResult = fromWallet.sendCoins(this.peerGroup, sendRequest)
            return sendingResult.broadcastComplete
        } catch (insufficientMoneyException: InsufficientMoneyException) {
            throw IllegalStateException("Not enough money to perform transaction", insufficientMoneyException)
        } catch (exception: Exception) { // Остальные проблемы с созданием транзакции
            throw BitcoinCommunicationException("Exception while creating transaction")
        }
    }


}