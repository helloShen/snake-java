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
java -jar <path-to-your-snake-java-vx.x.jar>
```

For example, for `/Users/Tom/Desktop/snake-java-v1.0.jar`, the command would be,
```bash
java -jar /Users/Tom/Desktop/snake-java-v1.4.jar
```

## MVC pattern
The game is developed with MVC design pattern.

### View: JFrame and JPanel
Using Swing `JFrame` and `JPanel` to draw a `600 * 600` game board, which is further divided into `40 * 40` small units, `15 * 15` pixels for each, called `Grid`. We set some of those grids into different color to draw a picture.
```java
// panel size
private static final int PANEL_WIDTH = 600;
private static final int PANEL_HEIGHT = 600;
// grids
private static final int GRID_SIZE = 15;
private static final int GRIDS_X = PANEL_WIDTH / GRID_SIZE;
private static final int GRIDS_Y = PANEL_HEIGHT / GRID_SIZE;
```
`paintComponent()` method is in charge of changing view. JPanel instance will invoke `paintComponent()` for us when we call `repaint()` method.

A typical trap here is that `repaint()` does not do the drawing immediately. What `repaint()` actually do is add a notion in the event queue that the component need to be draw **in the future**. When the swing thread gets to the repaint request, it will, with some intermediate logic, send
`paintComponent()` to that component. Thus, the time span is not promised. Here's a doc talking about this issue: [<ins>Paint & Repaint in Swing</ins>](https://web.stanford.edu/class/archive/cs/cs108/cs108.1092/handouts/27PaintRepaint.pdf).

### Model: snake array and apple array
The snake on the canvas is just a bunch of contiguous grids with a different color. Which can be easily represented by an 2-dimensional array. Each column corresponds to the x and y coordinates of a point. For apple, we need only 1 single point.
```java
// snake
private final int[][] snake = new int[GRIDS_X * GRIDS_Y][2];
// apple
private final int[] apple = new int[2];
```

### Controller - event dispatching thread
Swing is not thread safe. All Swing components and related classes, must be accessed on the event dispatching thread.
```java
public class SnakeGame implements Runnable {
    // some initialization code omitted
    @Override
    public void run() {
        // Invoked on the event dispatching thread.
        // Construct and show GUI.
    }

    public static void main(String[] args) {
        // Invoke SnakeGame on the event dispatching thread.
        SwingUtilities.invokeLater(new SnakeGame());
    }
}
```
Two event listeners living on event dispatching thread monitor two different types of events:
* `MyTimerAdapter` implements `TimerListener` interface. It monitors `ActionsEvent` from Timer. Timer callback `actionPerformed()` event handler to draw the panel at specified intervals.
* `MyKeyAdapter` implements `KeyListener` interface. It monitors `KeyEvent` from user's keyboard. Then the event handler `keyPressed()` will decide how to control the snake by changing the data in `snake[][]` array.

They were created as two inner classes of `GamePanel` class.
```java
private class MyTimerAdapter implements ActionListener { /* some code */ }
private class MyKeyAdapter extends KeyAdapter { /* some code */ }
```

Have to mention, `KeyListener` need a keyboard focus, which can be provided by `GamePanel`. So `MyKeyAdapter` is plugin to the `GamePanel` by `addKeyListener()` method.
```java
public GamePanel(){
    // ... some code ommited
    this.addKeyListener(new MyKeyAdapter()); // bind KeyListener to panel
    // ...
}
```

### Thread safety
We have two threads in `SnakeGame` process:
* event dispatching thread (an event queue)
* timer thread

All event listener, event handler or others tasks are living on event dispatching thread, which maintain a synchronized `EventQueue`. Timer thread has nothing to do with all these tasks. The only thing it does is sending a signal to the event dispatching thread, reminding it to repaint the panel. The event dispatching thread will keep everything synchronized in event queue.

So our SnakeGame is thread safe.

### Project workflow
- Language: `Java8`
- Build Tool: `Gradle`
- Unit Test: `JUnit-Jupiter`
- Version Control: `Git`
- IDE: `Intellij`

## Refs for myself
Swing
* [Event dispatching thread documentation](https://docs.oracle.com/javase/tutorial/uiswing/concurrency/dispatch.html)
* [Swing thread policy](https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html#threading)
* [More discusses about event dispatching thread on stackoverflow](https://docs.oracle.com/javase/tutorial/uiswing/concurrency/dispatch.html)
* [Swing event & listener explained](https://docs.oracle.com/javase/tutorial/uiswing/events/intro.html)
* [How to write a key listener](https://docs.oracle.com/javase/tutorial/uiswing/events/keylistener.html)
* [JPanel is focusable](https://docs.oracle.com/javase/7/docs/api/javax/swing/JPanel.html)
* [JFrame API](https://docs.oracle.com/javase/tutorial/uiswing/components/frame.html)
* [How to use Swing Timer](https://docs.oracle.com/javase/7/docs/api/javax/swing/Timer.html)
* [Paint & Repaint in Swing](https://web.stanford.edu/class/archive/cs/cs108/cs108.1092/handouts/27PaintRepaint.pdf)
* A good book to read: [Java Concurrency in Practice]

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
