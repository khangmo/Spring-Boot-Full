/**
* Author KhangNT
* Create Date 2016/04/01
*
* Version v1.0
*/

/** 
 * Get parameter form URL
 * 
 * @param paramName. 
 */
function getParameterByName(paramName) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)", "i"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}