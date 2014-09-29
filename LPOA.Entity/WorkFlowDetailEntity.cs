using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LPOA.Entity
{
    public class WorkFlowDetailEntity
    {
        public string Id { get; set; }
        public string FlowId { get; set; }
        public string Worker { get; set; }
        public long FlowResult { get; set; }
        public string Intro { get; set; }
        public DateTime FlowDate { get; set; }

    }
}
