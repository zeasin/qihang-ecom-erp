using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Activities;
using LPOA.Entity;
using LPOA.Sqlite;

namespace LPOA.WF.Leaves
{

    public sealed class FormSubmitActivity : CodeActivity<Guid>
    {
        // 定义一个字符串类型的活动输入参数
        public InArgument<string> Text { get; set; }
        public InArgument<Guid> WorkId { get; set; }
        public InArgument<string> WorkType { get; set; }

        // 如果活动返回值，则从 CodeActivity<TResult>
        // 派生并从 Execute 方法返回该值。
        protected override Guid Execute(CodeActivityContext context)
        {
            // 获取 Text 输入参数的运行时值
            string text = context.GetValue(this.Text);
            Guid workid = context.GetValue(this.WorkId);
            string work = context.GetValue(this.WorkType);
            log4net.ILog log = log4net.LogManager.GetLogger("workflow");
            if (log.IsDebugEnabled)
            {
                log.Debug("1提交请假单[" + workid + "]");
            }
            Guid flowId= Guid.NewGuid();
            //将流程写入数据库
            //WorkFlowContext wfc = new WorkFlowContext();
            WorkFlowEntity flowEntity = new WorkFlowEntity { FlowId = flowId.ToString(), WorkId = workid.ToString(), FlowLevel = "0",  FlowDesc = "提交请假表单",  FlowName = work };
            WorkFlowDAL dal = new WorkFlowDAL();
            dal.InsertWorkFlow(flowEntity);
            if (log.IsDebugEnabled)
            {
                log.Debug("1提交请假单[添加工作流]");
            }
            //wfc.AddEntity(new WorkFlow { Id = flowId, WorkId = workid.ToString(), FlowLevel = 0, Intro = "提交请假表单", WorkType = work });
            //插入流程明细
            WorkFlowDetailEntity wfdEntity = new WorkFlowDetailEntity();
            wfdEntity.FlowDate = DateTime.Now;
            wfdEntity.FlowId = flowId.ToString();
            wfdEntity.FlowResult = 0;
            wfdEntity.Id = Guid.NewGuid().ToString();
            wfdEntity.Intro = "初始化流程";
            wfdEntity.Worker = "system";
            dal.InsertWorkFlowDetail(wfdEntity);
            if (log.IsDebugEnabled)
            {
                log.Debug("1提交请假单[添加工作流Detail]");
            }
            //WorkFlowDetailContext wfdContext = new WorkFlowDetailContext();
            //wfdContext.AddEntity(wfdEntity);

            return flowId;
        }
        //protected override void Execute(CodeActivityContext context)
        //{
        //    // 获取 Text 输入参数的运行时值
        //    string text = context.GetValue(this.Text);
        //    log4net.ILog log = log4net.LogManager.GetLogger("workflow");
        //    if (log.IsDebugEnabled)
        //    {
        //        log.Debug("1提交请假单["+text+"]");
        //    }
        //}
    }
}
