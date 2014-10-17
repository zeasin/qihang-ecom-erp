$(document).ready(function () {
    //止损模态框
    $(".js-stopLost").on("click", function () {
        var tenderId = $(this).attr("data-tenderId");
        $.ajax({
            type: "Post",
            url: "/Tender/StopLostDetail",
            cache: false,
            data: { TenderId: tenderId },
            success: function (data) {
                $("#tenderId").val(tenderId);
                $("#NotRepayPrincipalTotal").text(data.NotRepayPrincipalTotal);
                $("#UserAccountBalance").text(data.UserAccountBalance);
                $("#InterestTotal").text(data.InterestTotal);
                $("#PayInterestTotal").text(data.PayInterestTotal);
                $("#NotPayInterestTotal").text(data.NotPayInterestTotal);
                $("#CreditSymbol").text(data.CreditSymbol);
                $("#PayByPlatform").text(data.PayByPlatform);
                $("#stopLostModal").modal();
            }
        });
    });

    //止损提交
    $("#btnStopLost").click(function () {
        var tenderId = $("#tenderId").val();
        var pwd = $("#txtStopLostPwd").val();
        var remark = $("#txtStopLostRemark").val();
        var banWithdraw = $("#chkBanWithdraw").get(0).checked;
        var banTender = $("#chkBadTender").get(0).checked;

        if (pwd.length == 0) {
            $("#stoplostPwdTip").show();
            return;
        } else {
            $("#stoplostPwdTip").hide();
        }

        if (remark.length == 0) {
            $("#stoplostRemarkTip").show();
            return;
        } else {
            $("#stoplostRemarkTip").hide();
        }

        $.ajax({
            type: "Post",
            url: "/Tender/StopLostSubmit",
            cache: false,
            data: { TenderId: tenderId, Pwd: pwd, Remark: remark, BanWithdraw: banWithdraw, BanTender: banTender },
            success: function (data) {
                if (data.length == 0)
                    window.location.reload();
                else
                    alert(data);

            },
            error: function () {
                alert("系统异常，请稍后再试");
            }
        });
    });
});

$(".js-reminders").on("click", function () {
    var tenderId = $(this).attr("data-tenderId");
    $("#reminderTenderId").val(tenderId);

    $.ajax({
        type: "Post",
        url: "/Tender/ReminderInfo",
        cache: false,
        data: { TenderId: tenderId },
        success: function (data) {
            var bind = '<span class="text-success">(已绑定)</span>';
            var notBind = '<span class="color_f34915">(未绑定)</span>';



            if (data.Moblie.length != 0) {
                $("#spanReminderMobile").html(bind);
                $("#chkReminderMobile").attr("disabled", false);
                $("#reminderMobile").text(data.Moblie);
            } else {
                $("#spanReminderMobile").html(notBind);
                $("#chkReminderMobile").attr("disabled", true);
                $("#reminderMobile").text("");
            }

            if (data.Mail.length != 0) {
                $("#spanReminderMail").html(bind);
                $("#chkReminderMail").attr("disabled", false);
                $("#reminderMail").text(data.Mail);
            } else {
                $("#spanReminderMail").html(notBind);
                $("#chkReminderMail").attr("disabled", true);
                $("#reminderMail").text("");
            }

            $("#reminderMsg").val(data.Msg);

            $("#remindersModal").modal('show');

        },
        error: function () {
            alert("系统异常，请稍后再试");
        }
    });

});

$("body").on("click", "#btnReminder", function () {
    var tenderId = $("#reminderTenderId").val();
    var moblie = $("#chkReminderMobile")[0].checked;
    var mail = $("#chkReminderMail")[0].checked;

    $.ajax({
        type: "Post",
        url: "/Tender/SentReminderInfo",
        cache: false,
        data: { TenderId: tenderId, Moblie: moblie, Mail: mail },
        success: function (data) {
            window.location.reload();
        }
    });
});

//借款管理中的搜索
$(document).ready(function () {
    $(".TenderOrderBy").click(function () {
        $("#TenderOrderBy").val($(this).attr("data-value"));
        if ($("#Sort").val().length == 0) {
            $("#Sort").val("Asc");
        } else {
            if ($("#Sort").val() == "Asc")
                $("#Sort").val("Desc");
            else
                $("#Sort").val("Asc");
        }
        $("#p").val(1);
        $("#formSearchTender").submit();
    });
});


/**
 * Created by Administrator on 14-8-25.
 */
var LoanHandler = function () {
    //    止损
    function stopLoan() {
        $(".js-stopLost").on("click", function () {
            // 获取id，然后根据id取数据
            $("#stopLoanId").val($(this).attr("data-loan-id"));
            $("#stopLostModal").modal();
        });

        $(".js-remindersStopLost").on("click", function () {
            $("#stopLoanId").val($(this).attr("data-loan-id"));
            $("#remindersStopLostModal").modal();
        });
    }

    function auditIdCard() {
        $(".js-auditIdCard").on("click", function () {
            $("#modalIdCardId").val($(this).attr("data-idCard-id"));
            $("#auditIdCardModal").modal();
        });
    }
    function datePickerHandle() {
        $(".datepicker").datepicker("setStartDate", new Date());
    }

    //   催款
    function remindersHandle() {
        if ($(".js-reminders").length > 0) {
            $(".js-reminders").on("click", function () {
                $("#remindersModal").modal();
            });
        }
    }

    //    打款
    function giveMoneyHandle() {
        if ($(".js-giveMoney").length > 0) {
            $(".js-giveMoney").on("click", function () {
                // 先填充数据，然后打开modal
                var $this = $(this),
                    $thisRow = $this.parents("tr"),
                    borrowerName = $.trim($thisRow.find(".borrowerName").text()),
                    loanTitle = $.trim($thisRow.find(".loanTitle").text()),
                    moneyNumber = $.trim($thisRow.find(".moneyNumber").text()),
                    loanFee = $.trim($thisRow.find(".loanFee").text()),
                    loanRate = $.trim($thisRow.find(".loanRate").text()),
                    loanDeadline = $.trim($thisRow.find(".loanDeadline").text()),
                    loanFeeVal = $.trim($thisRow.find(".loanFeeVal").val()),
                    moneyNumberVal = $.trim($thisRow.find(".moneyNumberVal").val()),
                    loanTotal = parseFloat(loanFeeVal) + parseFloat(moneyNumberVal);
                var theModal = $("#giveMoneyModal");
                theModal.find(".borrowerName").text(borrowerName || "");
                theModal.find(".loanTitle").text(loanTitle || "");
                theModal.find(".moneyNumber").text(moneyNumber || "");
                theModal.find(".loanFee").text(loanFee || "");
                theModal.find(".loanRate").text(loanRate || "");
                theModal.find(".loanDeadline").text(loanDeadline || "");
                theModal.find(".loanTotal").text(loanTotal + "元" || "");

                theModal.modal();
            });
        }
    }

    // 手机预约
    function handleOrder() {
        if ($(".js-makeOrder").length > 0) {
            $(".js-makeOrder").on("click", function () {
                var $this = $(this),
                    thisUserName = $this.attr("data-user-name") || "",
                    thisLoanId = $this.attr("data-loan-id"),
                    theModal = $("#makeOrderModal");
                theModal.find(".userName").text(thisUserName);
                theModal.find(".loanId").val(thisLoanId);
                theModal.modal();
            });
        }
    }
    return {
        init: function () {
            stopLoan();
            auditIdCard();
            datePickerHandle();
            remindersHandle();
            giveMoneyHandle();
            handleOrder();
        }
    }
}();