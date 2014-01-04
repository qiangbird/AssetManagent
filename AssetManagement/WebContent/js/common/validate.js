String.prototype.trim = function() {
    //return this.replace(/[(^\s+)(\s+$)]/g,"");
    //return this.replace(/^\s+|\s+$/g,""); //
    return this.replace(/^\s+/g,"").replace(/\s+$/g,"");
};
