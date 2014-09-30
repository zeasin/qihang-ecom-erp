using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LPOA.Entity
{
    public class WorkFlowEntity
    {
        public string FlowId { get; set; }
        public string FlowName { get; set; }

        public string WorkId { get; set; }
        public long FlowLevel { get; set; }
        public string FlowDesc { get; set; }
    }
}
