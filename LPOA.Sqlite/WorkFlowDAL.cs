using LPOA.Entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LPOA.Sqlite
{
    public class WorkFlowDAL
    {
        /// <summary>
        /// 查询所有
        /// </summary>
        public List<WorkFlowEntity> GetWorkFlowList()
        {
            var data = DALBase.DAL.WorkFlows.All();
            return data.ToList<WorkFlowEntity>();
        }
        public List<WorkFlowDetailEntity> GetWorkFlowDetail(Guid flowId)
        {
            var data = DALBase.DAL.WorkFlowDetail.FindAllBy(FlowId:flowId);
            return data.ToList<WorkFlowDetailEntity>();
        }

        /// <summary>
        /// 主键ID查询
        /// </summary>
        /// <param name="memberId"></param>
        /// <returns></returns>
        public WorkFlowEntity GetWorkFlowEntity(Guid flowId)
        {
            var data = DALBase.DAL.WorkFlows.FindByFlowId(flowId.ToString());
            return data;
        }

        public void InsertWorkFlow(WorkFlowEntity entity)
        {
            DALBase.DAL.WorkFlows.Insert(entity);
        }

        public void InsertWorkFlowDetail(WorkFlowDetailEntity entity)
        {
            DALBase.DAL.WorkFlowDetail.Insert(entity);
        }

    }
}
