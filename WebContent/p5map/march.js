var March = (function(){
  
  function _class(character){
    this.character = character;
    this.route = [];
  }
  
  _class.prototype.addRoute = function(x, y){
    this.route.push({x, y});
  }
  
  _class.prototype.isTerminate = function(x, y){
    return this.route.length == 0;
  }
  
  _class.prototype.step = function(div){
    if( !this.isTerminate() ){
      var target = this.route[0];
      this.character.chase(target, div);
      if( this.character.pointEquals(target) ){
        this.route.shift();
      }
    }
  }
  
  return _class;
  
})()