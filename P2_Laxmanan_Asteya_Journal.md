Asteya Laxmanan
Period 2

**Tuesday 4/18/2023 at home - 2 hours**

We were on a discord vc and began the process of setting up our classes, Actor and World, working together for it. We split up the work on the Actor and World classes, and then shared out each part we did so all of us would understand. My GitHub ran into quite a few errors, as it wasn't letting me push my changes due to some "fatal: bad object refs/heads/main 2" error. Because of this, I just sent the code I had through discord and Bhargav added it to his file and pushed the changes. I think I have resolved the pushing/pulling error we were running into. 

**Sunday 4/23/2023 at home - 3 hours**

Our main tasks to complete today were to finish all of the tests, and get the Ball/BallWorld class working. We collaborated over discord. Our Actor class was mainly done, just the getIntersecting methods were to be done, so we completed those. I worked on the BallWorld and Game class. 

We have passed all of our test cases, and the BallWorld testing works as well. The Ball bounces around the BallWorld. 

**Tuesday 4/24/2023 at home - 1 hour**

We got on call when we got a little bit of time, and helped Bhargav with the paddle and mouse logic. We got a decent headstart onto it, but then due to a scheduling conflict we had to hop off the call and work on it asynchronously. 

**Tuesday 4/25/2023 at home - 1 hour**

Today, we worked together to completely finish slides 40-46. We had started it previously, but we completed it now. We split up the three features that needed to be implemented among the three of us, and I finished my part. We have come together to discuss and everything is complete. 

**Tuesday 5/2/23 at school - 1 hour 30 minutes**

Mainly a brainstorm day, as we finalized the game and a couple of specifications for it. We decided to make it forest themed, so we began to look for sprites on this. We found a character and boss spritesheet.

**Thursday 5/4/23 at school - 1 hour 30 minutes**

We finalized the sprites for our game, and we began to crop it. I used an online image editor to crop our character spritesheet into individual frames, and added it to our project. I had done this previously with the same spritesheet, but it did not cut properly as I used a software. These old images are in the "resources" folder as a backup, I added the new sprites I cut to the "resources-edited" folder. 

**Thursday 5/4/23 at home - 1 hour 30 minutes** 

In class, we split up the work, and decided that we would do our divided parts at home. Bhargav worked on the core-movement, and I helped bug-test the movement and rotation. I am continuing to crop the couple of other spritesheets that we have, and will upload them when I finish. We intend to finish the rest of our movements, and begin implementing our special features soon. 

**Monday 5/15/23 at school - 45 minutes**

We worked on the dash and jump movement to smoothen it out, and we implemented a different logic because our previous one was quite inconsistent. Nicholas and I completely re-coded the logic. 

**Thursday 5/18/23 at school 1 hour 30 minutes**

Ran into some files with file naming and filepaths, reset all the file names and fixed the bug. Implemented Coin class. 

**Tuesday 5/23/23 at school - 1 hour 30 minutes**

We first grouped together to assess the problems/bugs we needed to work, and then split from there. We discussed about how we want to store the different levels, and we decided to save them in a txt file, and load them upon initialization of each level. I am working on the reading file part where it loads the new level once the character goes above the screen to advance. I also finished cropping all the boss sprites, so I have pushed that as well. 

**Tuesday 5/23/23 at home - 45 minutes**

Implemented the loading level logic, where we read a text file when loading a level. This is convenient when we are going from level to level when the character finishes the current level. 

**Saturday 5/27/23 at home - 2 hours** 

I wrote the Spike class, and we will add spikes to the world. 
Implemented the spike-player interaction, so that when the player touches the spikes, it dies and respawns - cycles through the death animation, and returns to its original starting position. There are a few issues because of the large bounding boxes, and we will fix that together later. 

**Sunday 5/28/23 at home - 3 hours**

Implemented the LevelWorld class, in which we will create our levels. Made a complex method to make any arrangement of bricks (row x col) starting with the top left corner being (x, y). Includes all the logic for choosing the correct tile design (for example, if x = 0, it will select a plain dirt tile because it is connected to the wall. However, if x is somewhere else in the middle, it will use the green border tiles to indicate the location of the tiles. 
I also created our first complete level, an introductory and somewhat basic level including the key parkour features of the game. Then, I wrote this level to a txt file using our previous method and now it is easily accessible in level1.txt in the world_files folder. Additionally, added up and down feature to spikes because we require some of our spikes to face downward. 

**Wednesday 5/31/23 at home - 1 hour**

I began to recode the logic for the LevelBuilder because it was slightly inconsistent, but as I tried to it just became more and more complicated so I ended up deciding to modify my original logic code. It works now, and we are going to make some more levels through this.

**Saturday 6/3/23 at home - 2 hours**

Designed a new level, in the text file level4.txt, have a bunch of other test txt files as I used those for testing. I will delete them once I confirm the levels. 

**Wednesday 6/7/23 at home - 5 hours**

Implemented the Spike rotation logic into the text file system and levels, so that the spikes can face any direction. Created a new level (level3.txt), however it's very difficult so we will have it as one of our later levels. Added chest logic into level loading through files. Created three extra levels, sort of like tutorial levels, using Pointers to reference what the player is supposed to do. The tutorial levels cover the three basic mechanics: lvl 1 is jump, lvl 2 is jump and dash, and lvl 3 is wall jump. Cleaned up all of our world files, kept only the files we needed. Fixed some other small things like incorrect tile placements in the level and adding chests. 
