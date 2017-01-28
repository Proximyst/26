package com.proximyst.fe26

import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.economy.EconomyResponse
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import java.text.DecimalFormat
import java.util.*

class EconHandler(val main: Fe26) : Economy {
    override fun getBalance(player: OfflinePlayer): Double =
            main.accounts[player.uniqueId] ?: main.config["starting-balance"] as Double

    @Suppress("DEPRECATION")
    @Deprecated("Use #getBalance(OfflinePlayer) instead", ReplaceWith("getBalance(OfflinePlayer)"))
    override fun getBalance(playerName: String): Double =
            getBalance(Bukkit.getOfflinePlayer(playerName))

    @Suppress("DEPRECATION")
    @Deprecated("Use #getBalance(OfflinePlayer) instead.", ReplaceWith("getBalance(OfflinePlayer)"))
    override fun getBalance(playerName: String, world: String?): Double =
            getBalance(playerName)

    @Deprecated("Use #getBalance(OfflinePlayer) instead.", ReplaceWith("getBalance(OfflinePlayer)"))
    override fun getBalance(player: OfflinePlayer, world: String?): Double =
            getBalance(player)

    override fun has(player: OfflinePlayer, amount: Double): Boolean =
            main.accounts[player.uniqueId] ?: 0.0 >= amount

    @Suppress("DEPRECATION")
    @Deprecated("Use #has(OfflinePlayer, Double) instead.", ReplaceWith("has(OfflinePlayer, Double)"))
    override fun has(playerName: String, amount: Double): Boolean =
            has(Bukkit.getOfflinePlayer(playerName), amount)

    @Suppress("DEPRECATION")
    @Deprecated("Use #has(OfflinePlayer, Double) instead.", ReplaceWith("has(OfflinePlayer, Double)"))
    override fun has(playerName: String, world: String?, amount: Double): Boolean =
            has(playerName, amount)

    @Deprecated("Use #has(OfflinePlayer, Double) instead.", ReplaceWith("has(OfflinePlayer, Double)"))
    override fun has(player: OfflinePlayer, world: String?, amount: Double): Boolean =
            has(player, amount)

    override fun depositPlayer(player: OfflinePlayer, amount: Double): EconomyResponse {
        val amt = Math.abs(amount)
        var new = getBalance(player) + amt
        if (new > Double.MAX_VALUE)
            new = Double.MAX_VALUE
        main.accounts[player.uniqueId] = new
        return EconomyResponse(amt, new, EconomyResponse.ResponseType.SUCCESS, "")
    }

    @Suppress("DEPRECATION")
    @Deprecated("Use #depositPlayer(OfflinePlayer, Double) instead.", ReplaceWith("depositPlayer(OfflinePlayer, Double)"))
    override fun depositPlayer(playerName: String, amount: Double): EconomyResponse =
            depositPlayer(Bukkit.getOfflinePlayer(playerName), amount)

    @Suppress("DEPRECATION")
    @Deprecated("Use #depositPlayer(OfflinePlayer, Double) instead.", ReplaceWith("depositPlayer(OfflinePlayer, Double)"))
    override fun depositPlayer(playerName: String, world: String?, amount: Double): EconomyResponse =
            depositPlayer(playerName, amount)

    @Suppress("DEPRECATION")
    @Deprecated("Use #depositPlayer(OfflinePlayer, Double) instead.", ReplaceWith("depositPlayer(OfflinePlayer, Double)"))
    override fun depositPlayer(player: OfflinePlayer, world: String?, amount: Double): EconomyResponse =
            depositPlayer(player, amount)

    override fun hasAccount(player: OfflinePlayer): Boolean =
            main.accounts[player.uniqueId] != null

    @Suppress("DEPRECATION")
    @Deprecated("Use #hasAccount(OfflinePlayer) instead.", ReplaceWith("hasAccount(OfflinePlayer)"))
    override fun hasAccount(playerName: String): Boolean =
            hasAccount(Bukkit.getOfflinePlayer(playerName))

    @Suppress("DEPRECATION")
    @Deprecated("Use #hasAccount(OfflinePlayer) instead.", ReplaceWith("hasAccount(OfflinePlayer)"))
    override fun hasAccount(playerName: String, world: String?): Boolean =
            hasAccount(Bukkit.getOfflinePlayer(playerName))

    @Suppress("DEPRECATION")
    @Deprecated("Use #hasAccount(OfflinePlayer) instead.", ReplaceWith("hasAccount(OfflinePlayer)"))
    override fun hasAccount(player: OfflinePlayer, world: String?): Boolean =
            hasAccount(player)

    override fun createPlayerAccount(player: OfflinePlayer): Boolean {
        if (hasAccount(player))
            return false
        main.accounts[player.uniqueId] = main.config["starting-balance"] as Double
        return true
    }

    @Suppress("DEPRECATION")
    @Deprecated("Use #createPlayerAccount(OfflinePlayer) instead.", ReplaceWith("createPlayerAccount(OfflinePlayer)"))
    override fun createPlayerAccount(playerName: String): Boolean =
            createPlayerAccount(Bukkit.getOfflinePlayer(playerName))

    @Suppress("DEPRECATION")
    @Deprecated("Use #createPlayerAccount(OfflinePlayer) instead.", ReplaceWith("createPlayerAccount(OfflinePlayer)"))
    override fun createPlayerAccount(playerName: String, world: String?): Boolean =
            createPlayerAccount(playerName)

    @Suppress("DEPRECATION")
    @Deprecated("Use #createPlayerAccount(OfflinePlayer) instead.", ReplaceWith("createPlayerAccount(OfflinePlayer)"))
    override fun createPlayerAccount(player: OfflinePlayer, world: String?): Boolean =
            createPlayerAccount(player)

    override fun withdrawPlayer(player: OfflinePlayer, amount: Double): EconomyResponse {
        val amt = Math.abs(amount)
        var new = getBalance(player) - amt
        if (new < Double.MIN_VALUE)
            new = Double.MIN_VALUE
        main.accounts[player.uniqueId] = new
        return EconomyResponse(amt, new, EconomyResponse.ResponseType.SUCCESS, "")
    }

    @Suppress("DEPRECATION")
    @Deprecated("Use #withdrawPlayer(OfflinePlayer, Double) instead.", ReplaceWith("withdrawPlayer(OfflinePlayer, Double)"))
    override fun withdrawPlayer(playerName: String, amount: Double): EconomyResponse =
            withdrawPlayer(Bukkit.getOfflinePlayer(playerName), amount)

    @Suppress("DEPRECATION")
    @Deprecated("Use #withdrawPlayer(OfflinePlayer, Double) instead.", ReplaceWith("withdrawPlayer(OfflinePlayer, Double)"))
    override fun withdrawPlayer(playerName: String, world: String?, amount: Double): EconomyResponse =
            withdrawPlayer(playerName, amount)

    @Suppress("DEPRECATION")
    @Deprecated("Use #withdrawPlayer(OfflinePlayer, Double) instead.", ReplaceWith("withdrawPlayer(OfflinePlayer, Double)"))
    override fun withdrawPlayer(player: OfflinePlayer, world: String?, amount: Double): EconomyResponse =
            withdrawPlayer(player, amount)

    override fun currencyNamePlural(): String = "coins"

    override fun currencyNameSingular(): String = "coin"

    override fun getName(): String =
            "Fe 26"

    override fun isEnabled(): Boolean = main.isEnabled

    override fun fractionalDigits(): Int = 2

    override fun format(amount: Double): String =
            numberFormat.format(amount)

    val numberFormat: DecimalFormat = DecimalFormat("#,###.00")

    @Deprecated("26 doesn't support banks.", ReplaceWith(""), DeprecationLevel.ERROR)
    override fun bankDeposit(bank: String, amount: Double): EconomyResponse =
            EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "26 doesn't support banks.")

    @Deprecated("26 doesn't support banks.", ReplaceWith(""), DeprecationLevel.ERROR)
    override fun bankWithdraw(bank: String, amount: Double): EconomyResponse =
            EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "26 doesn't support banks.")

    @Deprecated("26 doesn't support banks.", ReplaceWith(""), DeprecationLevel.ERROR)
    override fun deleteBank(bank: String): EconomyResponse =
            EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "26 doesn't support banks.")

    @Deprecated("26 doesn't support banks.", ReplaceWith(""), DeprecationLevel.ERROR)
    override fun createBank(bank: String, owner: String): EconomyResponse =
            EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "26 doesn't support banks.")

    @Deprecated("26 doesn't support banks.", ReplaceWith(""), DeprecationLevel.ERROR)
    override fun createBank(bank: String, owner: OfflinePlayer): EconomyResponse =
            EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "26 doesn't support banks.")

    @Deprecated("26 doesn't support banks.", ReplaceWith(""), DeprecationLevel.ERROR)
    override fun isBankMember(bank: String, playerName: String): EconomyResponse =
            EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "26 doesn't support banks.")

    @Deprecated("26 doesn't support banks.", ReplaceWith(""), DeprecationLevel.ERROR)
    override fun isBankMember(bank: String, player: OfflinePlayer): EconomyResponse =
            EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "26 doesn't support banks.")

    @Deprecated("26 doesn't support banks.", ReplaceWith(""), DeprecationLevel.ERROR)
    override fun isBankOwner(bank: String, playerName: String): EconomyResponse =
            EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "26 doesn't support banks.")

    @Deprecated("26 doesn't support banks.", ReplaceWith(""), DeprecationLevel.ERROR)
    override fun isBankOwner(bank: String, player: OfflinePlayer): EconomyResponse =
            EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "26 doesn't support banks.")

    @Deprecated("26 doesn't support banks.", ReplaceWith(""), DeprecationLevel.ERROR)
    override fun getBanks(): MutableList<String> =
            ArrayList()

    @Deprecated("26 doesn't support banks.", ReplaceWith(""), DeprecationLevel.ERROR)
    override fun bankHas(bank: String, amount: Double): EconomyResponse =
            EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "26 doesn't support banks.")

    @Deprecated("26 doesn't support banks.", ReplaceWith(""), DeprecationLevel.ERROR)
    override fun bankBalance(bank: String): EconomyResponse =
            EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "26 doesn't support banks.")

    override fun hasBankSupport(): Boolean = false
}