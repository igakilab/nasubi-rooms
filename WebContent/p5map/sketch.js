var back;
var photo1,photo2,photo3;
var canvas;
var kitaba;
var koike;
var yamagida;
var dest;
var r1,r2,r3,r4,r5;
var reply = "";
var tmp;
var pt = null;

function preload(){
    back = loadImage("zemimap2016.png");
    photo1 = loadImage("kitaba.png");
    photo2 = loadImage("koike.png");
    photo3 = loadImage("yamagida.png");
    kitaba = new Character(photo1);
    koike = new Character(photo2);
    yamagida = new Character(photo3);
    kitaba.set(500, 700);
    koike.set(550,750);
    yamagida.set(500,850);
}

function setup() {
  canvas = createCanvas(880,1250);
  background(back);
  noStroke();
  rectbox();
  var url = "http://localhost:3000/api/latest";
	var request = new XMLHttpRequest();
	request.open('GET', url);
	request.onreadystatechange = function () {
    if (request.readyState != 4) {
  	      // リクエスト中
   	    } else if (request.status != 200) {
  	      // 失敗
  	    } else {
    	    // 取得成功
    	    reply = JSON.parse(request.responseText);
    	    for(var i=0;i<reply.length;i++){
    	      tmp = reply[i];
    	      console.log(tmp);
    	      if(tmp.minor==101){
    	        tmp = replacePx(tmp);
              kitaba.setTarget(tmp);
    	      }else if(tmp.minor==102){
    	        tmp = replacePx(tmp);
    	        koike.setTarget(tmp);
    	      }else if(tmp.minor==103){
    	        tmp = replacePx(tmp);
    	        yamagida.setTarget(tmp);
    	      }
    	    }
   	 }    
	};
request.send(null);
}

function draw() {
  background(back);
  
  if( pt ){
    text("mouse (" + pt.x + ", " + pt.y + ")", 20, 50);
    text("mouse (" + pt.x + ", " + pt.y + ")", 20, 1000);
  }
  
  if(tmp){
    text("(x" + tmp.x + ",y:" + tmp.y + ")", 20, 30);
    kitaba.step(3);
    koike.step(3);
    yamagida.step(3);
  }
  kitaba.draw();
  koike.draw();
  yamagida.draw();
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
  console.log("(x" + tmp.x + ",y:" + tmp.y + ")");
  return {x:a.x*110, y:a.y*110};
}