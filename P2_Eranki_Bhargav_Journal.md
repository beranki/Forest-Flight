Bhargav Eranki <br>
Period 2 <br>

## Development Journal

#### Thurs 4/13/2023 in class + home - 1 hr
My team and I worked on brainstorming for our game. We didn't have a clear cut idea on what we were interested in doing, so we brought together a lot of influences from games we had been playing, and came up with what can be seen on the README.md file in the main branch of the repository. I also added the world and actor abstract classes.

#### Tues 4/18/2023 at home - 1 hr
We were on a discord vc and began the process of setting up our classes, Actor and World, working together for it. We split up the work on the Actor and World classes, and then shared out each part we did so all of us would understand. Because of this, Asteya just sent the code he had through discord and I committed it as a seperate Actor file as well, then pushing the changes. Asteya was running into merge conflicts, but believes they are all settled.

#### Thurs 4/20/2023 at home - 20 minutes
I set up the tests for the engine and also changed the logic for the World classes slightly. After running the tests, I realized there were several errors with our existing logic, and planned to consult w/ my team to confront them all together.

#### Sunday 4/23/2023 at home - 2 hrs
We hopped on call and worked on setting up the Ball and BallWorld. However, before that, there were key logic errors with the Actor and World classes which needed to be fixed. Presenting each step of development we did with one another, Asteya worked on writing the Game, Ball, and BallWorld classes while I worked on fixing the World and Actor classes. Eventually, after repeated bug testing, I finally got all test cases for the engine tester to pass, and then pushed it to the repository. After this, Asteya told me that there was a problem with the ball not moving with the Ball, BallWorld, and Game classes. I took a look at it and realized he did not call the timer on the BallWorld when initializing it. After a small change to the bounce logic, it was complete, and we had done HW#1! I made sure to tell Asteya the errors he made through text and what I did to change them. 

#### Monday 4/24/2023 at home - 30 min
We got on call with a little bit of free time and worked out the paddle and mouse logic on my computer. I livestreamed while they followed along, and I pushed when I had to go. I resumed the improved 
ball deflection logic for the paddle later when I had the time to do so. 

#### Tuesday 4/26/2023 at home - 1 hr
I was unable to make a call with Asteya and Nicholas due to timing conflicts, but they got on call with me to update me about everything, and answered all my questions. I then continued to work on the 
paddle ball deflection logic, and while I couldn't get all of it, I got most of it (excluding the logic from figure 4, I think) and plan to finish it up when I get free time later on in the week.

#### Monday 5/1/2023 in class - 1 hour
I was busy and not able to finish all of the logic for paddle ball deflection logic in earlier meetings, so I finished it up for figure 4 and officially completed the basis for the BreakoutDemo. I worked alongside Asteya and Nicholas to confirm this logic. We also worked on filling out the official README for the project in the main branch, transferring some of the initial brainstorming we did into a newly formatted md file in the main branch that contained a concisely written explanation of the game we intended to make, aptly titled Forest Flight (subject to change.) We also worked on gathering sprites, looking at the stylistic themes that we wished to pursue with our game.

#### Tuesday 5/2/2023 in class - 1 hour
We continued to source the sprites as a team, and discussed core implementation details on how we intended to bring to life the game we had envisioned. We revised the inital design of the game - meant to be a Celeste style, parkour oriented game, we decided to switch to one that also incorporates a combat based level at the end. 

#### Thursday 5/4/2023 in class - 1 hour
We continued to find sprites for the background and other assets, as we had found the spritesheets for our boss and our main character - this was mainly Asteya. We worked on dividing the efforts to contribute to the game and decided that I would work on some core movement like moving left and right, dashing, and attacking. Nicholas would work on jumping and other mobility aspects which we intended to include. 

#### Thursday 5/4/2023 at home - 1 hour
I finished up the movement logic for the main character and attached them to the main sprites. However, there were some bugs in the sprites rotating moving left and right, and also we decided to change our dash sprites. Asteya and I worked on fixing these bugs and continued to implement core details. We also worked on reducing the extraneous bounding box from the sprites so that any collision measured was as accurate as possible. From this point, we intend on working on doing the jump logic w/ Nicholas and also adding platforms and ironing out the logic for gravity and multi-screen parkour.

#### Friday 5/5/2023 at home - 15 minutes
With the current logic in place, I noticed a small bug with dashing that enabled the user to infinite jump in succession to dashing. I made a quick change to the logic, and also wrote a small condition that helped fix the issue with fazing into the ground unless the person had jumped first. Both were simple fixes that I had to attend to.

#### Tuesday 5/9/2023 at school - 1 hour
After Nicholas worked on some of the movement bugs, Asteya and I decided to change our cluttered sprite system and simplify some of the file names. After this, I added some of the objects which we would be using (like powerups) alongside a general Tile class, which enabled us to add tiles w/ any image onto the map. This would include floor tiles, platforms, and more.

#### Sunday 5/14/2023 at home - 20 minutes
I fixed some of the previous logic that we wrote for jumping on tiles - it was a quick fix pertaining to changing our existing logic, where we relied on being on the ground for resetting a lot of actions - now we changed it to being on a tile OR the ground.

#### Tuesday 5/23/2023 at school - 1 hour
I changed the structure of the Tile class to store the url so I could write the url of the image that the Tile was storing when saving the map. Speaking of, we needed a system to store the different levels of the game so that the player could jump between different layers and eventually reach the boss fight. I wrote a method which saved the location, class, and if needed, image url of all actors in the world. This would be accompanied by Asteya's file loader, which would read in these files I wrote to and load the worlds into them.

#### Wednesday 5/24/2023 at home - 30 minutes
I fixed minor bugs with merge conflicts and restored the code back to full functionality by just running through changes made as a part of the merge and reverting them if necessary.

#### Saturday 5/27/2023 at home - 3 hours
We met together in a discord call and worked on resolving the wall collision for dashing, as bugs were becoming more apparent. With some fixes in mind, Nicholas worked on reworking the logic to be simpler and ultimately more functional. He also worked on his own test world. In the meantime, Asteya worked on the logic for implementing spikes and the death/respawn logic for obstacles in the game. I worked on implementing elements of the game pertaining to the boss fight, including a coin cointer which allowed the player to get the coins stored from chests and the environment and have a running counter in the top left of the screen. Alongside this, I worked on developing the sprites for the boss into key animations in the Boss class, and tested how I wanted to stage the final boss battle. This included planning environmental attacks, different stages of attacks from the boss, rewards, map design and more. I intend on implementing thse in the future with my team.

#### Sunday 5/28/2023 at home - 30 minutes
I worked more on the boss by sequencing different stages of the boss attack and understanding how that would be done with the structure of the existing engine. There was very little code output, more of thinking and planning with the team through Discord.

#### Thursday 6/1/2023 at home - 15 minutes
I installed Asteya's level builder to use for the boss level. We worked together to break this down and made sure that I understood the logic behind it before I attempted using it.

#### Thursday 6/7-8/2023 at home - 4 hours 30 minutes
As this was the day before the project was due, I had been working on the boss level exclusively for a little less than 2 weeks, but had not committed all of it yet. As I went to pull Nicholas and Asteya's most recent changes, I forgot that my own changes were unstaged and as a result, lost all of the progress I had made. With significantly more complex environmental attacks, combos, and a multi stage boss fight, it was a devastating loss of material and progress, but I was keen on ensuring we had some sort of boss battle ready for the demo in class today. Working with Nicholas and Asteya as they confronted fixing movement bugs and designing new levels, I worked on remaking a fun yet simple boss battle that could be challenging to new and experienced players alike. I think I did so in a way that is not only demanding of coordination but also requires an understanding of the game's core mechanics. After a few hours this was all done, and I integrated it with the levels designed by Asteya and Nicholas. Last but not least, I added a small keybinds menu on the app so players could understand what the movement keybinds were beforehand.
