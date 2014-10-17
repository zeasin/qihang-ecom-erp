/**
 * Created by Administrator on 14-8-19.
 */

var AnyibaoHandler = function(){

    function initDatepicker(){
        var $datepicker = $(".datepicker");
        if($datepicker.length > 0 && $datepicker.datepicker){
            $datepicker.datepicker({
                format: 'yyyy-mm-dd',
                autoclose: true,
                language: 'zh-CN',
                todayBtn: "linked",
                startDate:Date.now()
            });
        }


        var $lastDate = $(".lastDate");
        if($lastDate.length > 0 && $lastDate.datepicker){
            $lastDate.datepicker().on("changeDate",function(){
                $(".lastDateText").text(this.value);
            });
        }

    }

    function investorDetailHandle(){
        $(".js-investorDetail").on("click",function(){
            var $this = $(this),
                thisId = $this.attr("data-anyibao-id"),
                container = $("[data-target='"+thisId+"']");
            container.toggle(300);
        });
    }

    function cashBack(){
        if($(".js-cashBack").length>0){
            $(".js-cashBack").on("click",function(){
                $("#cashBackModal").find(".modal-title").text($(this).attr("data-modal-title"));
                $("#cashBackModal").modal();
            });
        }
    }

    return {
        init:function(){
            initDatepicker();
            investorDetailHandle();
            cashBack();
        }
    }
}();