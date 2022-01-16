## Java Application - Hungry Snake Game
![java](https://img.shields.io/badge/java-1.8-brightgreen)
![gradle](https://img.shields.io/badge/gradle-7.0-brightgreen)
![junit-jupiter](https://img.shields.io/badge/junit-5.7.0-brightgreen)
![git](https://img.shields.io/badge/git-2.24.3-brightgreen)
![ide](https://img.shields.io/badge/intellij-21.3.1-brightgreen)

My implementation of a tiny game Hungry Snake. Developed based on java Swing.
<p align="left" width="100%">
    <img width="30%" src="imgs/snake-welcome.png"> 
    <img width="30%" src="imgs/snake-gameplay.png"> 
    <img width="30%" src="imgs/snake-gameover.png"> 
</p>

## Usage
Download the latest version: `snake-java-v1.0.jar`. Type the following command line in terminal:
```bash
java -jar <path-to-your-snake-java-v1.0.jar>
```

For example, if the `.jar` package is saved at `/Users/Tom/Desktop/snake-java-v1.0.jar`, then the command would be,
```bash
java -jar /Users/Tom/Desktop/snake-java-v1.0.jar
```

## GUI
Using Swing `JFrame` and `JPanel` as a graphic tool to draw a `600 * 600` game board, which is further divided into `40 * 40` small units in size of `15 * 15` for each, called `Grid`.

## Snake and Apple
The snake is represented by an 2-dimensional array,
```java
int[][] snake = new int[snakeSize][2];
```
Each pair of array is the x and y coordinates of a point. For apple, it needs only 1 point,
```java
int[] apple = new int[2];
```

## Animating the image by Timer `ActionEvent`
The main idea is to use `javax.swing.Timer` to fire `ActionEvent` at specified intervals (that is 100ms in my case). Meanwhile `JPanel` is implemented as a `ActionListener` to monitor the `ActionEvent` sent by `Timer` every 100 millisecond. Then these `ActionEvent` trigger `JPanel` to call `actionPerformed()` method to draw the panel frame by frame.

## Keyboard events controls the snake
Bind a `java.awt.event.KeyListener` (in my case, the inner class `MyKeyAdapter` extends the `java.awt.event.KeyAdapter` abstract class, and overrides its `keyPressed()` method) to the panel using `java.swing.JPanel.addKeyListener()` method, so that panel can receive keyboard event.

## Project workflow
- Language: `Java8`
- Build Tool: `Gradle`
- Unit Test: `JUnit-Jupiter`
- Version Control: `Git`
- IDE: `Intellij`


