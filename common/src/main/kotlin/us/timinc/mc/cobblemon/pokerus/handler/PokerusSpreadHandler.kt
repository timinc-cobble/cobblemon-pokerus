package us.timinc.mc.cobblemon.pokerus.handler

import com.cobblemon.mod.common.api.battles.model.actor.ActorType
import com.cobblemon.mod.common.api.events.battles.BattleVictoryEvent
import us.timinc.mc.cobblemon.pokerus.PokerusManager
import us.timinc.mc.cobblemon.timcore.AbstractHandler

object PokerusSpreadHandler : AbstractHandler<BattleVictoryEvent>() {
    override fun handle(evt: BattleVictoryEvent) {
        if (!evt.battle.isPvW) return

        (evt.winners + evt.losers)
            .filter { it.type == ActorType.PLAYER }
            .map { actor -> actor.pokemonList.map { it.effectedPokemon } }
            .forEach(PokerusManager::affectTeamPostBattle)
    }
}