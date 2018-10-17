<html>
	
	<head>
		<meta charset="UTF-8">
		<meta name="author" content="Pa Sanyang">
		<meta name="description" content="Pa Sanyang website">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title> Skyltmontörerna | Produkter </title>
		<link rel="stylesheet" type="text/css" href="css/reset.css">
		<link rel="stylesheet" type="text/css" href="css/style.css">
		<link rel="stylesheet" type="text/css" href="icon/business-icons/font/flaticon.css">
		<link rel="stylesheet" type="text/css" href="icon/social/font/flaticon.css">
		<link rel="stylesheet" type="text/css" href="css/responsive-style.css">
		<link rel="stylesheet" href="css/lightbox.css" type="text/css" media="screen" />
	    <script src="script/jquery-3.1.1.min.js"></script>
		<script type="text/javascript" src="script/masonry.pkgd.min.js"></script>
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
	********************** Photo Grid Masonry: Start 
	**************************************************************************-->
	
	<script type="text/javascript" src="script/lightbox.js"></script>
	
	<div class="grid">
	    <?php include('php/images_upload.php'); ?>
	</div>
	
	<script>
	$(window).on('load', function(){
	    $('div.grid').masonry({
	      itemSelector: 'div.grid-item',
	      columnWidth: 'div.grid-item'
	    });    
	});
	</script>
	
	<script>
	    // init Masonry
		var $grid = $('.grid').masonry({
		  // options...
		});
		// layout Masonry after each image loads
		$grid.imagesLoaded().progress( function() {
		  $grid.masonry('layout');
		});
	</script>
	
	<!--*************************************************************************
	********************** Photo Grid Masonry: End 
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