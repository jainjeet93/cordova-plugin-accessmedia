var exec = require('cordova/exec');


function accessMedia(){
}

accessMedia.prototype.coolMethod = function(arg0, success, error) {
	exec(success, error, "AccessMedia", "coolMethod", [arg0]);
};

accessMedia.install = function () {
	if (!window.plugins) {
		window.plugins = {};
	}

	window.plugins.accessMedia = new accessMedia();
	return window.plugins.accessMedia;
};

cordova.addConstructor(accessMedia.install);
