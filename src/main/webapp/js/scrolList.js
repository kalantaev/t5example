$(document).ready(function(){
    $("table").hide();
    $("h5 div").click(function(){
        $(this).parent().next().slideToggle();
    });
}); 