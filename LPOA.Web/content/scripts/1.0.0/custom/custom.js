/**
Custom module for you to write your own javascript functions
**/
var Custom = function () {

    // private functions & variables

    function dateRange(){
        var $dateRage = $(".input-daterange");
        if($dateRage.length>0&&$dateRage.datepicker){
            $dateRage.datepicker({
                format: 'yyyy-mm-dd',
                autoclose: true,
                language: 'zh-CN',
                todayBtn: "linked"
            });
        }
    }

    function trunk8(){
        if ($(".trunk") && $(".trunk").trunk8) {
            $(".trunk").trunk8();
            $(window).resize(function () {
                $(".trunk").trunk8();
            });
        }
        if ($(".trunk-fill") && $(".trunk-fill").trunk8) {
            $(".trunk-fill").trunk8({fill:'&hellip;<span class="canShow"></span>'});
            $(window).resize(function () {
                $(".trunk-fill").trunk8({fill:'&hellip;<span class="canShow"></span>'});
            });
            $(".trunk-fill").has(".canShow").css("cursor","pointer");
            $(".trunk-fill").has(".canShow").on("click",function(){
                $("#trunkMsg").html($(this).attr("title"));
                $("#trunkModal").modal();
            });
        }
        if ($(".trunk-fill4") && $(".trunk-fill4").trunk8) {
            $(".trunk-fill4").trunk8({fill:'&hellip;<span class="canShow"></span>',lines:4});
            $(window).resize(function () {
                $(".trunk-fill4").trunk8({fill:'&hellip;<span class="canShow"></span>',lines:4});
            });
            $(".trunk-fill4").has(".canShow").css("cursor","pointer");
            $(".trunk-fill4").has(".canShow").on("click",function(){
                $("#trunkMsg").html($(this).attr("title"));
                $("#trunkModal").modal();
            });
        }
    }

    // 按钮确认
    var btnConfirm = function (selector, message, callback) {
        if ($(selector) && $(selector).length > 0) {
            $(selector).on("click", function (e) {

                //callback(confirm(message));
                if (confirm(message)) {
                    if(callback){
                        callback(this);
                    }

                    //return true;
                }
                else
                {
                    return false;
                }
                //return confirm(message);
            });
        }
    };

    var substringMatcher = function(strs) {
        return function findMatches(q, cb) {
            var matches, substrRegex;

// an array that will be populated with substring matches
            matches = [];

// regex used to determine if a string contains the substring `q`
            substrRegex = new RegExp(q, 'i');

// iterate through the pool of strings and for any string that
// contains the substring `q`, add it to the `matches` array
            $.each(strs, function(i, str) {
                if (substrRegex.test(str)) {
// the typeahead jQuery plugin expects suggestions to a
// JavaScript object, refer to typeahead docs for more info
                    matches.push({ value: str });
                }
            });

            cb(matches);
        };
    };




    // public functions
    return {

        //main function
        init: function () {
            //initialize here something.
            dateRange();
            trunk8();
        },
        btnConfirm:btnConfirm,
        // typehead（自动完成） 匹配子字符串
        substringMatcher:substringMatcher,

        //some helper function
        doSomeStuff: function () {

        }

    };

}();

/***
Usage
***/
//Custom.init();
//Custom.doSomeStuff();