# PROkedex

## 2023 Update:

Pretty much completely rewrote everything. The old version is pretty useless by now because so many spawns changed and it doesn't work with the new spawn dump structure, so there is no feasible way to update the spawn database.

The spawn chance predictions are gone because the new spawn dumps don't provide the information required for it to work. It was more guesswork than provably correct in the first place.

### Feature changes:

+ Better Spellchecker: The old one had some annoying bugs, this one should work more reliably and less intrusively.
+ Better presentation of results: No more issues with spacing!
+ Better GUI: You can resize the application window now without breaking everything. Also, right-clicking in the result table opens a pop-up menu that lets you copy or directly search for what's below.
+ Better repel check: The old one was very unreliable, so wrote a new one from scratch. It should be more accurate and less prone to breaking.
+ Better maintainability: If you are modifying and building this app yourself, you will find that the new structure is way more intuitive. The GUI now actually only does GUI stuff instead of being a band-aid for everything. ActionEvents are handled by different classes, so there is no overlap in functionality.
+ Better internals: Finally using Java8 standard methods instead of relying on very old ones.
+ Better updates: You can keep the spawn dumps and the files that depend on it updated by letting the app handle all the downloading and replacing. Don't worry about flooding PRO's servers, the routine makes sure that an update is reasonable before connecting to the http-server.

Enjoy.

## Original readme:

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
