<?php 

$folder_path = 'image/'; //image folder path

$folder = opendir($folder_path); 

 while (false !== ($entry = readdir($folder))) {
    if ($entry != "." && $entry != ".." && $entry != "Thumb.db") {
        
        $file_path = $folder_path.$entry;
        $ext = strtolower(pathinfo($file_path, PATHINFO_EXTENSION));
        if($ext=='jpg' || $ext =='png' || $ext == 'gif')
        {
            echo '<div class="grid-item" ><a href="'.$file_path.'" data-lightbox="lightbox" ><img src="'.$file_path.'" width="100%" /></a></div>';
        }
    }
}

closedir($folder );

/* echo '<div class="grid-item" ><a class="grid" href="'.$file_path.'" rel="lightbox" data-lightbox="gallery" onclick="hideLightbox(); return false;"><img src="'.$file_path.'" width="100%" id="loadingImage"/></a></div>';*/
?>
