using LPOA.Entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LPOA.Sqlite
{
    public class WFConfigDAL
    {
        public WFNodeEntity GetWFNodeByGreaterLevel(long level,string wfid)
        {
            var data = DALBase.DAL.WFNode.Find(DALBase.DAL.WFNode.Level > level && DALBase.DAL.WFNode.WFId == wfid);
            return data; 
        }

        public List<WFNodeEntity> GetWFNodeByWFId(Guid WFID)
        {
            var data = DALBase.DAL.WFNode.FindAllByWFId(WFID.ToString());
            return data.ToList<WFNodeEntity>();
        }

        public WFConfigEntity GetWFConfig(string WFName)
        {
            var data = DALBase.DAL.WFConfig.FindByWFName(WFName);
            return data;
        }
    }
}
