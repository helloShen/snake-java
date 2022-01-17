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

#### Tips: `repaint()` method is asynchronous
A typical trap here is that `repaint()` does not do the drawing immediately. What `repaint()` actually do is add a notion in the event queue that the component need to be draw **in the future**. When the swing thread gets to the repaint request, it will, with some intermediate logic, send
`paintComponent()` to that component. Thus, the time span is not promised. Here's a doc talking about this issue: [<ins>Paint & Repaint in Swing</ins>](https://web.stanford.edu/class/archive/cs/cs108/cs108.1092/handouts/27PaintRepaint.pdf).

## Keyboard events controls the snake
Bind a `java.awt.event.KeyListener` (in my case, the inner class `MyKeyAdapter` extends the `java.awt.event.KeyAdapter` abstract class, and overrides its `keyPressed()` method) to the panel using `java.swing.JPanel.addKeyListener()` method, so that panel can receive keyboard event.

## Project workflow
- Language: `Java8`
- Build Tool: `Gradle`
- Unit Test: `JUnit-Jupiter`
- Version Control: `Git`
- IDE: `Intellij`

## Refs for myself
Swing
* [Paint & Repaint in Swing](https://web.stanford.edu/class/archive/cs/cs108/cs108.1092/handouts/27PaintRepaint.pdf)

Gradle
* [Two ways to specify java version in build.gradle file](https://stackoverflow.com/questions/27861658/how-specify-the-required-java-version-in-a-gradle-build)
* [Specify java version with gradle toolchains](https://docs.gradle.org/current/userguide/toolchains.html)

Git
* [Whether to .gitignore the whole .idea folder or not?](https://stackoverflow.com/questions/3041154/intellij-idea-9-10-what-folders-to-check-into-or-not-check-into-source-contro)
* [Another discussion about ignoring .idea folder](https://stackoverflow.com/questions/11968531/what-to-gitignore-from-the-idea-folder)
* [How to remove .idea folder from git commit](https://www.david-merrick.com/2017/08/04/how-to-remove-the-idea-folder-from-git/)
* [Difference between revert, checkout and reset](https://stackoverflow.com/questions/8358035/whats-the-difference-between-git-revert-checkout-and-reset)
* [Difference between restore and git reset](https://stackoverflow.com/questions/58003030/what-is-the-git-restore-command-and-what-is-the-difference-between-git-restor/58003889#58003889)

Intellij
* [An official guide for managing intellij project under version control](https://intellij-support.jetbrains.com/hc/en-us/articles/206544839-How-to-manage-projects-under-Version-Control-Systems)
* [JetBrains.gitignore - An ultimate .gitignore file for intellij project](https://github.com/github/gitignore/blob/main/Global/JetBrains.gitignore)
