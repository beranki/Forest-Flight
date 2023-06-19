Nicholas Kann
Period 2


**Thursday 4/20/2023 at home -  30 minutes**

I worked with my team to fix some issues in our code from HW #1 through Discord. I did not work as much as I wanted to though, because I just came out of surgery so I was resting.

**Sunday 4/23/2023 at home - 2 hours**

My Intellij IDE was not working for various reasons, one of them being that it somehow had differing versions of the Java runtime environment and the Java compiler. So, I went into a call with my team so they could screenshare and I can work through them. I helped my team fix some bugs with the code, like Asteya having a huge error log that turned out to be a NullPointerException because he was trying to grab an image from a folder that didn't exist. We found out that it got removed when we were handling merge conflicts, so we readded it. Eventually, I gave up on trying to fix Intellij and just moved to Eclipse, which ended up working.

**Tuesday 4/25/2023 at home -  45 minutes**

I went on call with my team to go through slides 40-46. I added the paddle movement using arrows keys. Then we split up the work, so that Asteya works on the Brick class, I work on Score class, and Bhargav works on better ball bounce. I added the Score class and all of its features.	

**Tuesday 5/2/23 at school - 1 hour 30 minutes**

I brainstormed with the team in class and we got a final idea of what we want (forest theme). We also decided to have the progression start with parkour, then easy enemies, then a final boss that challenges what the player learned in the earlier stages, testing their movement. We then found spritesheets that we liked, which included the tileset, characters, and enemies.

**Thursday 5/4/23 at school - 1 hour 30 minutes**

We began cutting out the sprites from the spritesheet to get each frame lined up perfectly. Asteya was mainly in charge of cropping, while Bhargav and I started finding other potential spritesheets we could just download for free (we did not find any). Bhargav and I then started fixing our eclipse project because the main branch was not being recognized as a java project so we had to do a lot of debugging.

**Thursday 5/4/23 at home - 1 hour**

I fixed the dashing mechanic so that it would play the proper animation and face the right direction. I also changed the dashing physics so that it would have a velocity and an acceleration, so the movement feels a lot nicer to play with. I also added gravity and jumping, although it is not fully complete (the gravity logic might not work as well for higher falls, so I will rewrite the gravity logic). I also added a known bugs list on the readme.

**Sunday 5/7/23 at home - 30 minutes**

I manually edited the sprites to add two new sprites: updash and downdash. I then sent the sprite sheet to Asteya so he could crop it out according to the dimensions he used for all the other sprites.

**Tuesday 5/9/23 at school - 1 hour 15 minutes**

I found a couple of bugs like being able to jump multiple times so we fixed that as a team. We also changed our resources file system because it was set as a package before for some reason so it made it difficult to navigate through our sprites. I added the up-dash mechanic but I discovered some bugs with it like the jump velocity interfering with the dash velocity.

**Tuesday 5/9/23 at home - 1 hour 30 min**

I found a lot more bugs that I added to the bugs list in the readme. I also realized that our current gravity system is pretty difficult to work with when adding new features so I noted down that we should rework the gravity system later. I added grass platforms but the player is not able to interact with them yet because the gravity system makes it hard to decipher what the players current dy velocity is. 

**Monday 5/15/23 at school - 30 min**

Asteya and I worked through the code together to understand the gravity and collision logic that Bhargav implemented. Then, we cleaned up the code by deleting unneeded comments. We also scanned through the whole code to see which variables or functions were outdated and had no use. 

**Tuesday 5/16/23 at school - 1 hour 15 min**

We wanted to make the dashes function in a way that the jump velocity and gravity would not affect the player during the dash. We also wanted to fix the gravity affecting the updash. I was able to kill two birds with one stone by setting the dy velocity to zero whenever the player was in a dash.	Asteya and I also came up with plans for our game, like using environmental damage to defeat the final boss.

**Thursday 5/18/23 at school - 1 hour 15 min**

I worked with the team to map out our development plan so we would be more organized. We decided that I would work on refining the movement while Bhargav and Asteya worked on the levels and the final boss, and I would move on to help them after the movement is perfected. Bhargav and I also fixed the tile collision when landing on tiles by using only the player's colored pixels as hitboxes, rather than the whole image.

**Sunday 5/21/23 at home - 30 min**

I replaced the dash velocity with a dx dash velocity and a dy dash velocity so that the updash/downdash (downdash not yet implemented) velocities would be shorter than the side dashes. I also wrote code to make sure that the player could not infinite dash. The player can only dash one time before reseting the cooldown by landing on the floor.

**Tuesday 5/23/23 at school - 1 hour 15 min**
I fixed the midair jump bug, added a wall collision system for walking, and worked on finding the proper hitbox for the wall collision for walking. It took me a long time because it was a lot of trial and error of editing the hitbox size and rerunning the code over and over again.

**Thursday 5/25/23 at school - 1 hour 15 min**
I added a wall collision system for side dashing (it was buggy). It required me to make helper methods so I wouldn't flood the act method too much.

**Saturday 5/27/27 at home - 3 hours**
I reworked the wall collision system for side dashing which required me to delete a lot of the progress I made on Thursday. This took me an extremely long time because I kept encountering odd bugs that took me a while to figure out the cause, and I encountered bugs that required me to restart my entire logic. Eventually I found a simple workaround that worked. I also created a test world and a way of loading the test world when its needed.

**Sunday 5/28/27 at home - 30 min**
I added a fall death so that when the player falls out of the world on the bottom of the map, they die. This required me to change a small part of our gravity system but it wasn't hard. I also fixed death glitches like: the player facing the opposite direction upon spawning, the player having the death image after respawning for one frame, the jump continuing after respawn, and the dash continuing after death. I also changed some of the player's movement attributes so that the player is slower, dashes less distance, and jumps less distance.

**Monday 5/29/27 at home - 1 hour 30 min**
I fixed the bug where the player would fall through the tile they were standing on too early if they walk to edge of the tile. This required me to rework the getFarFootX method into getRightFootX and getLeftFootX, which ended up being a lot more versatile. I also added diagonal dashing which was just side and up dashing combined except with a faster deacceleration (can't make diagonal dashing too good). I also fixed some bugs where the player's dash velocities were not properly reset after respawning.

**Thursday 6/1/23 at school - 1 hour**
I "drew" the image for the diagonal dash which required me to use a photo editing app to edit out the spear, rotate it, and color in parts of the body and spear to make it look more natural. I then implemented it into the code.

**Sunday 6/4/23 at home - 2 hours 30 min**
I added head collisions to the game so that jumping or dashing into a ceiling tile would stop the player's dy movement. I then fixed distance inaccuracies with walking wall collisions (the player was too far from the wall when initially walking but got permanently closer after dashing because the dash offset the player's movement since the player doesn't move pixel by pixel but rather multiple pixels at once). This allowed me to add wall jumping, which I think elevates the opportunities for levels in the game by a lot.

**Tuesday 6/6/23 at home - 3 hours 30 min**
I fixed the issue where the player was able to to wall jump after they dashed, which wasn't really a bug I just didn't implement it yet. Then, since Asteya has been asking for me to fix the hitboxes with spikes for over a week, I got to work on that. Instead of checking player collision with the spike by just using getIntersectingObjects, since the image of the player was much larger than what it actually looks like, I added four rectangular hitboxes onto the player. Since those hitboxes are mostly just on the player's non transparent pixels, the player won't die to a spike when it looks like it hasn't touched them yet. This was inspired by the hitboxes in super smash bros, but now that I'm typing this I just realized I probably should've used ovals instead of rectangles for the hitboxes. I then did some minor refining like fixing the bug with the player facing the wrong way after death, the player being unable to diagonal dash mid-walljump, and the player's dash momentum being affected by the walljump.

**Wednesday 6/7/23 at home - 3 hours 30 min**
I mostly polished the game so that it's actually playable today. First, I fixed a level skip glitch that would make the game horrible. Then, I fixed a wall jumping glitch where the player would go into the wall and get stuck in an animation. I also polished levels by using the correct tiles for tiles that had the wrong image, moved some objects/tiles around, and etc. I then made level 3 possible to beat (it was literally impossible before), but it was extremely hard. So, I added a level skip key, which is pressing escape five times. I fixed the chests not containing the coins and made it so coins transfer over the levels. I then did final polishing like adjusting the player's starting positions and made the start button easier to press.


