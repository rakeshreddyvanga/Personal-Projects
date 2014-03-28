(function ($) {

    $(document).ready(function () {
        alert("In custom JS");
        $("#detailsHead").click(function () {
            $("#detailsId").slideToggle(1000);
        });
    });
})(jQuery);