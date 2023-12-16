# PROkedex
2023 Update:
Pretty much completely rewrote everything. Better Spellchecker, better presentation of results, better GUI management, less annoying bugs, way more readable and maintainable, finally using Java8 standard methods. I'm not completely done since the repel check is not implemented yet, but it's still a huge update. Once I get around making a new repel check (the old one was iffy as heck, so I would want to do a completely new one), I will make a new release. Until that point, consider this a preview for the peeps that are building it themselves.

To those: the new main you should use is in PROkedexGUI.java (capitalization), consider every class that hasn't been updated as deprecated. Due to the heavy refactoring, the new classes are NOT compatible with the old ones, so import as a new project if you want to keep the old version because of some changes you made. By this point, the old version is pretty much useless since the spawns changed so much and the old one (by the way foolishly) relied on the T1-T9 system to work. Turns out, it was never super accurate anyway.

Enjoy.

Original readme:
A simple spawn parser for PRO. Just wrote it because Reborn is down.

Searches for spawns of Pokemon either via area name or Pokemon name. Halfway passable repel trick filter is also available, but it currently doesn't understand multi-repel spawns (such as Pinsir/Scyther in Safari Area 1). Hey, my QA was me using it for about 2 hours and it always did what I wanted it to, so there's that.
I've also added a suggestion for a farming route if you enter a Pokemon's name. The predictor suggests a route based on the predicted spawn rate which entirely banks on my data collection (about 50k encounters in total). Not really sure how good it is, but you can try it out and report back!

Requirements:

-Java RE (any above 6 should be fine, so if you downloaded it in the past 10 years, you're good)

Uses:

-JSON Simple library (packaged into the jar)

-Spawn dumps from the PRO website.

Quick release link:

https://github.com/Karyoplasma/PROkedex/releases/tag/1.0.4b
