//// Midterm code for 59CST112
//Made by David Marques
//things I learned while making this: CTRL + T = auto format! makes the code look ssooo good

/** note that I also comment on the right of the code!*/

String Help = "W to take away wall /n 1, 2, and 3 to reset corresponding balls \n R to reset everything. \n M to toggle Mouse \n P to set pool table to Pink \n H to hide the help";

float tableR, tableL, tableT, tableB;      //pool table coordinates

float wallR, wallL, wallT, wallB;
float wallWidth;

float deltaX, deltaY, deltaDX, deltaDY;
float sigmaX, sigmaY, sigmaDX, sigmaDY;    //Ball values
float marcoX, marcoY, marcoDX, marcoDY;
float ballDia;

float ratX, ratY;
boolean ratDisplay;

float poolR,poolG,poolB;

float temp;
boolean help;

boolean wall;

void reset() {

  poolR = 29;
  poolG = 191;
  poolB = 41;
  
  ballDia=40;
  wallWidth=40;
  //Table size
  tableR=width-20;
  tableL=20;        
  tableT=height/4;
  tableB=height-20;
  
  wallR=width/2+wallWidth;
  wallL=width/2-wallWidth;
  wallT=tableT;
  wallB=tableB;
  wall=true;
  
  ratX=1;
  ratY=height - 50;
  ratDisplay = false;

  help = true;

  //random values when reset
  resetBall1();
  resetBall2();
  resetBall3();
}


//// SETUP:  size and table
void setup() {
  size( 700, 500 );
  rectMode(CORNERS);
  reset();
}


// Draw function bringing it all together
void draw() {
  scene();
  balls();
  wall();
  rat();
  ballMove();
  wallBounce();
  ballBounce();
}



// the background scene
void scene() {
  float purple;
  noStroke();
  background(#F5BC1E);

  fill(#8E620A);
  rect(tableL-10, tableT-10, tableR+10, tableB+10); //Rim
  fill(poolR,poolG,poolB);
  rect(tableL, tableT, tableR, tableB);             //Table



  stroke(0);    //reset things back to default
  fill(255);
  if(help == false){
    text("Press H for Help and Buttons",10,10);
  } else {
    text(Help,10,10);
  }
}


//how the balls are displayed
void balls() {
  
  //Delta "RED" "1"
  fill(255, 0, 0);
  ellipse(deltaX, deltaY, ballDia, ballDia);
  fill(0);
  text("1", deltaX, deltaY);
  

  //Sigma "YEL" "2"
  fill(255, 255, 0);
  ellipse(sigmaX, sigmaY, ballDia, ballDia);
  fill(0);
  text("2", sigmaX, sigmaY);

  //Marco "BLU" "3"
  fill(0, 0, 255);
  ellipse(marcoX, marcoY, ballDia, ballDia);
  fill(255);
  text("3", marcoX, marcoY);
  
  fill(0);
}



void wall(){
  if(wall == true){
    fill(#16DBD6);
    rect(wallL,wallT,wallR,wallB);
  }
}

void rat(){
  fill(255);
  ratX+=4;
  if(ratDisplay == true){
    if(frameCount % 30 > 15){
      triangle(ratX,ratY,ratX-20,ratY,ratX-20,ratY-10);
    } else {
      triangle(ratX,ratY,ratX-20,ratY,ratX-20,ratY+10);
    }
  }
  if(ratX > width){
    ratX = 1;
  }
}


//how the balls move
void ballMove() {
  deltaX += deltaDX;
  deltaY += deltaDY;

  sigmaX += sigmaDX;
  sigmaY += sigmaDY;

  marcoX += marcoDX;
  marcoY += marcoDY;
}



//how the balls bounce off the "walls" (includes middle wall)
//accounts for the ball diameter
void wallBounce() {
  if ( deltaX>tableR-ballDia/2 || deltaX<tableL+ballDia/2 ) deltaDX *= -1;
  if ( deltaY>tableB-ballDia/2 || deltaY<tableT+ballDia/2 ) deltaDY *= -1;

  if ( sigmaX>tableR-ballDia/2 || sigmaX<tableL+ballDia/2 ) sigmaDX *= -1;
  if ( sigmaY>tableB-ballDia/2 || sigmaY<tableT+ballDia/2 ) sigmaDY *= -1;

  if ( marcoX>tableR-ballDia/2 || marcoX<tableL+ballDia/2 ) marcoDX *= -1;
  if ( marcoY>tableB-ballDia/2 || marcoY<tableT+ballDia/2 ) marcoDY *= -1;
  
  //middle wall bounce
  if (wall == true){
    if ( deltaX<wallR+ballDia/2) deltaDX *= -1;
    if ( sigmaX<wallR+ballDia/2) sigmaDX *= -1;
    if ( marcoX<wallR+ballDia/2) marcoDX *= -1;
  }
}

//how the balls bounce off each other
void ballBounce() {
  if (dist(deltaX, deltaY, sigmaX, sigmaY)<ballDia) {
    temp=deltaDX; deltaDX=sigmaDX; sigmaDX=temp;  //delta sigma
    temp=deltaDY; deltaDY=sigmaDY; sigmaDY=temp;
  }

  if (dist(sigmaX, sigmaY, marcoX, marcoY)<ballDia) {
    temp=sigmaDX; sigmaDX=marcoDX; marcoDX=temp;  //sigma marco
    temp=sigmaDY; sigmaDY=marcoDY; marcoDY=temp;
  }

  if (dist(deltaX, deltaY, marcoX, marcoY)<ballDia) {
    temp=deltaDX; deltaDX=marcoDX; marcoDX=temp;  //delta marco
    temp=deltaDY; deltaDY=marcoDY; marcoDY=temp;
  }
}

//RESET BALL FUNCTIONS
void resetBall1(){
  deltaX  = random(wallR + ballDia/2, tableR - ballDia/2);
  deltaY  = random(tableT + ballDia/2, tableB - ballDia/2);
  deltaDX = random(-2, 2);
  deltaDY = random(-2, 2);
}

void resetBall2(){
  sigmaX  = random(wallR + ballDia/2, tableR - ballDia/2);
  sigmaY  = random(tableT + ballDia/2, tableB - ballDia/2);
  sigmaDX = random(-2, 2);
  sigmaDY = random(-2, 2);
}

void resetBall3(){
  marcoX  = random(wallR + ballDia/2, tableR - ballDia/2);
  marcoY  = random(tableT + ballDia/2, tableB - ballDia/2);
  marcoDX = random(-2, 2);
  marcoDY = random(-2, 2);
}

//// HANDLERS:  keys, click
void keyPressed() {
  
  //getting rid of the wall
  if (key == 'w' || key == 'W')  wall = false;
  
  //resetting to default settings
  if (key == 'r' || key == 'R')  reset();
   
  if (key == 'p' || key == 'P') { //changing pool table color to pink
    poolR = 240;
    poolG = 19;
    poolB = 159;
  }
  
  //toggle for the mouse
  if (key == 'm' || key == 'M')  ratDisplay = ! ratDisplay;
  
  ///Ball resets
  if (key == '1') resetBall1();
  if (key == '2') resetBall2();
  if (key == '3') resetBall3();
 
  if (key == 'h' || key == 'H') help = !help;
}

//reset functions for mouse press
void mousePressed(){
  if( dist(mouseX,mouseY,deltaX,deltaY) < ballDia/2) resetBall1();
  if( dist(mouseX,mouseY,sigmaX,sigmaY) < ballDia/2) resetBall2();
  if( dist(mouseX,mouseY,marcoX,marcoY) < ballDia/2) resetBall3();
}
