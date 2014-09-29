using LPOA.Entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LPOA.Sqlite
{
    public class LeavesDAL
    {
        /// <summary>
        /// 查询所有
        /// </summary>
        public List<LeaveEntity> ExecuteList()
        {
            var data = DALBase.DAL.Leaves.All();
            return data.ToList<LeaveEntity>();
        }
        /// <summary>
        /// 主键ID查询
        /// </summary>
        /// <param name="memberId"></param>
        /// <returns></returns>
        public LeaveEntity ExecuteEntity(long id)
        {
            var data = DALBase.DAL.Leaves.FindById(id);
            return data;
        }

        public void ExecuteInsert(LeaveEntity entity)
        {
            DALBase.DAL.Leaves.Insert(entity);
        }
        public void UpdateWorkFlowId( Guid leaveId,Guid FlowId)
        {
            DALBase.DAL.Leaves.UpdateByTitle(Title: leaveId.ToString(), WorkFlowId: FlowId.ToString());
        }
        public List<LeaveEntity> ExecuteListByUser(string userName)
        {
            var data = DALBase.DAL.Leaves.All().Where(DALBase.DAL.Leaves.LeaveUser==userName);
            return data.ToList<LeaveEntity>();
        }
        public List<LeaveEntity> ExecuteListByShangji(string shangji)
        {
            var data = DALBase.DAL.Leaves.FindAllBy(Shangji:shangji,State:0);
            return data.ToList<LeaveEntity>();
        }
       
    }
}
