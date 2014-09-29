using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LPOA.WF.Model
{
    public class WFWorker
    {
        /// <summary>
        /// 工作流名称
        /// </summary>
        public string WFName { get; set; }

        /// <summary>
        /// 工作WorkId
        /// </summary>
        public Guid WorkId { get; set; }

        /// <summary>
        /// 流程ID
        /// </summary>
        public Guid? FlowId { get; set; }

        /// <summary>
        /// 流程Level（判断流程走到哪）
        /// </summary>
        public long FlowLevel { get; set; }

        /// <summary>
        /// 操作人
        /// </summary>
        public string Worker { get; set; }
    }

    public class WFWorkResult
    {
        /// <summary>
        /// 流程ID
        /// </summary>
        public Guid FlowId { get; set; }
        /// <summary>
        /// 执行结果
        /// </summary>
        public long Result { get; set; }
        /// <summary>
        /// 下一步流程Level
        /// </summary>
        public long NextFlowLevel { get; set; }
        /// <summary>
        /// 消息
        /// </summary>
        public string Msg { get; set; }
    }
}
