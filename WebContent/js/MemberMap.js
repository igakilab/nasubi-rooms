var MemberMap = {};

MemberMap.points = [];

MemberMap.originX = 28;
MemberMap.originY = 1025;


MemberMap.backgroundImg = null;


MemberMap.addPoint = function(x0, y0, name){
	this.points[this.points.length] = {
		x: x0, y: y0, name: name
	};
}

/*
 * 点を描画するメソッド
 * 引数で与えられたx0, y0の位置に半径r0の円を書く
 */
MemberMap.drawPoint = function(ctx, x0, y0){
	//点
    ctx.beginPath();
    ctx.arc(x0,y0,8,0,Math.PI*2.0,true);
    ctx.fill();
}

/*
 * 点が誰のなのか識別するためのメソッド
 */
MemberMap.drawName = function(ctx, x0, y0, name){
	console.log(x0,y0,name);
	ctx.fillText(name, x0, y0);
}

MemberMap.clearPoints = function(){
	this.points = new Array();
}

/*
 * 基準点と照らし合わせて、マップ上の座標を計算するメソッド
 * 基準点からの距離で足し算引き算
 * 実際の座標(m)からキャンバス(px)への変換
 */
MemberMap.calcAbstractPoint = function(x0, y0){
	var ax = x0 * 120;
	var ay = y0 * 120;
	ax = ax + this.originX;
	ay = ay + this.originY + 50;

	return {x: ax, y: ay};
}

MemberMap.drawBackgroundImg = function(ctx, img_path, callback, dw, dh){
	var img = new Image();
	if( arguments.length >= 5 ){
		img.onload = function(){
			ctx.drawImage(img, 0, 0, dw, dh);
			callback();
		};
	}else{
		img.onload = function(){
			ctx.drawImage(img, 0, 0);
			callback();
		};
	}
	img.src = img_path;
}

MemberMap.draw = function(id, canvas_width, canvas_height){
    console.log(id);
	var canvas = document.getElementById(id);
    console.log(canvas);
    var ctx = canvas.getContext('2d');
    ctx.beginPath();
    //ctx.fillStyle = this.defaultFillColor;

    if( arguments.length >= 3 ){
    	ctx.clearRect(0, 0, canvas_width, canvas_height);
    }

    mp = this;
    var points = this.points;
    this.drawBackgroundImg(ctx, this.backgroundImg, function(){
    	for(var i=0; i<points.length; i++){
    		var pt = points[i];
    		var apt = mp.calcAbstractPoint(pt.x, pt.y);
    		mp.drawPoint(ctx, apt.x, apt.y, 10);
    		mp.drawName(ctx, apt.x-7, apt.y-10, pt.name);
        }
    });


}
