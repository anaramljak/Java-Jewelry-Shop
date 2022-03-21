var slideIndex = 0;
      showSlides();
      function showSlides() {
        var i;
        var slides = document.getElementsByClassName("mySlides");
        for(i = 0; i < slides.length; i++) {
          slides[i].style.display = "none";
        }
        slideIndex++;
        if(slideIndex > slides.length) {
          slideIndex = 1
        }
        slides[slideIndex - 1].style.display = "block";
        setTimeout(showSlides, 2000); // Change image every 2 seconds
      }

$(document).ready(function(){
    $(".cartProductQty").on('change',function(){
        var id=this.id;
        $('#update-product-'+id).css('display', 'inline-block');
    });
});

$(document).ready(function(){
    $(".cartPersQty").on('change',function(){
        var id=this.id;
        $('#update-product-'+id).css('display', 'inline-block');
    });
});



