using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LPOA.Sqlite
{
    public class WFConfigEntity
    {
        public string WFId{get;set;}
        public string WFName { get; set; }
        public string WFTitle { get; set; }
        public string WFIntro { get; set; }
    }

    public class WFNodeEntity
    {
        public long NodeId { get; set; }
        public string WFId { get; set; }
        public long Level { get; set; }
        public string Intro { get; set; }
    }
}
