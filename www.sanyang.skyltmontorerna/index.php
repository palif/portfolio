<html>
	
	<head>
		<meta charset="UTF-8">
		<meta name="author" content="Pa Sanyang">
		<meta name="description" content="Pa Sanyang website">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title> Skyltmontörerna | Index </title>
		<link rel="stylesheet" type="text/css" href="css/reset.css">
		<link rel="stylesheet" type="text/css" href="css/style.css">
		<link rel="stylesheet" type="text/css" href="icon/business-icons/font/flaticon.css">
		<link rel="stylesheet" type="text/css" href="icon/social/font/flaticon.css">
		<link rel="stylesheet" type="text/css" href="css/responsive-style.css">
	    <script src="script/jquery-3.1.1.min.js"></script>
	</head>	

<body>
	
	<header> 
		
		<nav class="wrap-content" >
			<div class="header-name-logo">
				<span><a style="text-decoration: none; color: black" href="http://skyltmontorerna.se/index"> Skyltmontörerna </a></span>
			</div>
			<ul>
				<li><a href="contact.php"> <span> <i class="icon-contact"> </i> KONTAKT </span> </a></li>
				<li><a href="about-us.php"> <span> <i class="icon-about-us"> </i> OM OSS </span> </a></li>
				<li><a href="gallery.php"> <span> <i class="icon-gallery"> </i> GALLERI </span> </a></li>
				<li><a href="service.php"> <span> <i class="icon-service"> </i> TJÄNSTER </span> </a></li>
			</ul>
		</nav>
		
	</header>
	
	<!--*************************************************************************
	********************** Responsive header: Start 
	**************************************************************************-->
	<div class="header-rs">
		<div id="rs-menu-icon"></div>
		<div id="rs-menu-overlay">
			<nav id="rs-menu-list">
				<ul>
					<li><a href="http://skyltmontorerna.se/index"> <span> <i class="icon-home"> </i> HEM </span> </a></li>
					<li><a href="http://skyltmontorerna.se/contact"> <span> <i class="icon-contact"> </i> KONTAKT </span> </a></li>
					<li><a href="http://skyltmontorerna.se/about-us"> <span> <i class="icon-about-us"> </i> OM OSS </span> </a></li>
					<li><a href="http://skyltmontorerna.se/gallery"> <span> <i class="icon-gallery"> </i> GALLERI </span> </a></li>
					<li><a href="http://skyltmontorerna.se/service"> <span> <i class="icon-service"> </i> TJÄNSTER </span> </a></li>
					<li> <span id="rs-menu-close"> <i class="icon-close"> </i> </span> </li>
				</ul>
			</nav>
		</div>
	</div>
	<script src="script/rs-menu.js"></script>
	<!--*************************************************************************
	********************** Responsive header: End 
	**************************************************************************-->
	
	<!--*************************************************************************
	********************** Image Slider: Start 
	**************************************************************************-->
	<div id="first-container-index"> 
		<div class="image-slide-content">
		  <img class="slide wrap-content" src="image/FullSizeRender-2.jpg" >
		  <img class="slide wrap-content" src="image/FullSizeRender.jpg" >
		  <img class="slide wrap-content" src="image/FullSizeRender-3.jpg" >
		</div>
	</div>
	<script src="script/autoImageSlider.js"></script>
	<!--*************************************************************************
	********************** Image Slider: End 
	**************************************************************************-->
		
	
	<!--*************************************************************************
	********************** Google Maps API: Start 
	**************************************************************************-->
	<div class="map-container">
		
		<div class="map-title wrap-content">
			<span>
				Ni hittar oss här:
			</span>
		</div>
		<div id="map" class="wrap-content"> </div>
	</div>

	<script>
		function initMap() {
			var uluru = {lat: 59.240417, lng: 17.858312};
			var map = new google.maps.Map(document.getElementById('map'), {
				zoom: 11,
				center: uluru
			});
			var marker = new google.maps.Marker({
				position: uluru,
				map: map
			});
		}
    </script>
    <script async defer
    	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDn_gO0kdjAIvjEAHAErYIzwP508ae-Slg&callback=initMap">
    </script>
    <!--*************************************************************************
	********************** Google Maps API: End 
	**************************************************************************-->
	
	
	<footer> 
		
		<div class="footer-content wrap-content">
			<div class="footer-first-row">
				<nav>
					<ul>
						<li><a href="facebook.com" target="_blank"> <span> <i class="icon-facebook"> </i>  </span> </a></li>
						<li><a href="http://instagram.com/skyltmontorerna" target="_blank"> <span> <i class="icon-instagram"> </i>  </span> </a></li>
						<li><a href="http://plus.google.com/109712671261897199851" target="_blank"> <span> <i class="icon-google"> </i>  </span> </a></li>
					</ul>
				</nav>
				<div>
					<ul>
						
					</ul>
				</div>
			</div>
			<div class="footer-second-row">
				<div><spa> Skyltmontörerna AB </spa></div>
				<div><spa> Copyright @ 2017 Sanyang Theme. All right is reserved. </spa></div>
			</div>
		</div>
		
	</footer>
		
</body>
	
</html>