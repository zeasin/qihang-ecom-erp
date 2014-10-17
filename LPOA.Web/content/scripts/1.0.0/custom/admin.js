/**
 * Created by Administrator on 14-9-10.
 */
var Admin = function () {

    // private functions & variables

    function roleSelectHandle(){
        if($(".js-roleSelect").length>0){
            $(".js-roleSelect").on("change",function(){
                var $this = $(this),
                    optionSelected = $this.find("option:selected");
                if(optionSelected.attr("data-show-password")=="show"){
                    $(".js-commondPassword").slideDown(300);
                }else{
                    $(".js-commondPassword").slideUp(300);
                }
            }).trigger("change");
        }
    }

    function deleteConfirm(){
        if(Custom&&Custom.btnConfirm){
            Custom.btnConfirm("[data-delete-confirm]","您确定要删除该管理员吗？",null);
            Custom.btnConfirm("[data-delrole-confirm]","您确定要删除该角色吗？",null);
        }
    }

    function handleSelectAll(){
        if($(".js-checkList").length>0){
            // 表格的checkbox全选
            // 用attr要用true，$(this).attr('checked',true);不然会出现第二次点击全选，属性改变，但样式没有打对勾的问题，用prop就没有这个问题。
            $(".js-checkList").on( "change", ".checkAll", function (event) {
                var $parentDiv = $(this).parents(".js-checkList");
                $parentDiv.find(".checkers").not(":disabled").prop({ "checked": this.checked });
                $parentDiv.find(".checkAll").not(":disabled").prop({ "checked": this.checked });
                $.uniform.update();
            }).trigger("change");
            //选择单个
            $(".js-checkList").on("change", ".checkers", function (event) {
                var $parentDiv = $(this).parents(".js-checkList");
                var $checkers = $parentDiv.find(".checkers");
                $parentDiv.find(".checkAll").prop({ "checked": $checkers.length == $checkers.filter(":checked").length ? true : false });
                $.uniform.update();
            }).trigger("change");
        }

    }


    // public functions
    return {

        //main function
        init: function () {
            //initialize here something.
            roleSelectHandle();
            deleteConfirm();
            handleSelectAll();
        }

    };

}();