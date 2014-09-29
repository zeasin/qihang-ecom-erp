using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using EntityFramework.Extensions;

namespace LPOA.Model
{
    public class WorkFlowEntity
    {
        public string FlowId { get; set; }
        public string FlowName { get; set; }

        public string WorkId { get; set; }
        public string FlowLevel { get; set; }
        public string FlowDesc { get; set; }
    }

  
}
