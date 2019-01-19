FIRST TIME ON A COMPUTER  
==============================================================================================================================================
0. Open VisualStudioCode
1. Make a WPI project. (CTRL+SHIFT+P, type WPILIB project.) If it makes a black screen, close the tab and start over.  
2. You want a template, java, and iterative  
3. When you select the folder, you'll want a place easy to access, your desktop is a good idea, but it can be anywhere.  
4. You can make the name anything, but please make it relevant easy to tell apart from the rest.  
5. The team number is 3414.  
3. Right click build.gradle, manage vendor libraries.  
4. Select Install New Library (online)  
5. paste the following links:  

http://devsite.ctr-electronics.com/maven/release/com/ctre/phoenix/Phoenix-latest.json  
  

https://www.kauailabs.com/dist/frc/2019/navx_frc.json  
Look at the percentage in the blue bar at the bottom, when it's done there will be no percentage and you may proceed to step 6.  
6. Close VisualStudio  
7. Go into the project directory, delete src  
8. Go into git bash, if you have right click go to your project directory and right click anywhere, select "git bash here" if not, you'll have to use 'cd' and 'ls' to get to your project directory. When you get there, type 'git clone https://github.com/hackbots-3414/2019WrongWayVS.git'  
9. Copy the SRC folder from your pulled git into your previously defined project folder.  
10. Launch VisualStudio
  
COMITTING AND PUSHING CHANGES
1. Copy the src folder from your project back into your git folder. If you want you can rename this to git
2.  Navigate in git to your git folder. It should only contain the src and the readme.md file type 'git commit -m "your message here"' 
2 1/2. See the SIGNING OUT OF GIT SECTION and follow that before pushing
3. Now your local repistory is updated, but you'll need to push your changes, type 'git push -u origin YOURBRANCH'  
SIGNING OUT OF GIT
go to https://github.com/settings/tokens and delete any existing tokens. If you don't have any, that's good, continue onward.
