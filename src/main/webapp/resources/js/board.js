$(window).on('load', function () {
    load('#js-load', '10');
    $("#js-btn-wrap .morebutton").on("click", function () {
        load('#js-load', '10', '#js-btn-wrap');
    })
});
 
function load(id, count, btn) {
    var boardlist = id + " .js-load:not(.active)";
    var boardlength = $(boardlist).length;
    var totalcount;
    if (count < boardlength) {
    	totalcount = count;
    } else {
    	totalcount = boardlength;
        $('.morebutton').hide()
    }
    $(boardlist + ":lt(" + totalcount + ")").addClass("active");
}