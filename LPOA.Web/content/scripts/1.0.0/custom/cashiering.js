/**
 * Created by Administrator on 14-8-22.
 */

var CashieringHandler = function(){

    function showCard(){
        var $showCardBtn = $(".js-showCardInfo");
        if($showCardBtn.length>0){
            $showCardBtn.on("click",function(){
                $("#bankCardModal").modal();
            });
        }
    }
    function auditWithdraw(){
        var $auditWithdrawBtn = $(".js-auditWithdraw");
        if($auditWithdrawBtn.length>0){
            $auditWithdrawBtn.on("click", function () {
                $("#auditWithdrawModal").modal();
                $("#EncashmentAuditID").val($(this).attr("data-apply-id"));
            });
        }
    }

    return {
        init:function(){
            showCard();
            auditWithdraw();
        }
    }
}();