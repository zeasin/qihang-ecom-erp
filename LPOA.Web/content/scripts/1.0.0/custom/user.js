/**
 * Created by Administrator on 14-8-21.
 */
var UserHandler = function(){

    function initDatepicker(){
        var $datepicker = $(".input-daterange");
        if($datepicker.length > 0 && $datepicker.datepicker){
            $datepicker.datepicker({
                format: 'yyyy-mm-dd',
                autoclose: true,
                language: 'zh-CN',
                todayBtn: "linked"
            });
        }
    }
    function handleCredit(){
        $(".js-doCredit").on("click",function(){
            $("#modalDoUserId").val($(this).attr("data-user-id"));
            $("#doCreditModal").modal();
        });
        $(".js-cancelCredit").on("click",function(){
            $("#modalCancelUserId").val($(this).attr("data-user-id"));
            $("#cancelCreditModal").modal();
        });
    }

    function handleFancyBox(){
        if($(".js-fancyBox").length>0 && $(".js-fancyBox").fancybox){
            $(".js-fancyBox").fancybox();
        }
    }


    function handleFilter(){
        if($(".status-filter").length>0){
            $(".status-filter").on("change",".toggle",function(){
                $("#State").val($(this).attr("data-value"));

                $(this).parents("form").submit();
            });
        }
    }

    function comparisonBox(){
        if($(".js-comparisonBox").length>0){
            $(".js-comparisonBox").on("click",function(){
                var $this = $(this),
                    theModal = $("#comparisonBoxModal");
                theModal.find(".oldFrontImg").attr("src",$this.attr("data-oldFrontImgUrl")||"");
                theModal.find(".oldBackImg").attr("src",$this.attr("data-oldBackImgUrl")||"");
                theModal.find(".newFrontImg").attr("src",$this.attr("data-newFrontImgUrl")||"");
                theModal.find(".newBackImg").attr("src",$this.attr("data-newBackImgUrl")||"");
                theModal.modal();
            });
        }

        if($(".js-showIdCard").length>0){
            $(".js-showIdCard").on("click",function(){
                var $this = $(this),
                    theModal = $("#idCardModal");
                theModal.find(".oldFrontImg").attr("src",$this.attr("data-oldFrontImgUrl")||"");
                theModal.find(".oldBackImg").attr("src",$this.attr("data-oldBackImgUrl")||"");
                theModal.modal();
            });
        }
    }

    // 不通过说明
    function notPass(){
        if($(".js-notPass").length>0){
            $(".js-notPass").on("click",function(e){
                e.preventDefault();
                var $this = $(this),
                    thisNotPassId = $this.attr("data-user-id"),
                    theModal = $("#notPassModal");
                $("#notPassId").val(thisNotPassId||"");
                theModal.modal();
            });
        }
    }

    return {
        init:function(){
            initDatepicker();
            handleCredit();
            handleFancyBox();
            handleFilter();
            comparisonBox();
            notPass();
        }
    }
}();