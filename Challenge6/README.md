# Challenge VI

**_Task:_ Circle detection (Computer Vision)**

***Description:*** Steps for circle location detection:
1. Load image
2. Convert image to greyscale
3. Do edge detection (Sobel operator)
4. Do circle detection at various radii (Hough transform)
5. Draw circle around the most prominent circle(s)

***Result:***
Sadly my circle hough operation fails at calculating and agregating the scores for each pixel. This is why the 4th image in all of these doesn't highlight the circles.
<p float="left">
  <img src="https://github.com/ransaked1/SpaceCadets-COMP1202/blob/master/Challenge6/circle1.png" width="400" height="300" />
  <img src="https://github.com/ransaked1/SpaceCadets-COMP1202/blob/master/Challenge6/circle2.png" width="400" height="300" /> 
</p>

<p float="left">
  <img src="https://github.com/ransaked1/SpaceCadets-COMP1202/blob/master/Challenge6/circle3.png" width="400" height="300" />
  <img src="https://github.com/ransaked1/SpaceCadets-COMP1202/blob/master/Challenge6/circle4.png" width="400" height="300" /> 
</p>


### Quick Setup
Download the project and open it in IntelliJ, it should automatically setup the dependencies and run the program in the IDE.

### Compatibility
Recommended JDK version - 17 <br>
Gradle version - 7.1 <br>
