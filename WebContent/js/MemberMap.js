var MemberMap = {};

MemberMap.members = [];
MemberMap.iconConfigs = [];
MemberMap.iconDefaultConfig = {code: 0, str:"×", color:"rgb(230, 230, 230)"};

MemberMap.backgroundImg = null;

MemberMap.TRIGHT = 100;
MemberMap.TLEFT = 101;
MemberMap.TTOP = 102;
MemberMap.TBOTTOM = 103;

MemberMap.defaultFillColor = "rgba(0, 0, 0, 1)";

//ユーザが変更してよい変数
//アイコンの大きさC
MemberMap.memberRectWidth = 30;
MemberMap.memberRectHeight = 30;
//フォントの種類
MemberMap.fontType = null;
//TBOTTOM指定時のずれ
MemberMap.tBottomGap = 15;


MemberMap.addMember = function(n0, dx0, dy0, ts0){
	this.members[this.members.length] = {
		name: n0, dx: dx0, dy: dy0, text_side: ts0
	}
}

MemberMap.clearMember = function(){
	this.members = new Array();
}

MemberMap.addIconConfig = function(c0, s0, cl0){
	this.iconConfigs[this.iconConfigs.length] = {
		code: c0, str: s0, color: cl0
	};
}

MemberMap.clearIconConfig = function(){
	this.iconConfigs = new Array();
}

MemberMap.setIconDefaultConfig = function(s0, cl0){
	this.iconDefaultConfig = {
		code: 0, str: s0, color: cl0
	};
}

MemberMap.setIconSize = function(w0, h0){
	this.memberRectWidth = w0;
	this.memberRectHeight = h0;
}

MemberMap.getIconConfig = function(code){
	for(var i=0; i<this.iconConfigs.length; i++){
		if( Array.isArray(this.iconConfigs[i].code) ){
			for(var j=0; j<this.iconConfigs[i].code.length; j++){
				if( code == this.iconConfigs[i].code[j] ){
					return this.iconConfigs[i];
				}
			}
		}else{
			if( code == this.iconConfigs[i].code ){
				return this.iconConfigs[i];
			}
		}
	}
	return this.iconDefaultConfig;
}

MemberMap.isDefined = function(val){
	return (val !== undefined && val != null);
}

MemberMap.drawMemberIcon = function(ctx, name, cx, cy, nstr_f, code){
	var dw = this.memberRectWidth;
	var dh = this.memberRectHeight;
	var dx = cx - (dw / 2);
	var dy = cy - (dh / 2);
	var ic_conf = this.getIconConfig(code);

	if( this.fontType != null ) ctx.font = this.fontType;
	var nmet = ctx.measureText(name);
	var nt_dx, nt_dy, nt_align;
	switch( nstr_f ){
	case this.TLEFT:
		nt_dx = dx - 5;
		nt_dy = dy + dh - 5;
		nt_align = "right";
		break;
	case this.TTOP:
		nt_dx = cx;
		nt_dy = dy - 5;
		nt_align = "center";
		break;
	case this.TBOTTOM:
		nt_dx = cx;
		nt_dy = dy + dh + 5 + this.tBottomGap;
		nt_align = "center";
		break;
	default:
		nt_dx = dx + dw + 5;
		nt_dy = dy + dh - 5;
		nt_align = "left";
		break;
	}

	if( this.fontType != null ) ctx.font = this.fontType;

	if( this.isDefined(ic_conf.color) ){
		ctx.fillStyle = ic_conf.color;
		ctx.fillRect(dx, dy, dw, dh);
		ctx.fillStyle = this.defaultFillColor;
	}
	ctx.strokeRect(dx, dy, dw, dh);
	ctx.textAlign = "center";
	ctx.fillText(ic_conf.str, cx , dy + dh -5, dw-6);
	ctx.textAlign = nt_align;
	ctx.fillText(name, nt_dx, nt_dy);
}

MemberMap.drawMember = function(ctx, idx){
	var mdx = this.members[idx].dx;
	var mdy = this.members[idx].dy;
	var mna = this.members[idx].name;
	var tsd = this.members[idx].text_side;

	LmmManager.getMemberState(mna, function(inf){
		MemberMap.drawMemberIcon(ctx, mna, mdx, mdy, tsd, inf.stateCode);
	});
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
    var cnvs = document.getElementById(id);
    var ctx = cnvs.getContext("2d");
    ctx.beginPath();
    ctx.fillStyle = this.defaultFillColor;

    if( arguments.length >= 3 ){
    	ctx.clearRect(0, 0, canvas_width, canvas_height);
    }

    if( this.backgroundImg != null ){
    	if( arguments.length >= 3 ){
	    	this.drawBackgroundImg(ctx, this.backgroundImg, function(){
	    		MemberMap.drawMemberList(ctx);
	    	}, canvas_width, canvas_height);
    	}else{
	    	this.drawBackgroundImg(ctx, this.backgroundImg, function(){
	    		MemberMap.drawMemberList(ctx);
	    	});
    	}
    }else{
    	this.drawMemberList(ctx);
    }
}

MemberMap.drawMemberList = function(ctx){
	for(var i=0; i<this.members.length; i++){
		this.drawMember(ctx, i);
	}
}
