# Asteroids_Game
 A classic arcade video game which player controls a spaceship in an asteroid field to destroy incoming asteroids.
https://github.com/xingyeahhh/Asteroids_Game/blob/1d288279fe9c983a3d3b50927e0073a720e9c5fe/Asteroids-Menu%202023-04-23%2000-39-02.mp4
### Class AND Method:
![image](https://github.com/xingyeahhh/Asteroids_Game/assets/123461462/82663200-a82f-4cb5-9646-fd4ce0c71e8d)

### Input(rotate, move, acceleration, fire, Invincible status, trail....)(Partial Display):
![image](https://github.com/xingyeahhh/Asteroids_Game/assets/123461462/9b1ff199-1b40-490d-90ad-a80065149393)

### Display:
1.To begin, the size of a Pane object called pane is set to WIDTH and HEIGHT.
2. Next, make pane the scene's root node by creating a Scene object and passing pane as a parameter.
3. After that, include text objects (of the Text class) in the window to show the game's score, health, and level data.
4. To instantly update the values of these text objects, use AnimationTimer objects.
5. The game updates in-game objects (such as spacecraft, meteorites, bullets, etc.) based on player input through keyboard event handlers (setOnKeyPressed and setOnKeyReleased).
6. I can update the status of the game object on each animation frame, such as moving the ship, handling collisions, firing bullets, etc., by using another AnimationTimer object.
7.I can add or remove game items from the window to reveal or hide the related objects while handling collisions and status changes between game objects.
8.To finish, I make the stage the stage's current stage and use the stage to change the window's title.the setTitle() function.
![image](https://github.com/xingyeahhh/Asteroids_Game/assets/123461462/c25743d2-9835-48d7-a71e-3047052538df)

### Menu:
1. To design the menu title and option text, use Text and Font.
2. To produce a text flashing effect, use Timeline, KeyFrame, and KeyValue.
3. Use StackPane and VBox to organize and show menu items using layouts.
4. To handle user input, add a mouse click event handler (setOnMouseClicked) to the menu item.
5. To produce an asteroid animation effect, use the AnimationTimer object.
6. Add all of the components to the main window using the Scene object, and then show the menu scene using the Stage object.
![image]![image](https://github.com/xingyeahhh/Asteroids_Game/assets/123461462/e56da75d-cf00-4da7-addb-d75951be8d6c)

### High Scores:
1.The player name (playerName) and score (score) are passed to the addHighScore method, which subsequently adds the information to the high score list. By invoking the loadHighScores method, it first loads the current high scores list. The highest score is then sorted first after being added to the list as a new high score. The lowest highscore is deleted if the highscore list has more than 20 records. By using the saveHighScores function, it then saves the modified high scores.
2.The high score list is loaded from the text file using the loadHighScores function. The existence of the highscore file is initially verified. If present, it uses a BufferedReader to read the file content line by line and adds each line to the highScores list. The procedure then outputs a list of the high score information.
3.The revised high scores are saved to a text file using the saveHighScores function. It writes each entry in the highScores list to a file using a BufferedWriter. The player's name and score are both contained in a string called a record that looks like "Alice:100".
![image](https://github.com/xingyeahhh/Asteroids_Game/assets/123461462/1f5bca78-4775-463f-bbb2-3598138a6d6e)

### Motion:
1. To ensure that the object is always moving on the screen
 if (this.character.getTranslateX() > AsteroidsApplication.WIDTH) 
{
 this.character.setTranslateX(this.character.getTranslateX() % AsteroidsApplication.WIDTH);
        }

2.To ensure the natural and real acceleration of object movement
double changeX = Math.cos(Math.toRadians(this.character.getRotate()));
double changeY = Math.sin(Math.toRadians(this.character.getRotate()));
        changeX *= 0.05;
        changeY *= 0.05;

![image](https://github.com/xingyeahhh/Asteroids_Game/assets/123461462/b2afcc78-fb48-4735-b277-73d443dcddf5)

### Asteroids:
1. I am able to create asteroids of various sizes and forms with the help of Class PolygonFactory.
2. Depending on the size of the meteorite, we can provide asteroids with varying acceleration times and random spinning speeds.
3.The asteroids will produce a green fireworks effect after it explodes.
![image](https://github.com/xingyeahhh/Asteroids_Game/assets/123461462/04a016b9-e5e4-47b6-85a9-0d6947811197)

### Alien:
1. The alien shoots in five of the eight directions roughly every three seconds.
2. Aliens can move completely autonomously
3. The alien will reemerge after being hit every eight to twelve seconds..
4. The alien will produce a blue fireworks effect after it explodes.
![image](https://github.com/xingyeahhh/Asteroids_Game/assets/123461462/34e09424-edf0-45c7-b713-7893cfad7084)

### Hyperspace:
1. The spacecraft has a hyperspace jump capability every 10 seconds.
2. The spaceship inherits the direction after the hyperspace jump, but its speed is zero.
3.  The spacecraft will reach an absolute safe place(no any collision) after a hyperspace jump.
![image](https://github.com/xingyeahhh/Asteroids_Game/assets/123461462/b271115c-eb1b-4ae0-b671-56b5965a998f)

### General Effect:
![image](https://github.com/xingyeahhh/Asteroids_Game/assets/123461462/7210e141-6148-4cb2-8db0-2df9cec26e20)













