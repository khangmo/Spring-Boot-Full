/** Post form, loading page, receive JSON 
 * Author: KhangNT
 * v1.0
 * @param {object} f
 * */
(function (f) {
    /**
     * Post form without AJAX
     * @param {object} target
     * @param {String} action
     */
    f.fn.postForm = function (target, action) {
        $(this).attr("action", "/" + action);
        var iframe = $('<iframe name="iframeUpload" style="position:absolute;top:-9999px" />').appendTo('body');
        iframe.load(function () {
            var content = $(iframe).contents().get(0);
            var data = $(content).find("body").html();
            $(target).empty().html(data);
            /* Remove iframe when upload success. */
            iframe.remove();
        });
    };
    /**
     * Loading page without parameter using AJAX
     * @param {object} target
     * @param {String} action
     */
    f.fn.loadingPage = function (target, action) {
        $.ajax({
            url: "/" + action,
            cache: false,
            success: function (result) {
                $(target).empty().html(result);
            }
        });
    };
})(jQuery, document, window, navigator);