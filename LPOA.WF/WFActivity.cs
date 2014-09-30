using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Activities;
using LPOA.WF.Model;
using LPOA.Sqlite;
using LPOA.Entity;

namespace LPOA.WF
{

    public sealed class WFActivity : CodeActivity
    {
        // 定义一个字符串类型的活动输入参数
        public InArgument<string> Text { get; set; }

        public InArgument<WFWorker> Worker { get; set; }

        public OutArgument<WFWorkResult> Result { get; set; }

        protected long WorkingLevel { get; set; }

        // 如果活动返回值，则从 CodeActivity<TResult>
        // 派生并从 Execute 方法返回该值。
        protected override void Execute(CodeActivityContext context)
        {
            // 获取 Text 输入参数的运行时值
            string text = context.GetValue(this.Text);
            WFWorker worker = context.GetValue(this.Worker);

            log4net.ILog log = log4net.LogManager.GetLogger("workflow");
            if (log.IsDebugEnabled)
            {
                log.Debug("0启动工作流[" + worker.WorkId.ToString() + "]");
            }


            //使用Workid查找流程
            WorkFlowDAL flowdal = new WorkFlowDAL();
            WorkFlowEntity working = flowdal.GetWorkFlowEntity(worker.WorkId, worker.WFName);
            
            if (working == null)
            {
                
                //提交流程表单
                if (log.IsDebugEnabled)
                {
                    log.Debug("1提交工作流表单[" + worker.WorkId.ToString() + "]");
                }
                Guid flowId = Guid.NewGuid();
                WorkFlowEntity flowEntity = new WorkFlowEntity { 
                    FlowId = flowId.ToString(), 
                    WorkId = worker.WorkId.ToString(), 
                    FlowLevel = 0, 
                    FlowDesc = "提交请假表单", 
                    FlowName = worker.WFName 
                };


                flowdal.InsertWorkFlow(flowEntity);

                //插入流程明细
                WorkFlowDetailEntity wfdEntity = new WorkFlowDetailEntity();
                wfdEntity.FlowDate = DateTime.Now;
                wfdEntity.FlowId = flowId.ToString();
                wfdEntity.FlowResult = 0;
                wfdEntity.Id = Guid.NewGuid().ToString();
                wfdEntity.Intro = "初始化流程";
                wfdEntity.Worker = "system";
                flowdal.InsertWorkFlowDetail(wfdEntity);


                WFWorkResult wr =new WFWorkResult();
                wr.Msg="OK";
                wr.NextFlowLevel=1;
                context.SetValue(this.Result, wr);
            }
            else
            {
                WorkingLevel = working.FlowLevel;
                //流程审核
                
                //查询工作流状态Level
                if (log.IsDebugEnabled)
                {
                    log.Debug("2工作流流转到[" + working.FlowLevel.ToString() + "]");
                }

                if (working.FlowLevel == 0)
                {
                    log.Debug("2工作流主管审批");
                }
                else
                {
                    log.Debug("3工作流：审批");
                }

                WFConfigDAL wfdal = new WFConfigDAL();
                //工作的流程配置
                WFConfigEntity wfConfig = wfdal.GetWFConfig(working.FlowName);

                List<WFNodeEntity> nodes = wfdal.GetWFNodeByWFId(Guid.Parse(wfConfig.WFId));

                //正在执行的流程节点
                var node = wfdal.GetWFNodeByGreaterLevel(working.FlowLevel, wfConfig.WFId.ToString());

                if (node != null)
                {
                    //表示还有流程没走完

                    //添加审批明细
                    WorkFlowDetailEntity wfdEntity = new WorkFlowDetailEntity();
                    wfdEntity.FlowDate = DateTime.Now;
                    wfdEntity.FlowId = working.FlowId.ToString();
                    wfdEntity.FlowResult = node.Level;//正在执行的流程Level
                    wfdEntity.Id = Guid.NewGuid().ToString();
                    wfdEntity.Intro = "审批流程";
                    wfdEntity.Worker = worker.Worker;

                    flowdal.InsertWorkFlowDetail(wfdEntity);


                    //更新FlowLevel
                    //flowdal.UpdateWorkFlowLevel(Guid.Parse(working.FlowId), node.Level);

                    WFWorkResult wr = new WFWorkResult();
                    wr.Msg = "OK";
                    wr.Result = -1;
                    wr.NextFlowLevel = 1;
                    context.SetValue(this.Result, wr);
                }
                else
                {
                    WFWorkResult wr = new WFWorkResult();
                    wr.Msg = "OK";
                    wr.Result = -1;
                    wr.NextFlowLevel = 1;
                    context.SetValue(this.Result, wr);
                }

            }
        }
    }
}
