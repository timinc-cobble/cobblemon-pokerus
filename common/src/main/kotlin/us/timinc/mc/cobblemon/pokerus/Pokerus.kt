package us.timinc.mc.cobblemon.pokerus

import com.cobblemon.mod.common.api.Priority
import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.pokemon.Pokemon
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import us.timinc.mc.cobblemon.pokerus.handler.PokerusCureHandler
import us.timinc.mc.cobblemon.pokerus.handler.PokerusEvBoost
import us.timinc.mc.cobblemon.pokerus.handler.PokerusSpreadHandler
import us.timinc.mc.cobblemon.timcore.AbstractConfig
import us.timinc.mc.cobblemon.timcore.AbstractMod
import us.timinc.mc.cobblemon.timcore.CustomIntProperty
import us.timinc.mc.cobblemon.timcore.CustomMarkBooleanProperty

const val MOD_ID: String = "pokerus"

object Pokerus : AbstractMod<Pokerus.PokerusConfig>(MOD_ID, PokerusConfig::class.java) {

    class PokerusConfig : AbstractConfig() {
        val wildInfectionChangeNumerator: Int = 3
        val wildInfectionChanceDenominator: Int = 65536
        val chanceToSpread: Int = 3
        val onlyInfectNewIfNobodyOnTeamHas: Boolean = false
        val evMultiplier: Int = 2
        val onlyInBattle: Boolean = true
    }

    object DataKeys {
        object PokemonProperties {
            val POKERUS = modResource("pokerus")
            val HAD_POKERUS = modResource("had_pokerus")
            val POKERUS_X = modResource("pokerus_x")
            val POKERUS_Y = modResource("pokerus_y")
            val LAST_CHECKED_DAY = modResource("last_checked_day")
        }

        object Marks {
            val POKERUS = modResource("pokerus")
            val POKERUS_CURED = modResource("pokerus_cured")
        }
    }

    object Chat {
        fun pokerusGained(pokemon: Pokemon): MutableComponent =
            Component.translatable("pokerus.notifications.pokerus.gained", pokemon.getDisplayName())

        fun pokerusCured(pokemon: Pokemon): MutableComponent =
            Component.translatable("pokerus.notifications.pokerus.cured", pokemon.getDisplayName())
    }

    object PokemonProperties {
        val POKERUS = CustomMarkBooleanProperty(DataKeys.PokemonProperties.POKERUS.toString(), DataKeys.Marks.POKERUS)
        val HAD_POKERUS =
            CustomMarkBooleanProperty(DataKeys.PokemonProperties.HAD_POKERUS.toString(), DataKeys.Marks.POKERUS_CURED)
        val POKERUS_X = CustomIntProperty(DataKeys.PokemonProperties.POKERUS_X.toString())
        val POKERUS_Y = CustomIntProperty(DataKeys.PokemonProperties.POKERUS_Y.toString())
        val LAST_CHECKED_DAY = CustomIntProperty(DataKeys.PokemonProperties.LAST_CHECKED_DAY.toString())
    }

    init {
        CobblemonEvents.BATTLE_VICTORY.subscribe(Priority.LOWEST, PokerusSpreadHandler::handle)
        CobblemonEvents.BATTLE_VICTORY.subscribe(Priority.LOWEST, PokerusCureHandler::handle)
        CobblemonEvents.EV_GAINED_EVENT_PRE.subscribe(Priority.LOWEST, PokerusEvBoost::handle)
    }
}