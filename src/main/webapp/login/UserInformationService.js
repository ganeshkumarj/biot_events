app.factory('userInformationService',function() {
    
    var userName,profile,code,image;
    
   this.getUserInfo=function(){
        return this;
    }
    
  this.setUserInfo=function(userName,profile,code,image){
        this.userName=userName;
        this.profile =profile
        this.code=code;
        this.image=image;
              
    };
    
    this.destroy = function(){
        this.userName=null;
         this.profile=null;
         this.code=null;
         this.image=null;
     }
    
    return this;
    
});