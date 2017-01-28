package com.proximyst.fe26

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import java.util.*

class Fe26 : JavaPlugin() {
    val config: MutableMap<String, Any?> = HashMap()
    val accounts: MutableMap<UUID, Double> = HashMap()
    lateinit var configFile: File
    lateinit var accountsDir: File
    val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    override fun onEnable() {
        if (!server.pluginManager.isPluginEnabled("Vault")) {
            logger.info("Vault isn't enabled, and this plugin is dependent on it. Disabling..")
            isEnabled = false
            return
        }

        /*-- Start loading config --*/
        if (!dataFolder.exists())
            dataFolder.mkdirs()
        configFile = File(dataFolder, "config.json")
        if (!configFile.exists())
            saveResource("config.json", false)
        config.putAll(gson.fromJson(InputStreamReader(getResource("config.json")), config.javaClass)) // Add defaults
        config.putAll(gson.fromJson(configFile.reader(), config.javaClass)) // Load config from data folder
        writeConfig() // Write defaults and custom options to config to make sure it's there for the user from start.
        /*-- Stop loading config --*/

        /*-- Start loading accounts --*/
        accountsDir = File(dataFolder, "accounts")
        if (!accountsDir.exists())
            accountsDir.mkdirs()
        for (file in accountsDir.listFiles()) {
            val uuid = file.nameWithoutExtension
            val contents: MutableMap<String, Any?> = gson.fromJson(InputStreamReader(getResource("config.json")), config.javaClass)
            val balance = contents["balance"] as? Double ?: config["starting-balance"] as Double
            accounts[UUID.fromString(uuid)] = balance
        }
        /*-- Stop loading accounts --*/

        /*-- Start loading taxes --*/
        // TODO: Add taxes
        /*-- Stop loading taxes --*/

        server.servicesManager.register(Economy::class.java, EconHandler(this), this, ServicePriority.Normal)
    }

    override fun onDisable() {
        writeConfig() // Write all options to config
    }

    fun writeConfig() {
        val json = gson.toJson(config)
        configFile.delete()
        Files.write(configFile.toPath(), json.toByteArray(), StandardOpenOption.CREATE, StandardOpenOption.WRITE)
    }
}