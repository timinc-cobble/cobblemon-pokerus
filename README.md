# Pokerus

v1.7.3-1.1

[Modrinth](https://modrinth.com/mod/cobblemon-pokerus)

[CurseForge](https://www.curseforge.com/minecraft/mc-mods/cobblemon-pokerus)

[GitHub](https://github.com/timinc-cobble/cobblemon-pokerus)

## What if…

…your Pokémon could get sick, but stronger for some reason?

## Features

- Your Pokémon may contract Pokérus randomly when battling wild Pokémon.
- Infected Pokémon in your party may affect other Pokémon in your party.
- Pokémon become cured over time.
- EV gains are boosted on infected Pokémon.

## Dependencies

- [Cobblemon](https://www.notion.so/Cobblemon-22157e0d4afd80a49896c70a775a3c7f?pvs=21)
- [Tim Core](https://www.notion.so/Tim-Core-22057e0d4afd809b9c02e78f26805376?pvs=21)

## Testing

### Wild Infections

After running the game at least once with the mod installed, open the config and modify `wildInfectionChanceDenominator` to `3`, then run `/pokerus reload`. Change this back when you’re done testing, otherwise you will have a 100% chance to gain Pokérus after a battle.

Open the game, battle a wild Pokémon, and after the battle, one of your Pokémon should have Pokérus. You can confirm this by going to the Pokémon’s mark list.

### EV Gain Boost

Note your Pokémon’s current EVs for Speed. Run `/pokespawn magikarp` and knock it out. Check your Pokémon’s new EVs, you should have received 2 instead of 1.

### Spread to Party

Set `chanceToSpread` to `1` in the config and run `/reload`.  Make sure you have both an infected Pokémon and a non-infected Pokémon on your team. Then engage in another battle with a wild Pokémon. A previously non-infected Pokémon should now be infected.

### Cured

After (at max) four in-game days of battling with a Pokémon that has contracted Pokérus, check their EV page again and you should see that their Pokérus infection has been cured. They still gain boosted EVs, they just can’t spread it to other Pokémon on their team.

## Player Help

[Config Options](https://www.notion.so/Config-Options-2fc57e0d4afd8109932af2a64208e482?pvs=21)

## Addon Dev Help

### Data Pack Help

[Pokémon Custom Properties](https://www.notion.so/Pok-mon-Custom-Properties-2fc57e0d4afd81adb665cd4608a8e41b?pvs=21)

### Resource Pack Help

[Translations](https://www.notion.so/Translations-2fc57e0d4afd8191803fd86b53303db6?pvs=21)

[Resources](https://www.notion.so/Resources-2fc57e0d4afd813787fcd084848c1d37?pvs=21)

## Known Issues

- None. Why? Who’s asking? 👀

## Roadmap

If you’d like to keep up with the work being done on the mod, please join [the Discord](https://discord.com/invite/WKAR27SdSv) and subscribe to notifications on the channel for this content. You can also keep track of the to do list available on [the mod’s main page](https://www.notion.so/Pokerus-2c757e0d4afd80f883cbd7992907bb46?pvs=21).

## Feedback

If you have any questions or requests concerning the mod, or just want to drop by and say hi, visit us over at [the Discord](https://discord.com/invite/WKAR27SdSv)!

## Support

If I've made something you enjoyed or helped you make something, please consider [dropping a tip in the cup](https://ko-fi.com/timsminecraftmods) and mention how I helped if you'd like!