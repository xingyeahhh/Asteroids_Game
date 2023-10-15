# Asteroids_Game
 A classic arcade video game which player controls a spaceship in an asteroid field to destroy incoming asteroids.
### Class AND Method
![image](https://github.com/xingyeahhh/Asteroids_Game/assets/123461462/82663200-a82f-4cb5-9646-fd4ce0c71e8d)

### Input(rotate, move, acceleration, fire, Invincible status, trail....)(Partial Display):
![image](https://github.com/xingyeahhh/Asteroids_Game/assets/123461462/9b1ff199-1b40-490d-90ad-a80065149393)

### Display
1.To begin, the size of a Pane object called pane is set to WIDTH and HEIGHT.
2. Next, make pane the scene's root node by creating a Scene object and passing pane as a parameter.
3. After that, include text objects (of the Text class) in the window to show the game's score, health, and level data.
4. To instantly update the values of these text objects, use AnimationTimer objects.
5. The game updates in-game objects (such as spacecraft, meteorites, bullets, etc.) based on player input through keyboard event handlers (setOnKeyPressed and setOnKeyReleased).
6. I can update the status of the game object on each animation frame, such as moving the ship, handling collisions, firing bullets, etc., by using another AnimationTimer object.
7.I can add or remove game items from the window to reveal or hide the related objects while handling collisions and status changes between game objects.
8.To finish, I make the stage the stage's current stage and use the stage to change the window's title.the setTitle() function.
![image](https://github.com/xingyeahhh/Asteroids_Game/assets/123461462/c25743d2-9835-48d7-a71e-3047052538df)

### Menu
1. To design the menu title and option text, use Text and Font.
2. To produce a text flashing effect, use Timeline, KeyFrame, and KeyValue.
3. Use StackPane and VBox to organize and show menu items using layouts.
4. To handle user input, add a mouse click event handler (setOnMouseClicked) to the menu item.
5. To produce an asteroid animation effect, use the AnimationTimer object.
6. Add all of the components to the main window using the Scene object, and then show the menu scene using the Stage object.
![image]![image](https://github.com/xingyeahhh/Asteroids_Game/assets/123461462/e56da75d-cf00-4da7-addb-d75951be8d6c)


