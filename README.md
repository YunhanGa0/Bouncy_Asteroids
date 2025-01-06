# Bouncy_Asteroids
>This is my work for BDIC *COMP2011J-Object Oriented Programming Individual Assignment*

A java based Asteroid-like game, where asteroids can bounce while reach the boundary of window. For who have no idea on what's Asteroids, here is an example: https://freeasteroids.org/

## Game Description
Asteroids was a classic arcade game where the player flies a spaceship destroying asteroids in space. This version
of the game will be a little different, traditionally the speed and momentum of asteroids is preserved and when
they cross an edge of the screen they would reappear on the opposite side travelling in the same direction. In
this version of the game, the player will still move in this way but the asteroids will instead bounce within the
bounds of the screen.

## Movement
- Movement in this game is in two distinct ways. For the players ship the important element of movement
is momentum. Objects moving in space always travel in a straight line at the same speed unless some
acceleration is applied. So when the players ship begins moving and no more thrust is applied, it  continue
in the same direction and at the same speed forever unless the ship crashes into an asteroid. The direction that
the ship travels is based on the direction that the ship was facing when the thrust was applied. If no thrust
is being applied, then the ship can face in any direction while it keeps moving in the direction of the original
thrust.
- The asteroids use a similar concept of movement, however asteroids have no ability to apply thrust and
cannot change direction on their own. The only way for an asteroid to change direction is when it collides with
the boundary of the screen. The calculations for the new direction and speed of travel should be based on the
angle of impact (and may require some trigonometry).
The player has control over the ship in three ways. They can rotate the ship around it’s center location
both clockwise and anti-clockwise using the left and right arrows. Additionally, the player can fire the ships
weapons. The bullets fired should travel forward in the direction that the ship is facing and not be influenced
by the current direction or speed of travel of the ship.
## Asteroids
- There are three types of asteroids represented in the game, large, medium and small. At the beginning each
level starts with only the large asteroids are on the screen. Every time a large asteroid is destroyed, it is replaced
by two faster moving medium asteroids. When a medium asteroid is destroyed it is replaced by two even faster
small asteroids.
- Asteroids can only be destroyed when they are shot or collide with the players ship. Asteroids the collide
on the screen should continue on their original trajectory and not cause any change in course for each other.
## Destruction
- When a large or medium asteroid is destroyed, it should be replaced by two smaller and faster asteroids. These
new asteroids should be travelling in a direction that is close to the direction of travel of the original asteroid,
but with some element of randomisation (±20°). For example, if a large asteroid was travelling with the heading
90°, then the two medium asteroids could have headings anywhere between 70° and 110°.
## Points/Scoring
Points are awarded for destroying the asteroids based on the following calculations:
- Large asteroid - 50 points each
- Medium asteroid - 100 points each
- Small asteroid - 200 points each
- Large spaceship - 500 points each
- Small spaceship - 1000 points each

Every time the players score reaches a multiple of 10000, the player gains an extra life.
## Enemy Spaceships
If the level has not been cleared quickly, such as within 30 seconds, an enemy spaceship may appear. These
spaceships will move across the screen once and fire at the player. The larger spaceship is generally slow and
does not fire frequently or very accurately. The smaller spaceship fires more often and more accurately, and
it moves more deceptively to make it harder to shoot. Spaceships can be destroyed if they collide with an
asteroid, in these cases both the spaceship and the asteroid are destroyed, but the player does not gain any
points. Approximately every 30 seconds a new alien spaceship should be generated.
## Levels
This game is based on the completion of levels, whenever a level is complete the player is returned to the
beginning to play again. At the beginning of the first level, there is only the player and a single asteroid. With
each successive level cleared, another asteroid is added at the beginning of the level. These asteroids should be
placed randomly within the bounds of the screen, but should not be close to the position of the player ship. In
earlier levels (1 to 5) large spaceships are spawned. In later levels (+5) small spaceships are spawned.

## Examples
