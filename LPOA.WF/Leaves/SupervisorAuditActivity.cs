using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Activities;
using LPOA.Entity;
using LPOA.Sqlite;

namespace LPOA.WF.Leaves
{

    public sealed class SupervisorAuditActivity : CodeActivity
    {
        // 定义一个字符串类型的活动输入参数
        public InArgument<string> Text { get; set; }
        public InArgument<Guid> FlowId { get; set; }

        public InArgument<string> Worker { get; set; }

        // 如果活动返回值，则从 CodeActivity<TResult>
        // 派生并从 Execute 方法返回该值。
        protected override void Execute(CodeActivityContext context)
        {
            // 获取 Text 输入参数的运行时值
            string text = context.GetValue(this.Text);
            Guid flowid = context.GetValue(this.FlowId);
            string worker = context.GetValue(this.Worker);

            log4net.ILog log = log4net.LogManager.GetLogger("workflow");
            if (log.IsDebugEnabled)
            {
                log.Debug("2主管审批[" + flowid + "]");
            }

            //插入流程明细
            WorkFlowDetailEntity wfdEntity = new WorkFlowDetailEntity();
            wfdEntity.FlowDate = DateTime.Now;
            wfdEntity.FlowId = flowid.ToString();
            wfdEntity.FlowResult = 0;
            wfdEntity.Id = Guid.NewGuid().ToString();
            wfdEntity.Intro = "审批流程";
            wfdEntity.Worker = worker;
            WorkFlowDAL dal = new WorkFlowDAL();
            dal.InsertWorkFlowDetail(wfdEntity);
            if (log.IsDebugEnabled)
            {
                log.Debug("1提交请假单[添加工作流Detail]");
            }
        }
    }
}
