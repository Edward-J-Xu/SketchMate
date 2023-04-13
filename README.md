# SketchMate


## Team 205 Members

 - Edward Xu (e32xu)
 - Wendy Zhang (y3447zha)
 - Yuki Yu (y447yu)
 - Yihui Lu (y489lu)

 Supported gradle tasks:

| Tasks   | Description                                          |
|:--------|:-----------------------------------------------------|
| clean   | Remove build/ directory                              |
| build   | Build the application project in build/ directory    |
| run     | Run the application or console project               |
| distZip | Create run scripts in application/build/distribution |
| distTar | Create run scripts in application/build/distribution |

## Folders
- application/ : client side, unit tests included
- web/ : server side, unit tests included
- shared/ : shared model layer, unit tests included in application/

## Goal
SketchMate is a Kotlin desktop application that provides a virtual canvas for users to create, share, and collaborate on digital content in real-time. The application offers a range of features designed to facilitate creativity and productivity, including drawing tools, text input, shape insertion, and exporting features.

Users can create and edit drawings using the intuitive interface, which supports stylus selection for enhanced precision. SketchMate also allows for easy sharing of content, enabling users to collaborate with others in real-time or share their creations with clients, colleagues, or friends.

SketchMate provides various customization options for colors, line styles, and brush sizes, enabling users to personalize their drawings to suit their creative preferences.

## Quick-start
Installers can be found in release pages. No additional dependency installation required, simply run bin/application and you are good to go!
1. Decompress `application-1.0.0.tar` or `application-1.0.0.zip`

2. Run executable in `application-1.0.0/bin/application`
* see [release page](https://git.uwaterloo.ca/e32xu/cs-346-project-whiteboard/-/wikis/home)

## Features
- Multi-User Login/Register
- User Change Password
- Create New Remote Board
- Open and Edit Remote Board (Select all the boards existing on cloud)
- Different Remote Boards Supported (Additional Features)
- Local Drawing as well as Remote Drawing
- Smooth Synchronization on remote drawing for different users around the world
- Auto-Save and Auto-Load (If you are in local mode, it will save to and load from local, else it will be for remote)
- Switching between Local and Remote Mode
- Hot Key Support
- Export to PNG (Additional Features)
- Export to PDF (Additional Features)
- Save and Restore Window Size/Positon on exit and relaunch.
- Once logged in, the local board for the user will be loaded automatically.
- Switching between Light Theme and Dark Theme (Additional Features)
- User/Board Data Stored on Cloud, communicated by HTTP and SQL
- User allowed to launch multiple instances using remote data.
- There are tools that support:
    -  drawing (multiple pen styles and colours),
    -  inserting text (multiple fonts, sizes and colours).
    -  inserting shapes (rectangles, circle and triangle, multiple fill and border colours, draggable and resizable)
    -  editing existing content (Move, Remove, change Styles, etc.)
    -  selecting and moving content around the board
    -  erasing content (object eraser)


## Releases
We have 4 sprints. Each sprint produces a software release.
* see [release page](https://git.uwaterloo.ca/e32xu)

## Meeting Minutes
* see [meeting minutes folder](https://git.uwaterloo.ca/e32xu/cs-346-project-whiteboard/-/tree/main/meeting-minutes)

## License
MIT License

Copyright (c) 2023  Yihui Lu, Edward Xu, Yuki Yu, Wendy Zhang

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.


