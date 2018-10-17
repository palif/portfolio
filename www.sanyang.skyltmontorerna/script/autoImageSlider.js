var index = 0;
autoSlider();

function autoSlider() {
    var i;
    var x = document.getElementsByClassName("slide");
    for (i = 0; i < x.length; i++) {
       x[i].style.opacity = "0.0";
       x[i].style.display = "none";  
    }
    index++;
    if (index > x.length) {index = 1}    
    x[index-1].style.opacity = "1.0";  
    x[index-1].style.display = "block";
    setTimeout(autoSlider, 4000); 
}