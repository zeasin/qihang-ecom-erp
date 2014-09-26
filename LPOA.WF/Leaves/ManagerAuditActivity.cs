using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Activities;

namespace LPOA.WF.Leaves
{

    public sealed class ManagerAuditActivity : CodeActivity
    {
        // 定义一个字符串类型的活动输入参数
        public InArgument<string> Text { get; set; }

        // 如果活动返回值，则从 CodeActivity<TResult>
        // 派生并从 Execute 方法返回该值。
        protected override void Execute(CodeActivityContext context)
        {
            // 获取 Text 输入参数的运行时值
            string text = context.GetValue(this.Text);
            log4net.ILog log = log4net.LogManager.GetLogger("workflow");
            if (log.IsDebugEnabled)
            {
                log.Debug("3总经理审批[" + text + "]");
            }
        }
    }
}
