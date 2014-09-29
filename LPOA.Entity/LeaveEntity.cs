using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LPOA.Entity
{
    public class LeaveEntity
    {
        public long Id { get; set; }

        public string Title { get; set; }

        public DateTime StartDate { get; set; }

        public DateTime EndDate { get; set; }

        public string LeaveType { get; set; }

        public string LeaveUser { get; set; }

        public string Shangji { get; set; }

        public string Bumen { get; set; }

        public string Zongjingli { get; set; }


        public string Intro { get; set; }

        public long State { get; set; }

        public string WorkFlowId { get; set; }
    }
}
