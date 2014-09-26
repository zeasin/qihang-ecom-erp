using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using EntityFramework.Extensions;

namespace LPOA.Model
{
    public class Leave
    {
        public long Id { get; set; }

        public string Title { get; set; }

        public DateTime? StartDate { get; set; }

        public DateTime? EndDate { get; set; }

        public string LeaveType { get; set; }

        public string LeaveUser { get; set; }

        public string Shangji { get; set; }

        public string Bumen { get; set; }

        public string Zongjingli { get; set; }


        public string Intro { get; set; }
    }

    public class NorthwindContext : DbContext
    {
        public DbSet<Leave> Leaves { get; set; }

        public void AddEntity(Leave entity)
        {
            Leaves.Add(entity);
            base.SaveChanges();
        }

        public Leave GetById(long id)
        {
            return Leaves.Where(p => p.Id == id).FirstOrDefault();
         
        }
    }
}
