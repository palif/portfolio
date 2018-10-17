var rsMenuButton = document.getElementById('rs-menu-icon');
var rsOverlay = document.getElementById('rs-menu-overlay');
var rsMenuList = document.getElementById('rs-menu-list');
var rsMenuClose = document.getElementById('rs-menu-close');

rsMenuList.style.transition = "0.4s ease-in-out";
rsOverlay.style.transition = "0.4s ease-in-out";

rsMenuButton.onclick = function() {
	rsOverlay.style.height = "100%";
}

rsMenuClose.onclick = function() {
	rsOverlay.style.height = "0%";
}