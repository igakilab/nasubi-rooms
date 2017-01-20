var back;
var images;
var characters = [];
var canvas;
var dest;
var r1,r2,r3,r4,r5;
var reply = "";
var tmp;
var pt = null;

function preload(){
    images = {
      "kitaba": loadImage("kitaba.png"),
      "ryokun": loadImage("koike.png")
    };

    back = loadImage("zemimap2016.png");

    console.log("load complete");
}

function reloadPosition(){
	P5map.latestPosition(function(reply){
		//console.log(reply);
	  	characters = [];

	  	for(var i=0; i<reply.length; i++){
	  	  var pos = reply[i];
	  	  var img = images[pos.memberName];
	  	  var chara = new Character(img);

	  	  var tmp = replacePx(pos.positions[0]);
	  	  chara.set(tmp.x, tmp.y);

	  	  if( pos.positions.length > 1 ){
	  	    chara.setTarget(replacePx(pos.positions[1]));
	  	  }

	  	  characters.push(chara);
  	}
	});
}

function setup() {
  canvas = createCanvas(880,1250);
  background(back);
  noStroke();
  rectbox();

  reloadPosition();
}

function draw() {
  background(back);

  if( pt ){
    text("mouse (" + pt.x + ", " + pt.y + ")", 20, 50);
    text("mouse (" + pt.x + ", " + pt.y + ")", 20, 1000);
  }

    characters.forEach(function(c){
      c.step(3);
    });
  characters.forEach(function(c){
      c.draw();
    });
}

function rectbox() {
  r1 = {x:0,y:0,width:403,height:1240};
  r2 = {x:403,y:0,width:340,height:1240};
  r3 = {x:0,y:86,width:114,height:1150};
  r4 = {x:307,y:152,width:114,height:1076};
  r5 = {x:242,y:87,width:161,height:64};
  r6 = {x:114,y:1161,width:289,height:67};
  r7 = {x:724,y:0,width:90,height:1150};
}

function mouseMoved(){
  pt = {x:mouseX, y:mouseY};
}

function replacePx(a){
	//console.log(a);
  return {x:a.x*110, y:a.y*110};
}