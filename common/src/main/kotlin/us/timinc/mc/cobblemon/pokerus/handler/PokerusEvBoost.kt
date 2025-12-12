package us.timinc.mc.cobblemon.pokerus.handler

import com.cobblemon.mod.common.api.events.pokemon.EvGainedEvent
import us.timinc.mc.cobblemon.pokerus.Pokerus
import us.timinc.mc.cobblemon.pokerus.extension.hasEverHadPokerus
import us.timinc.mc.cobblemon.timcore.AbstractHandler

object PokerusEvBoost : AbstractHandler<EvGainedEvent.Pre>() {
    override fun handle(evt: EvGainedEvent.Pre) {
        if ((!Pokerus.config.onlyInBattle || evt.source.isBattle()) && evt.pokemon.hasEverHadPokerus()) evt.amount *= Pokerus.config.evMultiplier
    }
}