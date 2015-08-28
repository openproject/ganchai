(function($){
  $(function(){
    $('.button-collapse').sideNav();
    $('.parallax').parallax();
      $("#ta-flow .row").masonry({
              itemSelector: '.col',
              //columnWidth: 200
          }
      );
  }); // end of document ready
})(jQuery); // end of jQuery name space