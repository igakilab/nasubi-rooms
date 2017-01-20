var Character = (function() {

  function _class(img) {
    this.x = 0;
    this.y = 0;
    this.size = 50;
    this.img = img;
    this.route = new March(this);
    this.r1=0;
    this.r2=0;
  }

  _class.prototype.set = function(x, y) {
    this.x = x;
    this.y = y;
  }
  
  _class.prototype.move = function(dx, dy){
    this.x += dx;
    this.y += dy;
  }
  
  _class.prototype.step = function(div) {
    this.route.step(div);
  }
  
  _class.prototype.chase = function(target, div) {
    this.move(
      Math.min(Math.max((target.x - this.x), -div), div),
      Math.min(Math.max((target.y - this.y), -div), div));
  }
  
  _class.prototype.setTarget = function(target) {
    console.log(this.rectContains(r1, this) ? "room1" : "room2");
    if( this.rectContains(r1,this) && this.rectContains(r2,target)){
      console.log("room1 -> room2");
      this.route.addRoute(150,80);
      this.route.addRoute(450,50);
      this.route.addRoute(target.x,target.y);
    }else if( this.rectContains(r2,this) && this.rectContains(r1,target)){
      this.route.addRoute(450,50);
      this.route.addRoute(150,50);
      this.route.addRoute(target.x,target.y);
    }else{
      this.route.addRoute(target.x,target.y);
    }
  }
  
  _class.prototype.rectContains = function(rect, point) {
    return(
    (rect.x <= point.x && point.x < (rect.x + rect.width) )&&
    (rect.y <= point.y && point.y < (rect.y + rect.height) ));
  }
  
  _class.prototype.pointEquals = function(p){
    return this.x == p.x && this.y == p.y;
  }

  _class.prototype.draw = function() {
    image(this.img, this.x-(this.size/2), this.y-(this.size/2), this.size, this.size);
  }

  return _class;

})();