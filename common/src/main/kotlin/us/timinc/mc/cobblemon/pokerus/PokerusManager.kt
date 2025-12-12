package us.timinc.mc.cobblemon.pokerus

import com.cobblemon.mod.common.pokemon.Pokemon
import net.minecraft.stats.Stats
import us.timinc.mc.cobblemon.pokerus.Pokerus.PokemonProperties.LAST_CHECKED_DAY
import us.timinc.mc.cobblemon.pokerus.extension.getPokerus
import us.timinc.mc.cobblemon.pokerus.extension.hasEverHadPokerus
import us.timinc.mc.cobblemon.pokerus.extension.hasPokerus
import us.timinc.mc.cobblemon.pokerus.extension.infectWithPokerus
import us.timinc.mc.cobblemon.pokerus.extension.neverHadPokerus
import us.timinc.mc.cobblemon.pokerus.extension.tickPokerus
import kotlin.math.floor
import kotlin.random.Random

object PokerusManager {
    fun affectTeamPostBattle(party: List<Pokemon>) {
        if (party.isEmpty()) return

        val infectedIndexes = party.withIndex()
            .filter { it.value.hasPokerus() }
            .map { it.index }

        tryInfectRandomPartyMember(party)
        for (index in infectedIndexes) {
            trySpreadFromIndex(index, party)
        }
    }

    private fun tryInfectRandomPartyMember(party: List<Pokemon>) {
        if (party.any { it.hasEverHadPokerus() } && Pokerus.config.onlyInfectNewIfNobodyOnTeamHas) return

        val chance = Random.nextInt(Pokerus.config.wildInfectionChanceDenominator)
        if (chance >= Pokerus.config.wildInfectionChangeNumerator) return

        val eligibleIndexes = party.indices.filter { party[it].neverHadPokerus() }
        if (eligibleIndexes.isEmpty()) return

        val targetIndex = eligibleIndexes.random()
        party[targetIndex].infectWithPokerus()
    }

    private fun trySpreadFromIndex(index: Int, party: List<Pokemon>) {
        val adjacentOffsets = listOf(-1, 1)

        for (offset in adjacentOffsets) {
            val targetIndex = index + offset
            if (targetIndex !in party.indices) continue

            val target = party[targetIndex]
            val spreadRoll = Random.nextInt(Pokerus.config.chanceToSpread)
            if (target.neverHadPokerus() && spreadRoll == 0) {
                target.infectWithPokerus(party[index].getPokerus() ?: continue)
            }
        }
    }

    fun tickDay(party: List<Pokemon>) {
        for (pokemon in party) {
            val lastCheckedDay = LAST_CHECKED_DAY.getValue(pokemon)
            val currentPlayTime =
                pokemon.getOwnerPlayer()?.stats?.getValue(Stats.CUSTOM.get(Stats.PLAY_TIME)) ?: continue
            val currentPlayDay = floor(currentPlayTime / 2400.0).toInt() + 1
            LAST_CHECKED_DAY.pokemonApplicator(pokemon, currentPlayDay.toFloat())
            if (lastCheckedDay === null || currentPlayDay <= lastCheckedDay) {
                continue
            }

            pokemon.tickPokerus()
        }
    }
}