/**
 * Created by Administrator on 14-8-19.
 */
var InfoManageHandler = function(){

    function initKindEditor(){
        var editor;
        KindEditor.ready(function (K) {
            editor = K.create('textarea[id="Body"]', {
//                uploadJson: '/HelpDocuments/UpLoad',
//                allowImageUpload: true,
                width: '100%',
                afterCreate: function () {
                    this.sync();
                },
                afterBlur: function () {
                    if (this.isEmpty()) {
                        this.text("");
                    }
                    this.sync();
                }
//                ,
//                items: ['cut', 'copy', 'paste', 'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
//                    'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
//                    'superscript', 'quickformat', 'selectall', '|', '/', 'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
//                    'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage', 'emoticons', '|', 'clearhtml']
            });
        });
    }

    return {
        init:function(){
            initKindEditor();
        }
    }
}();