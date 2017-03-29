var exec = require('cordova/exec');


function accessMedia(){
}

accessMedia.prototype.getAllImages = function(arg0, success, error) {
	exec(success, error, "AccessMedia", "getAllImages", [arg0]);
};

accessMedia.install = function () {
	if (!window.plugins) {
		window.plugins = {};
	}

	window.plugins.accessMedia = new accessMedia();
	return window.plugins.accessMedia;
};

cordova.addConstructor(accessMedia.install);
